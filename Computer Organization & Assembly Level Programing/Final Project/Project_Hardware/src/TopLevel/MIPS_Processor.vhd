library IEEE;
use IEEE.std_logic_1164.all;

entity MIPS_Processor is

  generic(N : integer := 32);
  port(iCLK            : in std_logic;
       iRST            : in std_logic;
       iInstLd         : in std_logic;
       iInstAddr       : in std_logic_vector(N-1 downto 0);
       iInstExt        : in std_logic_vector(N-1 downto 0);
       oALUOut         : out std_logic_vector(N-1 downto 0)); 
end  MIPS_Processor;


architecture structure of MIPS_Processor is

  -- Required data memory signals
  signal s_DMemWr       : std_logic; 
  signal s_DMemAddr     : std_logic_vector(N-1 downto 0); 
  signal s_DMemData     : std_logic_vector(N-1 downto 0);
  signal s_DMemOut      : std_logic_vector(N-1 downto 0);

  -- Required instruction memory signalss
  signal s_IMemAddr     : std_logic_vector(N-1 downto 0); 
  signal s_NextInstAddr : std_logic_vector(N-1 downto 0); 
  signal s_Inst         : std_logic_vector(N-1 downto 0);

  -- Required register file signals 
  signal s_RegWr        : std_logic; 
  signal s_RegWrAddr    : std_logic_vector(4 downto 0);
  signal s_RegWrData    : std_logic_vector(N-1 downto 0); 

  signal s_regWriteData :  std_logic_vector(31 downto 0); 
  signal s_muxMemToRegWrite :  std_logic_vector(31 downto 0); 

   --movn 
   signal s_mux1out     : std_logic;
   signal s_movnOut     : std_logic; 
   signal s_mux2out     : std_logic_vector(N-1 downto 0);
   signal s_mux3out     : std_logic_vector(N-1 downto 0);

  signal s_Halt         : std_logic;
  signal s_Ovfl         : std_logic; 
  signal s_beqS         : std_logic;
  signal s_ZERO         : std_logic;
  signal s_Carry        : std_logic; 
  signal s_signExtend, s_regDst, s_aluSrc, s_memToReg, s_memRead, s_Branch, s_j, s_ANDo, s_jr, s_jal, s_movn	: std_logic;
  signal s_aluControl   : std_logic_vector(3 downto 0);
  signal s_aluout, s_PCFour, s_tempram    : std_logic_vector(31 downto 0);
  signal s_regout1, s_regout2, s_extended: std_logic_vector(31 downto 0);
  signal s_jshifto, s_baddro, s_ALUWB : std_logic_vector(31 downto 0);
  signal s_writePass, s_wo : std_logic_vector(4 downto 0);

  signal s_JALmuxo : std_logic_vector(31 downto 0);
  signal s_JRmuxo  : std_logic_vector(31 downto 0);

  signal s_AS, s_sub, s_ignrOvfl : std_logic;

--Software Signals
  signal s_RegWr_temp, s_DmemWR_temp, s_EXmemZero, s_regequal, s_EXmemHalt, s_RegValue, s_Halt_temp, s_flush2 : std_logic;
  signal s_IDEXmem, s_EXmemMEM : std_logic_vector(2 downto 0);
  signal s_EXmemWB, s_IDEXwb, s_WBmemOut: std_logic_vector(3 downto 0);
  signal s_WRmuxO, s_IDEXrs, s_IDEXrt, s_IDEXrd, s_RegWrA_temp, s_tempForwardRegO, s_EXmemRegoMux, s_WRtemp, s_IDEXfunc : std_logic_vector(4 downto 0);
  signal s_IDEXex : std_logic_vector(5 downto 0);
  signal s_IDEXreg1, s_IDEXreg2, s_IDEXextend, s_IDEXinst, s_IDEXnewPC, s_EXmemMux, s_IFIDinst, s_IFIDnewPC, s_EXmemF, s_EXmemALUo, s_EXmemNewPC, s_WBdmem, s_WBmemF,
s_PCend, s_WBmemALUo, s_WBmemNewPC, s_JmuxO, s_BaddO, s_BshiftO, s_BmuxO : std_logic_vector(31 downto 0);

