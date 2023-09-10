library IEEE;
use IEEE.std_logic_1164.all;

entity memWB is
  generic(N : integer := 32);
  port(i_dmem       : in std_logic_vector(N-1  downto 0);
       i_aluout	    : in std_logic_vector(N-1 downto 0);
       i_NewPC      : in std_logic_vector(N-1 downto 0);
       i_rdrtmux    : in std_logic_vector(4 downto 0);
       i_flush	    : in std_logic;
       i_stall      : in std_logic;
       i_halt	    : in std_logic;
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_WB	    : in std_logic_vector(3 downto 0);
       i_rd	    : in std_logic_vector(4 downto 0);
       i_jrmuxout   : in std_logic_vector(N-1  downto 0);
       i_memreadwrite : in std_logic;
       o_dmem       : out std_logic_vector(N-1  downto 0);
       o_forward    : out std_logic_vector(N-1  downto 0);
       o_aluout	    : out std_logic_vector(N-1 downto 0);
       o_newpc      : out std_logic_vector(N-1 downto 0);
       o_rdrtmux    : out std_logic_vector(4 downto 0);
       o_rd	    : out std_logic_vector(4 downto 0);
       o_WB	    : out std_logic_vector(3 downto 0);
       o_jrmuxout   : out std_logic_vector(N-1  downto 0);
       o_memreadwrite : out std_logic;
       o_halt	    : out std_logic);

end memWB;

architecture behavior of memWB is

  component dffg_N
    generic(N : integer := 32);
    port(i_CLK        : in std_logic;  
         i_RST        : in std_logic;    
         i_WE         : in std_logic;     
         i_D          : in std_logic_vector(N-1 downto 0);  
         o_Q          : out std_logic_vector(N-1 downto 0));   
  end component;
  component dffg
    port(i_CLK        : in std_logic;  
         i_RST        : in std_logic;     
         i_WE         : in std_logic;     
         i_D          : in std_logic;     
         o_Q          : out std_logic);   
  end component;

  signal temp_we, temp_halt_Reg, temp_memreadwrite_Reg : std_logic;
  signal temp_ALUreg, temp_DMem, temp_nextPCReg, temp_jrmux_reg : std_logic_vector(31 downto 0);
  signal temp_RDRTreg, temp_reReg : std_logic_vector(4 downto 0);
  signal temp_WBreg : std_logic_vector(3 downto 0);


begin

  temp_we        <= ((i_stall) or not(i_flush));
  temp_RDRTreg   <= i_rdrtmux when (i_flush='1') else "00000";
  temp_ALUreg    <= i_aluout when (i_flush='1') else x"00000000";
  temp_DMem      <= i_dmem when (i_flush='1') else x"00000000";
  temp_reReg     <= i_rd when (i_flush='1') else "00000";
  temp_nextPCReg <= i_NewPC when (i_flush='1') else x"00000000";
  temp_WBreg     <= i_WB when (i_flush='1') else "0000";
  temp_jrmux_reg <= i_jrmuxout when (i_flush='1') else x"00000000";
  temp_halt_Reg  <= i_halt when (i_flush='1') else '0';
  temp_memreadwrite_Reg <= i_memreadwrite when (i_flush='1') else '0';

  RegRDRT: dffg_N
  generic map(N => 5) 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_RDRTreg,
           o_Q   => o_rdrtmux);

  RegALU: dffg_N
  generic map(N => 32) 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_ALUreg,
           o_Q   => o_aluout);

  DMem: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_DMem,
           o_Q   => o_dmem);

  RegRD: dffg_N
  generic map(N => 5)
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_reReg,
           o_Q   => o_rd);

  RegNewPC: dffg_N
  generic map(N => 32) 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => temp_we,
           i_D   => temp_nextPCReg,
           o_Q   => o_NewPC);

  RegWB: dffg_N 
  generic map(N => 4)
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => temp_we,
           i_D   => temp_WBreg,
           o_Q   => o_WB);

  RegHalt : dffg
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_halt_Reg,
      o_Q     => o_halt);

  jrmux_reg: dffg_N
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => temp_we,
           i_D   => temp_jrmux_reg,
           o_Q   => o_jrmuxout);

  memreadwrite_Reg : dffg
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_memreadwrite_Reg,
      o_Q     => o_memreadwrite); 

end behavior;
