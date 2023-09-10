library IEEE;
use IEEE.std_logic_1164.all;

entity memWB is
  generic(N : integer := 32);
  port(i_dmem       : in std_logic_vector(N-1  downto 0);
       i_forward    : in std_logic_vector(N-1 downto 0);
       i_aluout	    : in std_logic_vector(N-1 downto 0);
       i_NewPC      : in std_logic_vector(N-1 downto 0);
       i_rdrtmux    : in std_logic_vector(4 downto 0);
       i_halt	    : in std_logic;
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_WB	    : in std_logic_vector(2 downto 0);
       o_dmem       : out std_logic_vector(N-1  downto 0);
       o_forward    : out std_logic_vector(N-1  downto 0);
       o_aluout	    : out std_logic_vector(N-1 downto 0);
       o_newpc      : out std_logic_vector(N-1 downto 0);
       o_rdrtmux    : out std_logic_vector(4 downto 0);
       o_WB	    : out std_logic_vector(2 downto 0);
       o_halt	    : out std_logic);
end memWB;

architecture behavior of memWB is

  component dffg_N
    generic(N : integer := 32);
    port(i_CLK        : in std_logic;  
         i_RST        : in std_logic;    
         i_WE         : in std_logic;     
         i_D          : in std_logic_vector(N-1 downto 0);  
         o_Q          : out std_logic_vector(N-1 downto 0));   
  end component;
  component dffg
    port(i_CLK        : in std_logic;  
         i_RST        : in std_logic;     
         i_WE         : in std_logic;     
         i_D          : in std_logic;     
         o_Q          : out std_logic);   
  end component;

begin

  RegRDRT: dffg_N
  generic map(N => 5) 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => '1',
           i_D   => i_rdrtmux,
           o_Q   => o_rdrtmux);

  RegALU: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => '1',
           i_D   => i_aluout,
           o_Q   => o_aluout);

  DMem: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => '1',
           i_D   => i_dmem,
           o_Q   => o_dmem);

  FRD: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => '1',
           i_D   => i_forward,
           o_Q   => o_forward);

  RegNewPC: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => '1',
           i_D   => i_NewPC,
           o_Q   => o_NewPC);

  RegWB: dffg_N 
  generic map(N => 3)
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => '1',
           i_D   => i_WB,
           o_Q   => o_WB);

  RegHalt : dffg
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => '1',
      i_D     => i_halt,
      o_Q     => o_halt);
  
end behavior;