--Hardware Signals
signal	s_stallIF, s_IFIDflush, s_ReadWRmem   : std_logic := '0';
signal	s_EXmemstall, s_EXmemflush, s_IDEXstall, s_IDEXflush, s_memWBflush, s_memWBstall: std_logic := '1';
signal  s_forwardA, s_forwardB,  s_forwardBranch, s_forwardBranch2 : std_logic_vector(1 downto 0) := "00";
signal  s_IDEXhalt : std_logic := '0';
signal  s_PCstallAddr : std_logic_vector(31 downto 0);
signal  s_memWBrd, s_EXmemRD, s_exmemrdrtmux : std_logic_vector(4 downto 0);
signal  s_IDEXReadWRmem, s_EXmemReadWR, s_memWBReadWR, s_memwbmemreadwrite : std_logic; 
signal  s_memwbaluout, s_idexsignext, s_exmembranchaddr, s_IDEXJRmuxO, s_EXmemJRmuxO, s_memWBjrmuxO : std_logic_vector(31 downto 0);
signal  s_temp_regout2, s_temp_regout1, s_forwardAMuxOut, s_forwardBMuxOut  : std_logic_vector(31 downto 0);


component mem is
  generic(ADDR_WIDTH : integer;
          DATA_WIDTH : integer);
    port(
          clk          : in std_logic;
          addr         : in std_logic_vector((ADDR_WIDTH-1) downto 0);
          data         : in std_logic_vector((DATA_WIDTH-1) downto 0);
          we           : in std_logic := '1';
          q            : out std_logic_vector((DATA_WIDTH -1) downto 0));
    end component;

component PC is
        port(i_IN      : in std_logic_vector(N-1  downto 0);
            i_CLK      : in std_logic;
            i_RST      : in std_logic;
            i_WE       : in std_logic;
            o_OUT      : out std_logic_vector(N-1  downto 0));
    end component;

component AdderSub_N is 
    generic(N : integer := 32);
    port(i_D0           : in std_logic_vector(N-1 downto 0);
         i_D1           : in std_logic_vector(N-1 downto 0);
         i_addSub       : in std_logic;
         o_Sum          : out std_logic_vector(N-1 downto 0);
         o_Car          : out std_logic);
   end component;

component controlUnit is
    port(
       opcode     : in std_logic_vector(5 downto 0);
       func       : in std_logic_vector(5 downto 0);
       aluCtrl    : out std_logic_vector(3 downto 0);
       Branch     : out std_logic;
       beqS       : out std_logic;
       j	  : out std_logic;
       jr	  : out std_logic;
       jal	  : out std_logic;
       memRead    : out std_logic;
       memToReg   : out std_logic;
       memWrite   : out std_logic;
       aluSrc     : out std_logic;
       regWrite   : out std_logic;
       signExtend : out std_logic;
       regDst     : out std_logic;
       reset	  : out std_logic;
       sub        : out std_logic;
       ignrOvfl   : out std_logic;
       moven      : out std_logic);
  end component;

component dffg_N is
  generic(N: integer := 32);
  port(i_CLK        : in  std_logic;    
       i_RST        : in  std_logic;     
       i_WE         : in  std_logic;     
       i_D          : in  std_logic_vector(N-1 downto 0);   
       o_Q          : out std_logic_vector(N-1 downto 0)); 
end component;

component MIPS_Reg is

    port(i_CLK        : in std_logic;
       i_RST          : in std_logic;
       i_w_enable     : in std_logic;
       i_w_data       : in std_logic_vector(31 downto 0);
       i_w_adrs	      : in std_logic_vector(4 downto 0);
       i_rs	      : in std_logic_vector(4 downto 0);
       i_rt	      : in std_logic_vector(4 downto 0);
       o_rs           : out std_logic_vector(N-1  downto 0);
       o_rt           : out std_logic_vector(N-1  downto 0));
  end component;

