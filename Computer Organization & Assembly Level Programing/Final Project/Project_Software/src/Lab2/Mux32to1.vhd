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

library work;
use work.reg_p.all;

entity Mux32to1 is
  port(m_i: in  t_regt;
       m_s: in std_logic_vector(4 downto 0);
       m_o: out std_logic_vector(31 downto 0));

end Mux32to1;

architecture dataflow of Mux32to1 is

begin

  with m_s select

     m_o <= m_i(0)  when "00000",
            m_i(1)  when "00001",
            m_i(2)  when "00010",
            m_i(3)  when "00011",
            m_i(4)  when "00100",
            m_i(5)  when "00101",
            m_i(6)  when "00110",
            m_i(7)  when "00111",
            m_i(8)  when "01000",
            m_i(9)  when "01001",
            m_i(10) when "01010",
            m_i(11) when "01011",
            m_i(12) when "01100",
            m_i(13) when "01101",
            m_i(14) when "01110",
            m_i(15) when "01111",
            m_i(16) when "10000",
            m_i(17) when "10001",
            m_i(18) when "10010",
            m_i(19) when "10011",
            m_i(20) when "10100",
            m_i(21) when "10101",
            m_i(22) when "10110",
            m_i(23) when "10111",
            m_i(24) when "11000",
            m_i(25) when "11001",
            m_i(26) when "11010",
            m_i(27) when "11011",
            m_i(28) when "11100",
            m_i(29) when "11101",
            m_i(30) when "11110",
            m_i(31) when "11111",
            "00000000000000000000000000000000" when others;


  
  -- This process handles the asyncrhonous reset and
  -- synchronous write. We want to be able to reset 
  -- our processor's registers so that we minimize
  -- glitchy behavior on startup.
--  process (i_CLK, i_RST)
--  begin
--    if (i_RST = '1') then
--      s_Q <= '0'; -- Use "(others => '0')" for N-bit values
--    elsif (rising_edge(i_CLK)) then
--      s_Q <= s_D;
--    end if;

--  end process;
  
end dataflow;
