library IEEE;
use IEEE.std_logic_1164.all;

entity IFID is
  generic(N : integer := 32;
          gCLK_HPER   : time := 30 ns);
  port(i_inst       : in std_logic_vector(N-1  downto 0);
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_flush      : in std_logic;
       i_stall      : in std_logic;
       i_newPC	    : in std_logic_vector(N-1 downto 0);
       o_inst       : out std_logic_vector(N-1  downto 0);
       o_newPC     : out std_logic_vector(N-1  downto 0));
end IFID;

architecture behavior of IFID is

  signal s_tempOut, s_tempInst : std_logic_vector(31 downto 0);
  signal s_tempWE   : std_logic;
  
  component dffg_N
    port(i_CLK        : in std_logic;    
         i_RST        : in std_logic;     
         i_WE         : in std_logic;     
         i_D          : in std_logic_vector(N-1 downto 0);   
         o_Q          : out std_logic_vector(N-1 downto 0)); 
  end component;

-------------------------------------------------------------------------------------------------

begin

  s_tempWE  <= ((i_stall) or not(i_flush));
  s_tempOut <= i_inst when (i_flush = '1') else x"00000000";

  RegInst: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => s_tempWE,
           i_D   => s_tempOut,
           o_Q   => s_tempInst);

  o_inst <= s_tempInst;

  RegPC: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => '1',
           i_D   => i_newPC,
           o_Q   => o_newPC);
  
end behavior;