component ALU is
   generic(N: integer := 32);
   port(i_alu_A		: in std_logic_vector(31 downto 0);  
        i_alu_B     	: in std_logic_vector(31 downto 0);
        i_shamt		: in std_logic_vector(4 downto 0);    
        i_aluOP         : in std_logic_vector(3 downto 0); 
  	i_addSub	: in std_logic;
	i_Dir   	: in std_logic;  
	i_Arith  	: in std_logic;
	i_beqS		: in std_logic;
	i_s_ignrOvfl	: in std_logic;
        i_Inst          : in std_logic_vector(31 downto 0);
	o_overflow	: out std_logic;
	o_Zero		: out std_logic;
        o_F        	: out std_logic_vector(N-1 downto 0));  
  end component;

component extender is
    port(
	 i_orgn	 	: in std_logic_vector(15 downto 0);
	 i_sign         : in std_logic;
	 o_extd	        : out std_logic_vector(31 downto 0));
end component;


component mux2t1_N is
    generic(N : integer := 32);
    port(
       i_D0             : in std_logic_vector(N-1 downto 0);
       i_D1	        : in std_logic_vector(N-1 downto 0);
       i_S 	        : in std_logic;
       o_O 	        : out std_logic_vector(N-1 downto 0));
end component;

component BarrelShifter is
  port(i_input          : in std_logic_vector(31 downto 0);
       i_shamt          : in std_logic_vector(4 downto 0);
       i_Dir            : in std_logic;  
       i_Arith          : in std_logic;
       o_Out            : out std_logic_vector(31 downto 0));
end component;

component Mux2t1 is
  port(i0               : in std_logic;
       i1               : in std_logic;
       s                : in std_logic;
       oY               : out std_logic);
end component;

  component dffg
    port(
        i_CLK        : in std_logic;     
        i_RST        : in std_logic;     
        i_WE         : in std_logic;    
        i_D          : in std_logic;   
        o_Q          : out std_logic);
  end component;

component Move is
  port(	
	i_rt : in std_logic_vector(31 downto 0);
	o_regWriteO: out std_logic);

end component;

component Fetch is   
  generic(N: integer:= 32);
  port(WE		 : in std_logic;
       Jump_WE           : in std_logic;
       jr_WE             : in std_logic;
       jal_WE		 : in std_logic; 
       reg31             : in std_logic_vector(N-1 downto 0);
       Branch_WE         : in std_logic;
       IMM 	         : in std_logic_vector(N-1 downto 0);
       INSTR             : in std_logic_vector(N-1 downto 0);
       i_CLK             : in std_logic;
       i_RST             : in std_logic;
       o_Address         : out std_logic_vector(N-1 downto 0));
end component;

component IFID is
  port(i_inst       : in std_logic_vector(N-1  downto 0);
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_flush      : in std_logic;
       i_stall      : in std_logic;
       i_newPC	    : in std_logic_vector(N-1 downto 0);
       o_inst       : out std_logic_vector(N-1  downto 0);
       o_newPC     : out std_logic_vector(N-1  downto 0));
end component;

component memWB is 
  port(i_dmem       : in std_logic_vector(N-1  downto 0);
       i_aluout	    : in std_logic_vector(N-1 downto 0);
       i_NewPC      : in std_logic_vector(N-1 downto 0);
       i_rdrtmux    : in std_logic_vector(4 downto 0);
       i_flush	    : in std_logic;
       i_stall      : in std_logic;
       i_halt	    : in std_logic;
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_WB	    : in std_logic_vector(3 downto 0);
       i_rd	    : in std_logic_vector(4 downto 0);
       i_jrmuxout   : in std_logic_vector(N-1  downto 0);
       i_memreadwrite : in std_logic;
       o_dmem       : out std_logic_vector(N-1  downto 0);
       o_forward    : out std_logic_vector(N-1  downto 0);
       o_aluout	    : out std_logic_vector(N-1 downto 0);
       o_newpc      : out std_logic_vector(N-1 downto 0);
       o_rdrtmux    : out std_logic_vector(4 downto 0);
       o_rd	    : out std_logic_vector(4 downto 0);
       o_WB	    : out std_logic_vector(3 downto 0);
       o_jrmuxout   : out std_logic_vector(N-1  downto 0);
       o_memreadwrite : out std_logic;
       o_halt	    : out std_logic);
end component;

