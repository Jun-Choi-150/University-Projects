library IEEE;
use IEEE.std_logic_1164.all;

entity zero is

   port(i_In        : in std_logic_vector(31 downto 0);   
        o_Out       : out std_logic);
   

end zero;

architecture structure of zero is

begin


  with i_In select 
    o_Out <= 
     '1' when x"00000000",
     '0' when others;


end structure;
