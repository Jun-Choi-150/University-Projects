library IEEE;
use IEEE.std_logic_1164.all;

entity ALU is
   generic(N: integer := 32);
   port(i_alu_A		: in std_logic_vector(31 downto 0);  
        i_alu_B     	: in std_logic_vector(31 downto 0);
        i_shamt		: in std_logic_vector(4 downto 0);    
        i_aluOP         : in std_logic_vector(3 downto 0); 
  	i_addSub	: in std_logic;
	i_Dir   	: in std_logic;  
	i_Arith  	: in std_logic;
	i_beqS		: in std_logic;
	i_s_ignrOvfl	: in std_logic;
        i_inst          : in std_logic_vector(31 downto 0);
	o_overflow	: out std_logic;
	o_Zero		: out std_logic;
        o_F        	: out std_logic_vector(N-1 downto 0));   

end ALU;

architecture mixed of ALU is

  component invg is
    port(i_A          : in std_logic;
         o_F          : out std_logic); 
  end component;
------------------------------------------------------------
  component andg2 is 
    port(i_A          : in std_logic;
       i_B          : in std_logic;
       o_F          : out std_logic);
  end component;
  -----------------------------------------------------------------
  component Mux2t1 is
    port(i0               : in std_logic;
         i1               : in std_logic;
         s                : in std_logic;
         oY               : out std_logic); 
  end component;

  component mux2t1_N is
    generic(N : integer := 32);
    port(i_D0         : in std_logic_vector(N-1 downto 0);
         i_D1         : in std_logic_vector(N-1 downto 0);
         i_S          : in std_logic;
         o_O          : out std_logic_vector(N-1 downto 0));
  end component;

  component AdderSub_N is 
    generic(N : integer := 32);
    port(i_D0           : in std_logic_vector(N-1 downto 0);
         i_D1           : in std_logic_vector(N-1 downto 0);
         i_addSub       : in std_logic;
         o_Sum          : out std_logic_vector(N-1 downto 0);
         o_Car          : out std_logic);
   end component;

  component lui is 
    port(i_in        	: in std_logic_vector(31 downto 0);   
         o_lui      	: out std_logic_vector(31 downto 0)); 
  end component;  

  component BarrelShifter is 
    port(i_input	: in std_logic_vector(31 downto 0);
         i_shamt        : in std_logic_vector(4 downto 0);
         i_Dir		: in std_logic;  
         i_Arith	: in std_logic;
         o_Out          : out std_logic_vector(31 downto 0));
   end component;

  component slt is 
    port(i_addSub	: in std_logic_vector(31 downto 0);
         i_overFlow     : in std_logic;
         o_Out		: out std_logic_vector(31 downto 0));  
   end component;

  component zero is 
    port(i_In		: in std_logic_vector(31 downto 0);
         o_Out		: out std_logic);  
   end component;

  component xorg2 is
    port(i_A          : in std_logic;
         i_B          : in std_logic;
         o_F          : out std_logic);
  end component;

  component xor32 is
    port(i_A          : in std_logic_vector(31 downto 0);
         i_B          : in std_logic_vector(31 downto 0);
         o_F          : out std_logic_vector(31 downto 0));
  end component;

  component or32 is
    port(i_A          : in std_logic_vector(31 downto 0);
         i_B          : in std_logic_vector(31 downto 0);
         o_F          : out std_logic_vector(31 downto 0));
  end component;

  component nor32 is
    port(i_A          : in std_logic_vector(31 downto 0);
         i_B          : in std_logic_vector(31 downto 0);
         o_F          : out std_logic_vector(31 downto 0));
  end component;

  component and32 is
    port(i_A          : in std_logic_vector(31 downto 0);
         i_B          : in std_logic_vector(31 downto 0);
         o_F          : out std_logic_vector(31 downto 0));
  end component;

  component replqb is
    port(i_Input      : in std_logic_vector(7 downto 0);
	 o_Output     : out std_logic_vector(31 downto 0));
  end component;

  component mux9to1 is
    Port (i_D0 : in  std_logic_vector (31 downto 0);
	  i_D1 : in  std_logic_vector(31 downto 0);
	  i_D2 : in  std_logic_vector(31 downto 0);
	  i_D3 : in  std_logic_vector (31 downto 0);
	  i_D4 : in  std_logic_vector (31 downto 0);
	  i_D5 : in  std_logic_vector(31 downto 0);
	  i_D6 : in  std_logic_vector(31 downto 0);
	  i_D7 : in  std_logic_vector (31 downto 0);
	  i_D8 : in  std_logic_vector (31 downto 0);
          i_S  : in  std_logic_vector (3 downto 0);
          I_Y  : out  std_logic_vector(31 downto 0));
  end component;

