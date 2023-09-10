library IEEE;
use IEEE.std_logic_1164.all;

entity Fetch is 
  generic(N: integer:= 32);
  port(WE		 : in std_logic;

       Jump_WE           : in std_logic;-- to 1 bit
	jr_WE           : in std_logic;
       jal_WE		: in std_logic; 
       Branch_WE         : in std_logic;
       reg31             : in std_logic_vector(N-1 downto 0);
       IMM 	         : in std_logic_vector(N-1 downto 0);
       INSTR             : in std_logic_vector(N-1 downto 0);
       i_CLK             : in std_logic;
       i_RST             : in std_logic;
       o_Address        : out std_logic_vector(N-1 downto 0));
end Fetch;

	
architecture structural of Fetch is

  component mux2t1_N is
    generic(N : integer := 32);
    port(i_D0         : in std_logic_vector(N-1 downto 0);
         i_D1         : in std_logic_vector(N-1 downto 0);
	 i_S          : in std_logic;
         o_O          : out std_logic_vector(N-1 downto 0));
  end component;

  component AdderSub_N is 
    generic(N : integer := 32);
    port(i_D0         : in std_logic_vector(N-1 downto 0);
      	 i_D1         : in std_logic_vector(N-1 downto 0);
	 i_addSub     : in std_logic;
      	 o_Sum        : out std_logic_vector(N-1 downto 0);
      	 o_Car      : out std_logic);
  end component;

  component pc is 
   generic(N : integer := 32);
   port(i_CLK        : in std_logic;  
        i_RST        : in std_logic;
        i_WE         : in std_logic;    
        i_IN         : in std_logic_vector(N-1 downto 0);   
        o_OUT        : out std_logic_vector(N-1 downto 0));   
   end component;

---------------------------------------------------------------------
--signal
---------------------------------------------------------------------

  signal s_PC          : std_logic_vector(31 downto 0);
  signal s_addO        : std_logic_vector(31 downto 0);
  signal s_addCarO     : std_logic;
  signal s_jump        : std_logic_vector(25 downto 0);
  signal s_jumpAdd     : std_logic_vector(31 downto 0);
  signal s_branch      : std_logic_vector(31 downto 0);
  signal s_branchAdd   : std_logic_vector(31 downto 0);
  signal s_branchAddO  : std_logic_vector(31 downto 0);
  signal s_branchAddCarO: std_logic;
  signal s_muxBranch   : std_logic_vector(31 downto 0);
  signal s_muxJ        : std_logic_vector(31 downto 0);
  signal s_muxJR       : std_logic_vector(31 downto 0);
  

begin 

    pcReg: pc
    generic map(N => 32)
    port map(i_CLK 	=> i_CLK,
	     i_RST 	=> i_RST,
	     i_WE  	=> WE,
	     i_IN  	=> s_muxJR,
	     o_OUT 	=> s_PC); 


    pcAdd: AdderSub_N
    generic map(N => 32)
    port map(i_AddSub	=>'0',
	     i_D0	=> s_PC,
             i_D1       => x"00000004",
             o_Sum      => s_addO,
             o_Car 	=> s_addCarO);


     s_branchAdd <= IMM(29 downto 0) & '0' & '0';
     s_branch <= IMM;


    pcBranch: AdderSub_N
    generic map(N => 32)
    port map(i_AddSub	=>'0',
	     i_D0       => s_addO,
	     i_D1       => s_branchAdd,
	     o_Sum     	=> s_branchAddO,
	     o_Car 	=> s_branchAddCarO);


    muxBranch: mux2t1_N 
    port map(i_S 	    => Branch_WE,
	     i_D0	    => s_addO, -- PC
	     i_D1	    => s_branchAddO, --Branch Address
	     o_O 	    => s_muxBranch);


    s_jumpAdd (31 downto 0) <= s_addO(31 downto 28) & INSTR(25 downto 0) & '0' & '0';
    s_jump(25 downto 0)	    <= INSTR(25 downto 0);


    mux_J: mux2t1_N 
    port map(i_S 	   => Jump_WE,
             i_D0	   => s_muxBranch,
             i_D1	   => s_jumpAdd,
             o_O 	   => s_muxJ);


    mux_JR: mux2t1_N 
    port map(i_S 	   => jr_WE,
	     i_D0	   => s_muxJ,
	     i_D1	   => reg31,
	     o_O 	   => s_muxJR);


   o_Address <= s_PC;


end structural;
