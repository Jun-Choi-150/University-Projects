library IEEE;
use IEEE.std_logic_1164.all;

entity slt is

   port(i_addSub        : in std_logic_vector(31 downto 0);   
        i_overFlow      : in std_logic;   
        o_Out           : out std_logic_vector(31 downto 0));   

end slt;

architecture structural of slt is

   signal s_addSub31	: std_logic;
   signal s_tempSynth   : std_logic_vector(1 downto 0);

begin

  s_addSub31 <= i_addSub(31);
s_tempSynth <= s_addSub31 & i_overFlow;
  with s_tempSynth select 
    o_Out <= 
     x"00000001" when "10",
     x"00000001" when "01",
--     x"00000001" when "11",
     x"00000000" when others;


end structural;


