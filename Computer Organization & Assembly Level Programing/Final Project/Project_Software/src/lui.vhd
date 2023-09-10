library IEEE;
use IEEE.std_logic_1164.all;

entity lui is

   port(i_in        : in std_logic_vector(31 downto 0);   
        o_lui       : out std_logic_vector(31 downto 0));   

end lui;

architecture mixed of lui is

begin

  o_lui <= i_in(15 downto 0) & "0000000000000000";

end mixed;


