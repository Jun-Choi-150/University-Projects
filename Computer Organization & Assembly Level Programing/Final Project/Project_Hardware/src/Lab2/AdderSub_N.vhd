

library IEEE;
use IEEE.std_logic_1164.all;

entity AdderSub_N is
  generic(N : integer := 32); -- Generic of type integer for input/output data width. Default value is 32.
  port(i_D0           : in std_logic_vector(N-1 downto 0);
       i_D1           : in std_logic_vector(N-1 downto 0);
       i_addSub       : in std_logic;
       o_Sum          : out std_logic_vector(N-1 downto 0);
       o_Car          : out std_logic);

end AdderSub_N;

architecture structural of AdderSub_N is

  component invg_N is
    port(in_A          : in std_logic_vector;
         out_F         : out std_logic_vector);
  end component;

  component mux2t1_N is
    port(i_D0         : in std_logic_vector;
         i_D1         : in std_logic_vector;
         i_S          : in std_logic;
         o_O          : out std_logic_vector);
  end component;

  component FullAdder_N is
    port(i_D0           : in std_logic_vector;
         i_D1           : in std_logic_vector;
         i_C            : in std_logic;
         o_Sum          : out std_logic_vector;
         o_Car          : out std_logic);
  end component;


signal s_mux      : std_logic_vector(N-1 downto 0);
signal s_adder    : std_logic_vector(N-1 downto 0);


begin

  g_invg: invg_N
    port MAP(in_A             => i_D1,
             out_F            => s_mux);


  g_mux2t1: mux2t1_N
    port MAP(i_D0              => i_D1,
             i_D1              => s_mux,
             i_S               => i_addSub,
             o_O               => s_adder);

  g_fulladder: FullAdder_N
    port MAP(i_D0              => s_adder,
             i_D1              => i_D0,
             i_C               => i_addSub,
             o_Sum             => o_Sum,
             o_Car             => o_Car);

  
end structural;
