library IEEE;
use IEEE.std_logic_1164.all;

entity replqb is
  port(	
	i_Input : in std_logic_vector(7 downto 0);
	o_Output : out std_logic_vector(31 downto 0));

end replqb;




architecture mixed of replqb is

begin

  o_Output <= i_input & i_input & i_input & i_input;
	
end mixed;