component EXmem is 
  port(i_alu        : in std_logic_vector(N-1  downto 0);
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_WB	    : in std_logic_vector(3 downto 0);
       i_M	    : in std_logic_vector(2 downto 0);
       i_halt	    : in std_logic;
       i_ExMemMUX   : in std_logic_vector(N-1 downto 0);
       i_rdrtMUX    : in std_logic_vector(4 downto 0);
       i_NewPC	    : in std_logic_vector(N-1 downto 0);
       i_jrmuxout   : in std_logic_vector(N-1 downto 0);
       i_memreadwrite : in std_logic;
       i_Zero	    : in std_logic;
       i_rd	    : in std_logic_vector(4 downto 0);
       i_flush      : in std_logic;
       i_flush2     : in std_logic;
       i_stall      : in std_logic;
       o_WB	    : out std_logic_vector(3 downto 0);
       o_M	    : out std_logic_vector(2 downto 0);
       o_Zero	    : out std_logic;
       o_alu        : out std_logic_vector(N-1  downto 0);
       o_ExMemMUX   : out std_logic_vector(N-1 downto 0);
       o_rdrtMUX    : out std_logic_vector(4 downto 0);
       o_NewPC	    : out std_logic_vector(N-1 downto 0);
       o_rd	    : out std_logic_vector(4 downto 0);
       o_jrmuxout   : out std_logic_vector(N-1 downto 0);
       o_memreadwrite : out std_logic;
       o_halt	    : out std_logic);
end component;

component IDEX is 
  port(
        i_WB		: in std_logic_vector(3 downto 0);
        i_MEM		: in std_logic_vector(2 downto 0);
        i_EX	        : in std_logic_vector(5 downto 0);
        i_halt		: in std_logic; 
        i_flush		: in std_logic;
	i_stall		: in std_logic;
        i_reg1out    	: in std_logic_vector(N-1  downto 0);
        i_reg2out    	: in std_logic_vector(N-1  downto 0);
	i_NewPC 	: in std_logic_vector(N-1 downto 0);
        i_inst       	: in std_logic_vector(N-1  downto 0);
        i_signExt    	: in std_logic_vector(N-1  downto 0);
	i_regwriteaddr  : in std_logic_vector(4 downto 0);
        i_jrmuxout	: in std_logic_vector(N-1 downto 0);
	i_memreadwrite  : in std_logic;
        i_CLK        	: in std_logic;
        i_RST        	: in std_logic;
	i_funcS		: in std_logic_vector(4 downto 0);
	o_funcS		: out std_logic_vector(4 downto 0);
        o_WBSig      	: out std_logic_vector(3 downto 0);
        o_MEMSig     	: out std_logic_vector(2 downto 0);
        o_EX	     	: out std_logic_vector(5 downto 0);
	o_reg1out    	: out std_logic_vector(N-1 downto 0);
	o_reg2out    	: out std_logic_vector(N-1 downto 0);
        o_RegRs      	: out std_logic_vector(5-1 downto 0);
	o_NewPC  	: out std_logic_vector(N-1 downto 0);
        o_RegRt     	: out std_logic_vector(5-1 downto 0);
        o_signExt       : out std_logic_vector(N-1 downto 0);
        o_RegRd      	: out std_logic_vector(5-1 downto 0);
	o_regwriteaddr  : out std_logic_vector(4 downto 0);
        o_inst       	: out std_logic_vector(N-1  downto 0);
        o_jrmuxout	: out std_logic_vector(N-1 downto 0);
	o_memreadwrite  : out std_logic;
        o_halt		: out std_logic);
end component;

component hazardUnit is
    port( 
	IFIDrs		 : std_logic_vector(4 downto 0);
	IFIDrt		 : std_logic_vector(4 downto 0);
	IDEXrd		 : std_logic_vector(4 downto 0);
	EXMEMrd		 : std_logic_vector(4 downto 0);
	MEMWBrd		 : std_logic_vector(4 downto 0);
	
	jump   		 : in std_logic;
	regEqual 	 : in std_logic;
	jal    		 : in std_logic;
	jr		 : in std_logic;
	IDEX_jal 	 : in std_logic;
	EXMEM_jal	 : in std_logic;
	MEMWB_jal	 : in std_logic;
	IDEX_jr 	 : in std_logic;
	EXMEM_jr	 : in std_logic;
	MEMWB_jr	 : in std_logic;
	ReadWriteMem 	 : in std_logic;
	ex_ReadWriteMem  : in std_logic;
	mem_ReadWriteMem : in std_logic;
	wb_ReadWriteMem  : in std_logic;
        extendCheck      : in std_logic;

	stallIF : out std_logic;
	stallID : out std_logic;
	stallEX : out std_logic;
	stallWB : out std_logic;
	flushIF : out std_logic;
	flushID : out std_logic;
	flushEX : out std_logic;
	flushWB : out std_logic;
        flushtemp: out std_logic); 

