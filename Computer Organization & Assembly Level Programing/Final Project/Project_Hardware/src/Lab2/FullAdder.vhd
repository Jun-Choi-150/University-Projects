-------------------------------------------------------------------------
-- Henry Duwe
-- Department of Electrical and Computer Engineering
-- Iowa State University
-------------------------------------------------------------------------


-- TPU_MV_Element.vhd
-------------------------------------------------------------------------
-- DESCRIPTION: This file contains an implementation of a processing
-- element for the systolic matrix-vector multiplication array inspired 
-- by Google's TPU.
--
--
-- NOTES:
-- 1/14/19 by H3::Design created.
-------------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;


entity FullAdder is
  port(iA                          : in std_logic;
       iB 		           : in std_logic;
       iC 		           : in std_logic;
       o_Sum 		           : out std_logic;
       o_Carry 		           : out std_logic);

end FullAdder;

architecture structural of FullAdder is
  
  component xorg2
    port(i_A          : in std_logic;
         i_B          : in std_logic;
         o_F          : out std_logic);
  end component;

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

-- SIGNAL 

  signal s_xor1             : std_logic;
  signal s_and1, s_and2     : std_logic;

begin

  ---------------------------------------------------------------------------
  -- Level 0
  ---------------------------------------------------------------------------
 
  g_xor: xorg2
    port MAP(i_A             => iA,
             i_B             => iB,
             o_F             => s_xor1);


  ---------------------------------------------------------------------------
  -- Level 1
  ---------------------------------------------------------------------------
  g_and: andg2
    port MAP(i_A             => iC,
             i_B             => s_xor1,
             o_F             => s_and1);
  
  g_and2: andg2
    port MAP(i_A             => iA,
             i_B             => iB,
             o_F             => s_and2);

    
  ---------------------------------------------------------------------------
  -- Level 2
  ---------------------------------------------------------------------------
  g_xor2: xorg2
    port MAP(i_A             => s_xor1,
             i_B             => iC,
             o_F             => o_Sum);

  g_or: org2
    port MAP(i_A             => s_and1,
             i_B             => s_and2,
             o_F             => o_Carry);
    

  end structural;
