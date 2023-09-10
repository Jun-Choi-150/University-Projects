-------------------------------------------------------------------------
-- Henry Duwe
-- Department of Electrical and Computer Engineering
-- Iowa State University
-------------------------------------------------------------------------


-- mux2t1_N.vhd
-------------------------------------------------------------------------
-- DESCRIPTION: This file contains an implementation of an N-bit wide 2:1
-- mux using structural VHDL, generics, and generate statements.
--
--
-- NOTES:
-- 1/6/20 by H3::Created.
-------------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;

entity Move is
  port(	
	i_rt : in std_logic_vector(31 downto 0);
	o_regWriteO: out std_logic);

end Move;




architecture mixed of Move is

begin


  o_regWriteO <= '1' when (i_rt /= x"00000000") else 
                 '0';
	
end mixed;
