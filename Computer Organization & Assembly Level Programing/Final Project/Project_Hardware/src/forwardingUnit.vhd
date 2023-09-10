library IEEE;
use IEEE.std_logic_1164.all;

entity forwardingUnit is
  port( IDEXrs 	 	: in std_logic_vector(4 downto 0);
	IDEXWB  	: in std_logic_vector(3 downto 0);
	IDEXrt  	: in std_logic_vector(4 downto 0);
	IDEXrdrt	: in std_logic_vector(4 downto 0);
	IFIDrs		: in std_logic_vector(4 downto 0);
	IFIDrt		: in std_logic_vector(4 downto 0);
	EXMEMrd 	: in std_logic_vector(4 downto 0);
	EXMEMrt 	: in std_logic_vector(4 downto 0);
	MEMWBrd 	: in std_logic_vector(4 downto 0);
	MEMWBrt 	: in std_logic_vector(4 downto 0);
	EXMEMWB 	: in std_logic_vector(3 downto 0);
	MEMWBWB         : in std_logic_vector(3 downto 0);
	ForwardA        : out std_logic_vector(1 downto 0);
	ForwardBranch   : out std_logic_vector(1 downto 0);
	ForwardBranch2  : out std_logic_vector(1 downto 0);
	ForwardB        : out std_logic_vector(1 downto 0)
); 

end forwardingUnit;

architecture structure of forwardingUnit is

begin


  ForwardA <= "01" when (MEMWBWB(1) = '1')  and (Memwbrd = idexrs) and (exmemrd /= idexrs or (exmemwb(1) = '0')) else
              "10" when ((EXMEMWB(1) = '1') and (((EXMEMrt /= "00000") and (EXMEMrt = IDEXrs)))) else
              "01" when (MEMWBWB(1) = '1')  and (Memwbrd = idexrs) and (exmemrd /= idexrs or (exmemwb(1) = '0')) else
              "00";

  ForwardB <= "01" when (MEMWBWB(1) = '1')  and (Memwbrd = idexrt) and (exmemrd /= idexrt or (exmemwb(1) = '0')) else
              "10" when ((exmemwb(1) = '1') and (exmemrd = idexrt)) else
              "01" when (MEMWBWB(1) = '1')  and (Memwbrd = idexrt) and (exmemrd /= idexrt or (exmemwb(1) = '0')) else
              "00";
               

  ForwardBranch <= "01" when ((IDEXWB(1) = '1') and (IDEXrdrt /= "00000") and (IDEXrdrt = IFIDrs)) else
		   "10" when ((EXMEMWB(1) = '1') and (EXMEMrt /= "00000") and (IFIDrs = EXMEMrt)) else
		   "00";

  ForwardBranch2 <= "01" when ((IDEXWB(1) = '1') and (IDEXrdrt /= "00000") and (IDEXrdrt = IFIDrt)) else
		    "10" when ((EXMEMWB(1) = '1') and (EXMEMrt /= "00000") and (IFIDrt = EXMEMrt)) else 
		    "00";

  
end structure;