end component;

component forwardingUnit is 
   port(IDEXrs  	: in std_logic_vector(4 downto 0);
	IDEXWB  	: in std_logic_vector(3 downto 0);
	IDEXrt  	: in std_logic_vector(4 downto 0);
	IDEXrdrt	: in std_logic_vector(4 downto 0);
	IFIDrs		: in std_logic_vector(4 downto 0);
	IFIDrt		: in std_logic_vector(4 downto 0);
	EXMEMrd 	: in std_logic_vector(4 downto 0);
	EXMEMrt 	: in std_logic_vector(4 downto 0);
	MEMWBrd 	: in std_logic_vector(4 downto 0);
	MEMWBrt 	: in std_logic_vector(4 downto 0);
	EXMEMWB 	: in std_logic_vector(3 downto 0);
	MEMWBWB 	: in std_logic_vector(3 downto 0);
	ForwardA	: out std_logic_vector(1 downto 0);
	ForwardBranch   : out std_logic_vector(1 downto 0);
	ForwardBranch2 	: out std_logic_vector(1 downto 0);
	ForwardB 	: out std_logic_vector(1 downto 0));
end component;

------------------------------------------------------------------------------------------------------------------------

begin
  with iInstLd select
    s_IMemAddr <= s_NextInstAddr when '0',
      iInstAddr when others;


IMem: mem
    generic map(ADDR_WIDTH => 10,
                DATA_WIDTH => N)
    port map(clk  => iCLK,
             addr => s_IMemAddr(11 downto 2),
             data => iInstExt,
             we   => iInstLd,
             q    => s_Inst);

DMem: mem
    generic map(ADDR_WIDTH => 10,
                DATA_WIDTH => N)
    port map(clk  => iCLK,
             addr => s_DMemAddr(11 downto 2),
             data => s_DMemData,
             we   => s_DMemWr,
             q    => s_DMemOut);

pcReg: PC 
    port map(
        i_IN    => s_PCend,
        i_CLK   => iCLK,
        i_RST   => iRST,
        i_WE    => '1',
        o_OUT    => s_NextInstAddr
    );

prePCmux: mux2t1_N port map( 
		i_D0 => s_PCFour,
		i_D1 => s_JRmuxO, 
		i_S => s_j or (s_Branch and s_regequal) or s_jr,
		o_O  => s_PCend 
);

s_PCstallAddr <= x"00000000" when (s_stallIF = '0') else x"00000004"; 

pcAdd: AdderSub_N 
    port map(
        i_D0        => s_IMemAddr,           
        i_D1	    => s_PCstallAddr,	           
        i_addSub    => '0',       
        o_Sum 	    => s_PCFour,	            
        o_Car 	    => s_Carry	 
    );


control: controlUnit port map( 
	opcode     => s_IFIDinst(31 downto 26),
	func       => s_IFIDinst(5 downto 0),  
	aluCtrl    => s_aluControl,
	Branch     => s_Branch,
        beqS       => s_beqS,
	j          => s_j,
	jr         => s_jr,
	jal        => s_jal,
	memRead    => s_memRead,
	memToReg   => s_memToReg,
 	memWrite   => s_DmemWR_temp, 
	aluSrc     => s_aluSrc,
	regWrite   => s_RegWr_temp, 
	signExtend => s_signExtend,
       	regDst     => s_regDst,
	reset 	   => s_Halt_temp,
        sub        => s_sub,
        ignrOvfl   => s_ignrOvfl,
	moven      => s_movn
);

