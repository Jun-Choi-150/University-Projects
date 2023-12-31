-------------------------------------------------------------------------
-- Joseph Zambreno
-- Department of Electrical and Computer Engineering
-- Iowa State University
-------------------------------------------------------------------------


-- dffg.vhd
-------------------------------------------------------------------------
-- DESCRIPTION: This file contains an implementation of an edge-triggered
-- flip-flop with parallel access and reset.
--
--
-- NOTES:
-- 8/19/16 by JAZ::Design created.
-- 11/25/19 by H3:Changed name to avoid name conflict with Quartus
--          primitives.
-------------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;

entity decoder_32 is
  port(d_i: in  std_logic_vector(4 downto 0);
       d_e: in std_logic; 
       d_o: out std_logic_vector(31 downto 0));

end decoder_32;

architecture dataflow of decoder_32 is

signal s_d_o       : std_logic_vector(31 downto 0);

begin

  with d_i select
    s_d_o <= "00000000000000000000000000000001" when "00000",
             "00000000000000000000000000000010" when "00001",
             "00000000000000000000000000000100" when "00010",
             "00000000000000000000000000001000" when "00011",
             "00000000000000000000000000010000" when "00100",
             "00000000000000000000000000100000" when "00101",
             "00000000000000000000000001000000" when "00110",
             "00000000000000000000000010000000" when "00111",
             "00000000000000000000000100000000" when "01000",
             "00000000000000000000001000000000" when "01001",
             "00000000000000000000010000000000" when "01010",
             "00000000000000000000100000000000" when "01011",
             "00000000000000000001000000000000" when "01100",
             "00000000000000000010000000000000" when "01101",
             "00000000000000000100000000000000" when "01110",
             "00000000000000001000000000000000" when "01111",
             "00000000000000010000000000000000" when "10000",
             "00000000000000100000000000000000" when "10001",
             "00000000000001000000000000000000" when "10010",
             "00000000000010000000000000000000" when "10011",
             "00000000000100000000000000000000" when "10100",
             "00000000001000000000000000000000" when "10101",
             "00000000010000000000000000000000" when "10110",
             "00000000100000000000000000000000" when "10111",
             "00000001000000000000000000000000" when "11000",
             "00000010000000000000000000000000" when "11001",
             "00000100000000000000000000000000" when "11010",
             "00001000000000000000000000000000" when "11011",
             "00010000000000000000000000000000" when "11100",
             "00100000000000000000000000000000" when "11101",
             "01000000000000000000000000000000" when "11110",
             "10000000000000000000000000000000" when "11111",
             "00000000000000000000000000000000" when others;

  
    d_o <= s_d_o when (d_e = '1') else "00000000000000000000000000000000";

  
end dataflow;