------------------------------------------------------------------------------
-- signal
------------------------------------------------------------------------------

  -- Output signal from Components  
  signal  s_o_lui, s_o_barShift, s_o_addSub, s_o_slt, s_o_and, s_o_or, s_o_xor, s_o_nor, s_o_replqb : std_logic_vector(31 downto 0);

  signal  s_o_carry, c, s_o_zero, s_overflow, s_o_invZero : std_logic;

  signal s_tempSynth : std_logic_vector(1 downto 0);
  signal s_tempSynth2 : std_logic_vector(3 downto 0);





------------------------------------------------------------------------------
-- Mapping
------------------------------------------------------------------------------

begin

------------------------
   --Zero from addSub  
------------------------
  g_zero: zero
    port MAP(i_In               => s_o_addSub,
             o_Out              => s_o_zero);

------------------------
   -- Bne Beq 
------------------------

-- xor gate 
s_tempSynth <= s_o_zero & i_beqS; -- ADDED
 with s_tempSynth select o_Zero <= '1' when "00",
					'1' when "11",
					'0' when others ;

------------------------
   -- lui 
------------------------
  g_lui: lui
    port MAP(i_in              => i_alu_B,
             o_lui             => s_o_lui);

------------------------
   -- BarrerShifter 
------------------------

  g_barShift: BarrelShifter
    port MAP(i_input           => i_alu_B,
             i_shamt           => i_shamt,
             i_Dir             => i_Dir,
             i_Arith           => i_Arith,
             o_Out	       => s_o_barShift);

------------------------
  -- AdderSubtractor 
------------------------

  g_addSub: AdderSub_N
    port MAP(i_D0              => i_alu_A,
             i_D1              => i_alu_B,
             i_addSub          => i_addSub,
             o_Sum             => s_o_addSub,
	     o_Car	       => s_o_carry);

------------------------
  -- Overflow 
------------------------

s_tempSynth2 <= i_alu_A(31) & (i_alu_B(31) xor i_addSub) & s_o_addSub(31) & i_s_ignrOvfl; 
 with s_tempSynth2 select s_overflow <= 
	'1' when "0010",
	'1' when "1100",
	'0' when others;

------------------------
  -- slt
------------------------
  g_slt: slt
    port MAP(i_addSub          => s_o_addSub,
             i_overFlow        => s_overflow, 
             o_Out             => s_o_slt);

------------------------
  -- Gate Functions
------------------------

  g_xor: xor32
    port MAP(i_A              => i_alu_A,
             i_B              => i_alu_B,
             o_F              => s_o_xor);

  g_or: or32
    port MAP(i_A              => i_alu_A,
             i_B              => i_alu_B,
             o_F              => s_o_or);

  g_nor: nor32
    port MAP(i_A              => i_alu_A,
             i_B              => i_alu_B,
             o_F              => s_o_nor);

  g_and: and32
    port MAP(i_A              => i_alu_A,
             i_B              => i_alu_B,
             o_F              => s_o_and);

  g_replqb: replqb
    port MAP(i_Input 	      => i_inst(23 downto 16), 
             o_Output 	      => s_o_replqb);

  g_mux9: mux9to1
    Port MAP(i_D0		      => s_o_lui,
	     i_D1		      => s_o_slt,
	     i_D2		      => s_o_addSub,
	     i_D3		      => s_o_xor,
	     i_D4		      => s_o_or,
	     i_D5		      => s_o_nor,
	     i_D6		      => s_o_and,
	     i_D7		      => s_o_barShift,
	     i_D8 		      => s_o_replqb,
             i_S		      => i_aluOP,
             I_Y		      => o_F);


  o_overflow <= s_overflow;

end mixed;
