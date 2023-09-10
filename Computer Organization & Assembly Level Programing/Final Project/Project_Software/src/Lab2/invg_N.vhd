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

entity invg_N is
  generic(N : integer := 32); -- Generic of type integer for input/output data width. Default value is 32.
  port(in_A          : in std_logic_vector(N-1 downto 0);
       out_F         : out std_logic_vector(N-1 downto 0));

end invg_N;

architecture structural of invg_N is

--  component Mux2t1 is
--    port(i0               : in std_logic;
--         i1               : in std_logic;
--         s                : in std_logic;
--         oY               : out std_logic);
--  end component;

  component invg is
     
     port(i_A          : in std_logic;
          o_F          : out std_logic);

  end component;

--  signal s_o_O         : std_logic_vector(N-1 downto 0);
	
begin

  -- Instantiate N mux instances.

  G_NBit_MUX: for i in 0 to N-1 generate
    MUXI: invg port map(
              i_A      => in_A(i),    -- All instances share the same select input.
              o_F      => out_F(i));    -- ith instance's data 0 input hooked up to ith data 0 input.
  end generate G_NBit_MUX;


--  G_NBit_MUX: for i in 0 to N-1 generate
--    MUXI: Mux2t1 port map(
--              i0      => i_D0(i),    -- All instances share the same select input.
--              i1      => i_D1(i),    -- ith instance's data 0 input hooked up to ith data 0 input.
--              s       => i_s,        -- ith instance's data 1 input hooked up to ith data 1 input.
--              oY      => s_o_O(i));  -- ith instance's data output hooked up to ith data output.
--  end generate G_NBit_MUX;

--  G_NBit_Invg: for i in 0 to N-1 generate
--    MUXI: invg port map(
--              i_A      => s_o_O(i),      -- All instances share the same select input.
--              o_F      => o_O(i));  -- ith instance's data 0 input hooked up to ith data 0 input.
--  end generate G_NBit_Invg;
 
end structural;