RegFil: MIPS_Reg port map( 
		i_clk      => not iCLK, 
		i_rst      => iRST,
		i_w_enable => s_RegWr,
		i_w_data   => s_RegWrData, 
		i_w_adrs   => s_RegWrAddr,
		i_rs       => s_IFIDinst(25 downto 21), 
		i_rt       => s_IFIDinst(20 downto 16), 
		o_rs       => s_regout1, 
		o_rt       => s_regout2); 

immMux: mux2t1_N port map(  
        i_D0    => s_IDEXreg2, 
        i_D1	=> s_IDEXextend, 
	i_S	=> s_IDEXex(4),  
        o_O 	=> s_mux2out);

with s_WBmemOut(2) select s_tempram <= 
	s_WBmemALUo	when '0',
	s_WBmemNewPC    when '1',
	s_WBmemALUo	when others;

MuxMem: mux2t1_N port map( 
		i_D0 => s_tempram,
		i_D1 => s_WBdmem,
		i_S  => s_WBmemOut(0),
		o_O  => s_RegWrData
);

with s_IDEXinst(31 downto 26) select s_AS <= 
	s_IDEXinst(1) when "000000",          
        s_IDEXfunc(0) when others;
	 

mainALU: ALU port map( 
	i_alu_A		=> s_forwardAMuxOut, 
        i_alu_B     	=> s_forwardBMuxOut, 
        i_shamt		=> s_IDEXinst(10 downto 6),  
        i_aluOP         => s_IDEXex(3 downto 0), 
  	i_addSub	=> s_AS,
	i_Dir   	=> not s_IDEXinst(1),
	i_Arith  	=> s_IDEXinst(0),
	i_beqS		=> s_IDEXfunc(4),
        i_Inst          => s_IDEXinst, 
	i_s_ignrOvfl	=> s_IDEXfunc(1),
	o_overflow	=> s_Ovfl, 
	o_Zero		=> s_ZERO,
        o_F        	=> s_aluout);


oALUOut <= s_aluout;

signExtend: extender port map( 
		i_orgn =>  s_IFIDinst(15 downto 0),
		i_sign =>  s_signExtend,
		o_extd =>  s_extended
);

with s_jal select s_WRtemp <=
	s_IFIDinst(20 downto 16) when '0',
	"11111"	                 when others;

MuxWR: mux2t1_N 
	generic map(N => 5)
	port map(
		i_D0 => s_WRtemp,
		i_D1 => s_IFIDinst(15 downto 11),
		i_S  => s_regDst,
		o_O  => s_WRmuxO
);

s_ANDo <= s_EXmemMEM(2) and s_EXmemZero; 

s_BshiftO <=  s_extended(29 downto 0) & "00"; 

bAdder: AdderSub_N 
    port map(
        i_D0        => s_IFIDnewPC,           
        i_D1	    => s_BshiftO,	           
        i_addSub    => '0',       
        o_Sum 	    => s_BaddO,	            
        o_Car 	    => s_Carry		 
);

with s_forwardA select s_forwardAMuxOut <= 
	s_RegWrData	when "01",
	s_EXmemALUo     when "10", 
	s_RegWrData	when "11",
	s_IDEXreg1	when others;

with s_forwardB select s_forwardBMuxOut <= 
	s_RegWrData	when "01",
	s_EXmemALUo     when "10", 
	s_RegWrData	when "11",
	s_mux2out	when others;


s_temp_regout2 <= s_aluout when ((s_forwardBranch2(0) = '1') and not(s_forwardBranch2(1) = '1') and (s_Branch = '1')) else
		  s_EXmemALUo when ( not(s_forwardBranch2(0) = '1') and(s_forwardBranch2(1) = '1') and (s_Branch = '1')) else
		  s_regout2;

s_temp_regout1 <= s_aluout when ((s_forwardBranch(0)='1') and not(s_forwardBranch(1)='1') and (s_Branch = '1')) else
		  s_EXmemALUo when (not(s_forwardBranch(0)='1') and (s_forwardBranch(1)='1') and (s_Branch = '1')) else
		  s_regout1;

s_regequal <= '1' when ((s_temp_regout1 = s_temp_regout2) and (s_IFIDinst(31 downto 26) = "000100")) or ((s_temp_regout1 /= s_temp_regout2) and (s_IFIDinst(31 downto 26) = "000101")) else '0';

