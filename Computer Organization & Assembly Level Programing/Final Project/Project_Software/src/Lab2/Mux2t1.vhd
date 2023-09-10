-------------------------------------------------------------------------
-- Henry Duwe
-- Department of Electrical and Computer Engineering
-- Iowa State University
-------------------------------------------------------------------------


-- 2to1Mux.vhd
-------------------------------------------------------------------------
-- DESCRIPTION: 
--
--
-- 


--  by Wonjun Choi.
-------------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;

entity Mux2t1 is

  port(i0               : in std_logic;
       i1               : in std_logic;
       s                : in std_logic;
       oY               : out std_logic);

end Mux2t1;

architecture structural of Mux2t1 is

  component andg2
    port(i_A          : in std_logic;
         i_B          : in std_logic;
         o_F          : out std_logic);
  end component;

  component org2
    port(i_A          : in std_logic;
         i_B          : in std_logic;
         o_F          : out std_logic);
  end component;

  component invg
    port(i_A          : in std_logic;
         o_F          : out std_logic);
  end component;

  -- Signal 
  signal s_inv_out  : std_logic;

  signal s_and_out1 : std_logic;

  signal s_and_out2 : std_logic;

begin

  ---------------------------------------------------------------------------
  -- Level 0: 
  ---------------------------------------------------------------------------

  g_inv1 : invg
    port map (i_A     => s,
              o_F     => s_inv_out);
  
  ---------------------------------------------------------------------------
  -- Level 1:
  ---------------------------------------------------------------------------

  g_and1 : andg2
    port map (i_A     => i0,
              i_B     => s_inv_out,
              o_F     => s_and_out1);
  
  g_and2 : andg2
    port map (i_A     => i1,
              i_B     => s,
              o_F     => s_and_out2);
  
  ---------------------------------------------------------------------------
  -- Level 2:
  ---------------------------------------------------------------------------
 
  g_org1 : org2
    port map (i_A     => s_and_out1,
              i_B     => s_and_out2,
              o_F     => oY);



end structural;
