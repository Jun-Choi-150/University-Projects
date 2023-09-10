library IEEE;
use IEEE.std_logic_1164.all;

entity EXmem is
  generic(N : integer := 32;
          gCLK_HPER   : time := 30 ns);
  port(i_alu        : in std_logic_vector(N-1  downto 0);
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_WB	    : in std_logic_vector(2 downto 0);
       i_M	    : in std_logic_vector(2 downto 0);
       i_halt	    : in std_logic;
       i_ExMemMUX   : in std_logic_vector(N-1 downto 0);
       i_rdrtMUX    : in std_logic_vector(4 downto 0);
       i_NewPC	    : in std_logic_vector(N-1 downto 0);
       i_Zero	    : in std_logic;
       o_WB	    : out std_logic_vector(2 downto 0);
       o_M	    : out std_logic_vector(2 downto 0);
       o_Zero	    : out std_logic;
       o_alu        : out std_logic_vector(N-1  downto 0);
       o_ExMemMUX   : out std_logic_vector(N-1 downto 0);
       o_rdrtMUX    : out std_logic_vector(4 downto 0);
       o_NewPC	    : out std_logic_vector(N-1 downto 0);
       o_halt	    : out std_logic);
end EXmem;

architecture behavior of EXmem is

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

-------------------------------------------------------------------------------------------

begin

  RegZero: dffg 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => '1',
           i_D   => i_Zero,
           o_Q   => o_Zero);

  RegMux: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => '1',
           i_D   => i_ExMemMUX,
           o_Q   => o_ExMemMUX);

  rdrtMuxReg: dffg_N 
  generic map(N => 5)
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => '1',
           i_D   => i_rdrtMUX,
           o_Q   => o_rdrtMUX);

  RegALU: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => '1',
           i_D   => i_alu,
           o_Q   => o_alu);

  RegWB: dffg_N
  generic map(N => 3) 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => '1',
           i_D   => i_WB,
           o_Q   => o_WB);

  RegMEM: dffg_N
  generic map(N => 3) 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => '1',
           i_D   => i_M,
           o_Q   => o_M);

  RegNewPC: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => '1',
           i_D   => i_NewPC,
           o_Q   => o_NewPC);

  RegHalt : dffg
  port map(
      	   i_CLK   => i_CLK,
   	   i_RST   => i_RST,
     	   i_WE    => '1',
      	   i_D     => i_halt,
           o_Q     => o_halt);
  
end behavior;