------------------------------------------------------------------------------------------------------------------

bMux: mux2t1_N port map(
		i_D0 => s_IFIDnewPC,
		i_D1 => s_BaddO,
		i_S  => s_regequal,
		o_O  => s_BmuxO
);

s_JshiftO <= s_IFIDnewPC(31 downto 28) & (s_IFIDinst(25 downto 0) & "00");

jumpmux: mux2t1_N port map(
	i_D0 => s_BmuxO,
	i_D1 => s_JshiftO,
	i_S  => s_j, 
	o_O  => s_JmuxO);

with s_jr select s_JRmuxO <=
	s_JmuxO   	when '0', 
	s_regout1	when '1', 
	s_JmuxO	        when others;


if_id: IFID port map(
       i_Inst    => s_Inst,
       i_CLK     => iCLK,
       i_RST     => iRST,
       i_flush   => s_IFIDflush,
       i_stall   => s_stallIF,
       i_newPC   => s_PCFour, 
       o_Inst    => s_IFIDinst, 
       o_newPC   => s_IFIDnewPC);

mem_wb: memWB port map(
       i_dmem => s_DMemOut,
       i_aluout   => s_EXmemALUo,
       i_newPC    => s_EXmemNewPC,
       i_rdrtmux  => s_EXmemRegoMux, 
       i_halt     => s_EXmemHalt,
       i_CLK      => iCLK,
       i_RST      => iRST,
       i_rd       => s_EXmemrd, 
       i_jrmuxout => s_EXmemJRmuxO, 
       i_memreadwrite => s_EXmemReadWR, 
       i_flush    => s_memwbflush,
       i_stall    => s_memwbstall,
       i_WB       => s_EXmemWB,
       o_dmem     => s_WBdmem,
       o_aluout   => s_WBmemALUo,
       o_NewPC    => s_WBmemNewPC,
       o_rdrtmux  => s_RegWrAddr,
       o_WB       => s_WBmemOut,
       o_jrmuxout => s_memWBjrmuxO,
       o_memreadwrite => s_memWBReadWR,
       o_rd       => s_memwbrd, 
       o_Halt     => s_Halt);


s_RegWr <= s_WBmemOut(1);


ex_mem: EXmem port map(
       i_alu      => s_aluout,
       i_CLK      => iCLK,
       i_RST      => iRST,
       i_WB       => s_IDEXwb,
       i_M        => s_IDEXmem, 
       i_halt     => s_IDEXhalt,
       i_flush    => s_EXmemFlush, 
       i_flush2   => s_flush2,
       i_stall    => s_EXmemStall, 
       i_memreadwrite => s_IDEXReadWRmem,
       i_jrmuxout => s_IDEXJRmuxO,
       i_ExMemMUX => s_forwardBMuxOut,
       i_rdrtMUX  => s_RegWrA_temp,
       i_newPC    => s_IDEXnewPC,
       i_Zero     => s_ZERO, 
       i_rd       => s_IDEXrd,
       o_ExMemMUX => s_EXmemMux,
       o_rdrtMUX  => s_EXmemRegoMux, 
       o_jrmuxout => s_EXmemJRmuxO,
       o_NewPC    => s_EXmemNewPC,
       o_WB       => s_EXmemWB, 
       o_M        => s_EXmemMEM,
       o_Zero     => s_EXmemZero,
       o_memreadwrite => s_EXmemReadWR,
       o_rd       => s_EXmemrd,
       o_alu      => s_EXmemALUo,
       o_Halt     => s_EXmemHalt);

s_tempForwardRegO <= s_EXmemRegoMux; ---------------------------------------------------------------------------

s_DMemWr   <= s_EXmemMEM(1);
s_DMemData <= s_EXmemMux;
s_DMemAddr <= s_EXmemALUo;


