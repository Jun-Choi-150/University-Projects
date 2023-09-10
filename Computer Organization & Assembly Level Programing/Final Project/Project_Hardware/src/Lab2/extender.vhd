-- Quartus Prime VHDL Template
-- Single-port RAM with single read/write address

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity extender is
	port(i_sign	: in std_logic;
	     i_orgn     : in std_logic_vector(15 downto 0);
             o_extd     : out std_logic_vector(31 downto 0));

end extender;

architecture behavior of extender is


begin

  process (i_sign, i_orgn)
  begin 

	for i in 0 to 15 loop
	   o_extd(i) <= i_orgn(i);
	end loop;

	for i in 16 to 31 loop
           o_extd(i) <= (i_orgn(15) and i_sign);
	end loop;
  end process; 

end behavior;
