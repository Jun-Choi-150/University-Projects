library IEEE;
use IEEE.std_logic_1164.all;

entity PC is
   generic(N: integer := 32);
   port(i_CLK        : in std_logic;  
        i_RST        : in std_logic;
        i_WE         : in std_logic;    
        i_IN         : in std_logic_vector(N-1 downto 0);   
        o_OUT        : out std_logic_vector(N-1 downto 0));   

end PC;

architecture structure of PC is
  signal s_IN        : std_logic_vector(N-1 downto 0);
  signal s_OUT       : std_logic_vector(N-1 downto 0);

begin

  --o_OUT <= s_OUT;
  
  with i_WE select
    s_IN <= i_IN when '1',
            s_OUT when others;
   
   
  process (i_CLK, i_RST)
  begin
    if (i_RST = '1') then
        s_OUT <= x"00400000";
    elsif (rising_edge(i_CLK)) then
        s_OUT <= s_IN;
    end if;

  end process;


  o_OUT <= s_OUT;

end structure;
