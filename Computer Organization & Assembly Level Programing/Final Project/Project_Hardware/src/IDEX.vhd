library IEEE;
use IEEE.std_logic_1164.all;

entity IDEX is
  generic(N : integer := 32);
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

end IDEX;

architecture behavior of IDEX is

  component dffg_N
    generic(N : integer := 32);
    port(i_CLK        : in std_logic;    
         i_RST        : in std_logic;    
         i_WE         : in std_logic;     
         i_D          : in std_logic_vector(N-1 downto 0);   
         o_Q          : out std_logic_vector(N-1 downto 0));   
  end component;

  component dffg
    port(
        i_CLK        : in std_logic;     
        i_RST        : in std_logic;     
        i_WE         : in std_logic;    
        i_D          : in std_logic;   
        o_Q          : out std_logic);
  end component;

----------------------------------------------------------------------------------------------
  signal temp_we, temp_halt, temp_memreadwrite : std_logic;
  signal temp_D, temp_signExt, temp_reg1out, temp_reg2out, temp_nextpc, temp_jrmuxout : std_logic_vector(31 downto 0);
  signal temp_rs,temp_rt,temp_rd, temp_regwriteaddr : std_logic_vector(4 downto 0);
  signal temp_WB : std_logic_vector(3 downto 0);
  signal temp_EX : std_logic_vector(5 downto 0);
  signal temp_MEM : std_logic_vector(2 downto 0);

begin

  temp_we           <= ((i_stall) or not(i_flush));
  temp_D            <= i_inst when (i_flush='1') else x"00000000";
  temp_signExt      <= i_signExt when (i_flush='1') else x"00000000";
  temp_WB           <= i_WB when (i_flush='1') else "0000";
  temp_MEM          <= i_MEM when (i_flush='1') else "000";
  temp_EX           <= i_EX when (i_flush='1') else "000000";
  temp_reg1out      <= i_reg1out when (i_flush='1') else x"00000000";
  temp_reg2out      <= i_reg2out when (i_flush='1') else x"00000000";
  temp_rs           <= i_inst(25 downto 21) when (i_flush='1') else "00000"; 
  temp_rt           <= i_inst(20 downto 16) when (i_flush='1') else "00000";
  temp_rd           <= i_inst(15 downto 11) when (i_flush='1') else "00000"; 
  temp_regwriteaddr <= i_regwriteaddr when (i_flush='1') else "00000";
  temp_nextpc       <= i_NewPC when (i_flush='1') else x"00000000";
  temp_jrmuxout     <= i_jrmuxout when (i_flush='1') else x"00000000";
  temp_halt         <= i_halt when (i_flush='1') else '0';
  temp_memreadwrite <= i_memreadwrite when (i_flush = '1') else  '1';

  ImmReg: dffg_N
  generic map(N => 32)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => temp_we,
    i_D     => i_signExt,
    o_Q     => o_signExt);

  RegRD: dffg_N
  generic map(N => 5)
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_rd,
      o_Q     => o_RegRd);

  WB_Reg: dffg_N
  generic map(N => 4)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => temp_we,
    i_D     => i_WB,
    o_Q     => o_WBSig);

  MEM_Reg: dffg_N
  generic map(N => 3)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => temp_we,
    i_D     => i_MEM,
    o_Q     => o_MEMSig);

  EX_Reg: dffg_N
  generic map(N => 6)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => temp_we,
    i_D     => i_EX,
    o_Q     => o_EX);

  RegOut1 : dffg_N
  generic map(N => 32)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => temp_we,
    i_D     => i_reg1out,
    o_Q     => o_reg1out);

  RegOut2 : dffg_N
  generic map(N => 32)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => temp_we,
    i_D     => i_reg2out,
    o_Q     => o_reg2out);

  RegRS : dffg_N
  generic map(N => 5)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => temp_we,
    i_D     => temp_rs,
    o_Q     => o_RegRs);

  RegRT : dffg_N
  generic map(N => 5)
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_rt,
      o_Q     => o_RegRt);

  RegWRAddr : dffg_N
  generic map(N => 5)
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_regwriteaddr,
      o_Q     => o_regwriteaddr);

  RegNewPC : dffg_N
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_nextpc,
      o_Q     => o_NewPC);

  RegInst : dffg_N
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_D,
      o_Q     => o_inst);

  RegJR : dffg_N
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_jrmuxout,
      o_Q     => o_jrmuxout);

  RegHalt : dffg
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_halt,
      o_Q     => o_halt);

  RegFunc : dffg_N 
  generic map(N => 5)
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => '1',
      i_D     => i_funcS,
      o_Q     => o_funcS);

  memreadwrite_Reg : dffg
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_memreadwrite,
      o_Q     => o_memreadwrite);
    
  
end behavior;
