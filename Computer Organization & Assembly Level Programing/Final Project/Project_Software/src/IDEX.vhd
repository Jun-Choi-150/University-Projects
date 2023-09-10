library IEEE;
use IEEE.std_logic_1164.all;

entity IDEX is
  generic(N : integer := 32);
  port(
        i_WB		: in std_logic_vector(2 downto 0);
        i_MEM		: in std_logic_vector(2 downto 0);
        i_EX	        : in std_logic_vector(5 downto 0);
        i_halt		: in std_logic;
        i_reg1out    	: in std_logic_vector(N-1  downto 0);
        i_reg2out    	: in std_logic_vector(N-1  downto 0);
	i_NewPC 	: in std_logic_vector(N-1 downto 0);
        i_inst       	: in std_logic_vector(N-1  downto 0);
        i_signExt    	: in std_logic_vector(N-1  downto 0);
	i_regwriteaddr  : in std_logic_vector(4 downto 0);
        i_CLK        	: in std_logic;
        i_RST        	: in std_logic;
	i_funcS		: in std_logic_vector(4 downto 0);
	o_funcS		: out std_logic_vector(4 downto 0);
        o_WBSig      	: out std_logic_vector(2 downto 0);
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

begin

  ImmReg: dffg_N
  generic map(N => 32)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => '1',
    i_D     => i_signExt,
    o_Q     => o_signExt);

  RegRD: dffg_N
  generic map(N => 5)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => '1',
    i_D     => i_regwriteaddr,
    o_Q     => o_RegRD);

  WB_Reg: dffg_N
  generic map(N => 3)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => '1',
    i_D     => i_WB,
    o_Q     => o_WBSig);

  MEM_Reg: dffg_N
  generic map(N => 3)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => '1',
    i_D     => i_MEM,
    o_Q     => o_MEMSig);

  EX_Reg: dffg_N
  generic map(N => 6)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => '1',
    i_D     => i_EX,
    o_Q     => o_EX);

  RegOut1 : dffg_N
  generic map(N => 32)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => '1',
    i_D     => i_reg1out,
    o_Q     => o_reg1out);

  RegOut2 : dffg_N
  generic map(N => 32)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => '1',
    i_D     => i_reg2out,
    o_Q     => o_reg2out);

  RegRS : dffg_N
  generic map(N => 5)
  port map(
    i_CLK   => i_CLK,
    i_RST   => i_RST,
    i_WE    => '1',
    i_D     => i_inst(25 downto 21),
    o_Q     => o_RegRs);

  RegRT : dffg_N
  generic map(N => 5)
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => '1',
      i_D     => i_inst(20 downto 16),
      o_Q     => o_RegRt);

  RegWRAddr : dffg_N
  generic map(N => 5)
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => '1',
      i_D     => i_regwriteaddr,
      o_Q     => o_regwriteaddr);

  RegNewPC : dffg_N
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => '1',
      i_D     => i_NewPC,
      o_Q     => o_NewPC);

  RegInst : dffg_N
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => '1',
      i_D     => i_inst,
      o_Q     => o_inst);

  RegHalt : dffg
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => '1',
      i_D     => i_halt,
      o_Q     => o_halt);

RegFunc : dffg_N
  generic map(N => 5)
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => '1',
      i_D     => i_funcS,
      o_Q     => o_funcS);
  
    
  
end behavior;
