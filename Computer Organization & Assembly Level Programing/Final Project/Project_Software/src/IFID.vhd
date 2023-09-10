library IEEE;
use IEEE.std_logic_1164.all;

entity IFID is
  generic(N : integer := 32;
          gCLK_HPER   : time := 50 ns);
  port(i_inst       : in std_logic_vector(N-1  downto 0);
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_newPC	    : in std_logic_vector(N-1 downto 0);
       o_inst       : out std_logic_vector(N-1  downto 0);
       o_newPC     : out std_logic_vector(N-1  downto 0));
end IFID;

architecture behavior of IFID is

  component dffg_N
    port(i_CLK        : in std_logic;    
         i_RST        : in std_logic;     
         i_WE         : in std_logic;     
         i_D          : in std_logic_vector(N-1 downto 0);   
         o_Q          : out std_logic_vector(N-1 downto 0)); 
  end component;

-------------------------------------------------------------------------------------------------

begin

  RegInst: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => '1',
           i_D   => i_inst,
           o_Q   => o_inst);

  RegPC: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => '1',
           i_D   => i_newPC,
           o_Q   => o_newPC);
  
end behavior;
