--Hazard Detection Unit


library IEEE;
use IEEE.std_logic_1164.all;

entity hazardUnit is
  port( IFIDrs  	: std_logic_vector(4 downto 0);
	IFIDrt  	: std_logic_vector(4 downto 0);
	IDEXrd  	: std_logic_vector(4 downto 0);
	EXMEMrd 	: std_logic_vector(4 downto 0);
	MEMWBrd 	: std_logic_vector(4 downto 0);
	
	jump    	: in std_logic;
	regEqual	: in std_logic;
	jal    		: in std_logic;
	jr		: in std_logic;
	IDEX_jal 	: in std_logic;
	EXMEM_jal	: in std_logic;
	MEMWB_jal	: in std_logic;
	IDEX_jr 	: in std_logic;
	EXMEM_jr	: in std_logic;
	MEMWB_jr	: in std_logic;
	ReadWriteMem 	: in std_logic;
	ex_ReadWriteMem : in std_logic;
	mem_ReadWriteMem: in std_logic;
	wb_ReadWriteMem : in std_logic;
        extendCheck     : in std_logic;

	stallIF : out std_logic;
	stallID : out std_logic;
	stallEX : out std_logic;
	stallWB : out std_logic;
	flushIF : out std_logic;
	flushID : out std_logic;
	flushEX : out std_logic;
	flushWB : out std_logic;
        flushtemp : out std_logic); 

end hazardUnit;

architecture structure of hazardUnit is

begin

  stallIF <= '0' when ( IDEX_jal='1' or EXMEM_jal='1' or IDEX_jr='1' or EXMEM_jr='1' or ReadWriteMem='1' or ex_ReadWriteMem='1' or mem_ReadWriteMem='1' or wb_ReadWriteMem='1') else
	     '1';

  stallID <= '1';
  stallEX <= '1';
  stallWB <= '1';

  flushIF <= '0' when (regEqual = '1' or jump = '1' or jal = '1' or jr = '1' or ReadWriteMem = '1') else
	     '0' when (regEqual = '1') else
	     '1';
  flushID <= '1';
  flushEX <= '1';
  flushWB <= '1';
  
  flushtemp <= '0' when extendCheck = '1' else
             '1';

end structure;






