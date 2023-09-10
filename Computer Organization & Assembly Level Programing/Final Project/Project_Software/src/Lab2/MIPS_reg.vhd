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

library work;
use work.reg_p.all;

entity MIPS_Reg is
  port(i_rs                        : in std_logic_vector(4 downto 0);
       i_rt 		           : in std_logic_vector(4 downto 0);
       i_w_adrs 	           : in std_logic_vector(4 downto 0);
       i_w_data 	           : in std_logic_vector(31 downto 0);
       i_w_enable	           : in std_logic;
       i_clk	           	   : in std_logic;
       i_rst                       : in std_logic;
       o_rs 	                   : out std_logic_vector(31 downto 0);
       o_rt	                   : out std_logic_vector(31 downto 0));

end MIPS_Reg;

architecture structural of MIPS_Reg is
  
  component dffg_N
     port(i_CLK        : in  std_logic;     -- Clock input
          i_RST        : in  std_logic;     -- Reset input
          i_WE         : in  std_logic;     -- Write enable input
          i_D          : in  std_logic_vector(31 downto 0);   -- Data value input
          o_Q          : out std_logic_vector(31 downto 0));   -- Data value output
  end component;

  component decoder_32
     port(d_i: in  std_logic_vector(4 downto 0);
          d_e: in std_logic;
          d_o: out std_logic_vector(31 downto 0));
  end component;

  component Mux32to1
     port(m_i: in  t_regt;
          m_s: in std_logic_vector(4 downto 0);
          m_o: out std_logic_vector(31 downto 0));
  end component;



-- SIGNAL 

  signal s_dec_o       : std_logic_vector(31 downto 0);
  signal s_reg_o       : t_regt;
  signal s_mux_o       : std_logic_vector(31 downto 0);
  signal s_dff_0       : std_logic_vector(31 downto 0) := (others => '0');


begin

  ---------------------------------------------------------------------------
  -- Level 0
  ---------------------------------------------------------------------------
 
  g_Decoder: decoder_32
    port MAP(d_i             => i_w_adrs,
             d_e             => i_w_enable,
             d_o             => s_dec_o);


  ---------------------------------------------------------------------------
  -- Level 1
  ---------------------------------------------------------------------------

    g_Reg0: dffg_N
      port MAP(i_CLK           => i_clk,
               i_RST           => i_rst,
               i_WE            => s_dec_o(0),
               i_D             => s_dff_0,
               o_Q             => s_reg_o(0));

G_32Bit_Reg: for i in 1 to 31 generate
    g_Reg: dffg_N
      port MAP(i_CLK           => i_clk,
               i_RST           => i_rst,
               i_WE            => s_dec_o(i),
               i_D             => i_w_data,
               o_Q             => s_reg_o(i));
  
    end generate;
  ---------------------------------------------------------------------------
  -- Level 2
  ---------------------------------------------------------------------------
  g_Mux1: Mux32to1
    port MAP(m_i             => s_reg_o,
             m_s             => i_rs,
             m_o             => o_rs);

  g_Mux2: Mux32to1
    port MAP(m_i             => s_reg_o,
             m_s             => i_rt,
             m_o             => o_rt);
    

end structural;
