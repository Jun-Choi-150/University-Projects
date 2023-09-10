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

entity mux9to1 is
  Port (i_D0 : in  std_logic_vector (31 downto 0);
	i_D1 : in  std_logic_vector(31 downto 0);
	i_D2 : in  std_logic_vector(31 downto 0);
	i_D3 : in  std_logic_vector (31 downto 0);
	i_D4 : in  std_logic_vector (31 downto 0);
	i_D5 : in  std_logic_vector(31 downto 0);
	i_D6 : in  std_logic_vector(31 downto 0);
	i_D7 : in  std_logic_vector (31 downto 0);
	i_D8 : in  std_logic_vector (31 downto 0);
        i_S  : in  std_logic_vector (3 downto 0);
        i_Y  : out  std_logic_vector(31 downto 0));
end mux9to1;

architecture dataflow of mux9to1 is

begin

with i_S select
i_Y <=   i_D0 when "0011",
         i_D1 when "1010",
         i_D2 when "0000",
         i_D3 when "1001", 
	 i_D4 when "1011", 
         i_D5 when "1100", 
         i_D6 when "0001",
         i_D7 when "1110",
	 i_D8 when "0100",
	 i_D0 when others;
end dataflow;
