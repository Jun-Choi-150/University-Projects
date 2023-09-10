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

entity FullAdder_N is
  generic(N : integer := 32); -- Generic of type integer for input/output data width. Default value is 32.
  port(i_D0           : in std_logic_vector(N-1 downto 0);
       i_D1           : in std_logic_vector(N-1 downto 0);
       i_C            : in std_logic;
       o_Sum          : out std_logic_vector(N-1 downto 0);
       o_Car          : out std_logic);

end FullAdder_N;

architecture structural of FullAdder_N is

  component FullAdder is
    port(iA                        : in std_logic;
         iB 		           : in std_logic;
         iC 		           : in std_logic;
         o_Sum 		           : out std_logic;
         o_Carry 		   : out std_logic);
  end component;

signal s_carry : std_logic_vector(N downto 0);

begin

  s_carry(0) <= i_C;
  o_Car <= s_carry(N);

  -- Instantiate N mux instances.
  G_NBit_MUX: for i in 0 to N-1 generate
    MUXI: FullAdder port map(
              iA         => i_D0(i),      
              iB         => i_D1(i),  
              iC         => s_carry(i),
              o_Sum      => o_Sum(i),  
              o_Carry    => s_carry(i+1));

  end generate G_NBit_MUX;
  
end structural;