id_ex: IDEX port map( 
        i_WB           => s_jr & s_jal & s_RegWr_temp & s_memToReg, 
        i_MEM          => s_Branch & s_DmemWR_temp & s_memRead, 
        i_EX           => s_regDst & s_aluSrc & s_aluControl, 
        i_halt         => s_Halt_temp,
	i_funcS	       => s_beqS & s_j & s_jr & s_ignrOvfl & s_sub, 
        i_reg1out      => s_regout1,
        i_reg2out      => s_regout2,
        i_flush        => s_IDEXflush,
        i_stall        => s_IDEXstall,
	i_newPC        => s_IFIDnewPC, 
        i_Inst         => s_IFIDinst,
        i_signExt      => s_extended, 
	i_RegWriteAddr => s_WRmuxO,
        i_CLK          => iCLK,
        i_RST          => iRST,
        i_jrmuxout     => s_JRmuxO, 
	i_memreadwrite => s_ReadWRmem, 
	o_funcS        => s_IDEXfunc, 
        o_WBSig        => s_IDEXwb,
        o_MEMSig       => s_IDEXmem, 
        o_EX           => s_IDEXex,
	o_reg1out      => s_IDEXreg1,
	o_reg2out      => s_IDEXreg2, 
        o_RegRs        => s_IDEXrs, 
	o_NewPC        => s_IDEXnewPC,
        o_RegRt        => s_IDEXrt, 
        o_signExt      => s_IDEXextend,
        o_RegRd        => s_IDEXrd,
	o_Inst         => s_IDEXinst, 
	o_RegWriteAddr => s_RegWrA_temp,
	o_jrmuxout     => s_IDEXJRmuxO, 
	o_memreadwrite => s_IDEXReadWRmem,
        o_Halt         => s_IDEXhalt);


-----------------------------------------------------------------------------------------------------------------

s_ReadWRmem <= '1' when ((s_IFIDinst(31 downto 26) = "100011") or (s_IFIDinst(31 downto 26) = "101011")) else
		  '0';

hazard: hazardUnit port map(
	IFIDrs           => s_IFIDinst(25 downto 21),
	IFIDrt           => s_IFIDinst(20 downto 16),
	IDEXrd      	 => s_IFIDinst(15 downto 11),
	EXMEMrd          => s_tempForwardRegO, 
	MEMWBrd		 => s_RegWrAddr,
	
	jump       	 => s_j,
	regEqual   	 => s_regequal,
	jal        	 => s_jal,
	jr         	 => s_jr,
	IDEX_jal   	 => s_IDEXwb(2),
	EXMEM_jal  	 => s_EXmemWB(2),
	MEMWB_jal  	 => s_WBmemOut(2),
	IDEX_jr    	 => s_IDEXwb(3),
	EXMEM_jr         => s_EXmemWB(3),
	MEMWB_jr         => s_WBmemOut(3),
	ReadWriteMem     => s_ReadWRmem,
	ex_ReadWriteMem  => s_IDEXReadWRmem,
	mem_ReadWriteMem => s_EXmemReadWR,
	wb_ReadWriteMem  => s_memWBReadWR,
        extendCheck      => s_signExtend,  
 
	stallIF => s_stallIF, 
	stallID => s_IDEXstall,
	stallEX => s_EXmemstall,
	stallWB => s_memWBstall,
	flushIF => s_IFIDflush,
	flushID => s_IDEXflush,
	flushEX => s_EXmemflush,
	flushWB => s_memWBflush,
        flushtemp => s_flush2
);

forward: forwardingUnit port map( 
	IDEXrs          => s_IDEXrs,  
	IDEXrt 		=> s_IDEXrt,
	IDEXWB 		=> s_IDEXwb,
	IDEXrdrt	=> s_RegWrA_temp,
	IFIDrs 		=> s_IFIDinst(25 downto 21),
	IFIDrt 		=> s_IFIDinst(20 downto 16),
	EXMEMrd 	=> s_EXmemRD,
	EXMEMrt 	=> s_tempForwardRegO,
	MEMWBrd 	=> s_memWBrd,
	MEMWBrt 	=> s_RegWrAddr,
	EXMEMWB 	=> s_EXmemWB,
	MEMWBWB 	=> s_WBmemOut,
	ForwardA 	=> s_forwardA,
	ForwardBranch 	=> s_forwardBranch,
	ForwardBranch2	=> s_forwardBranch2,
	ForwardB 	=> s_forwardB
);

end structure;
