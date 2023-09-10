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

entity BarrelShifter is
  port(i_input          : in std_logic_vector(31 downto 0);
       i_shamt        : in std_logic_vector(4 downto 0);

	--0 right, 1 left
	i_Dir   :  in std_logic;  
	
	--0 logical , 1 arithmatic
	i_Arith   :  in std_logic;

       o_Out          : out std_logic_vector(31 downto 0));

end BarrelShifter;


architecture mixed of BarrelShifter is


signal s_a1, s_a2, s_a4, s_a8 : std_logic_vector(31 downto 0);

signal s_temp2 : std_logic_vector(1 downto 0); -- 2 bits
signal s_temp4 : std_logic_vector(3 downto 0); -- 4 bits
signal s_temp8  : std_logic_vector(7 downto 0); -- 8 bits
signal s_temp16  : std_logic_vector(15 downto 0); -- 16 bits


signal s_tempS, s_tempS2, s_tempS3, s_tempS4, s_tempS5 : std_logic_vector(2 downto 0);


begin

s_temp2 <= (others => i_input(31)); -- 2 bits
s_temp4 <= (others => i_input(31)); -- 4 bits
s_temp8 <= (others => i_input(31)); -- 8 bits
s_temp16 <= (others => i_input(31)); -- 16 bits

s_tempS <= i_Dir & i_Arith & i_shamt(0);
with s_tempS select
	s_a1 <= 
	"0" & i_input(31 downto 1) when "001",
	i_input(31) & i_input(31 downto 1) when "011", 
	i_input(30 downto 0) & "0" when "101", 
	i_input(30 downto 0) & "0" when "111",
	
	i_input when others;
	

s_tempS2 <= i_Dir & i_Arith & i_shamt(1);
with s_tempS2 select
	s_a2 <= 
	"00" & s_a1(31 downto 2) when "001",
	s_temp2 & s_a1(31 downto 2) when "011",
	s_a1(29 downto 0) & "00" when "101",
	s_a1(29 downto 0) & "00" when "111",

	s_a1 when others;
	
s_tempS3 <= i_Dir & i_Arith & i_shamt(2);
with s_tempS3 select
	s_a4 <= 
	"0000" & s_a2(31 downto 4) when "001",
	s_temp4 & s_a2(31 downto 4) when "011",
	s_a2(27 downto 0) & "0000" when "101",
	s_a2(27 downto 0) & "0000" when "111",

	s_a2 when others;

s_tempS4 <= i_Dir & i_Arith & i_shamt(3);
with s_tempS4 select
	s_a8 <= 
	"00000000" & s_a4(31 downto 8) when "001",
	s_temp8 & s_a4(31 downto 8) when "011",
	s_a4(23 downto 0) & "00000000" when "101",
	s_a4(23 downto 0) & "00000000" when "111",

	s_a4 when others;

s_tempS5 <= i_Dir & i_Arith & i_shamt(4);
with s_tempS5 select
	o_Out <= 
	"0000000000000000" & s_a8(31 downto 16) when "001",
	s_temp16 & s_a8(31 downto 16) when "011",
	s_a8(15 downto 0) & "0000000000000000" when "101" ,
	s_a8(15 downto 0) & "0000000000000000" when "111" ,
	
	s_a8 when others;


end mixed;
