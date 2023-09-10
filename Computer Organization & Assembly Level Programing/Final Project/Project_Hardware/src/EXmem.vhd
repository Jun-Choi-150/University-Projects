library IEEE;
use IEEE.std_logic_1164.all;

entity EXmem is
  generic(N : integer := 32;
          gCLK_HPER   : time := 30 ns);
  port(i_alu        : in std_logic_vector(N-1  downto 0);
       i_CLK        : in std_logic;
       i_RST        : in std_logic;
       i_WB	    : in std_logic_vector(3 downto 0);
       i_M	    : in std_logic_vector(2 downto 0);
       i_halt	    : in std_logic;
       i_ExMemMUX   : in std_logic_vector(N-1 downto 0);
       i_rdrtMUX    : in std_logic_vector(4 downto 0);
       i_NewPC	    : in std_logic_vector(N-1 downto 0);
       i_jrmuxout   : in std_logic_vector(N-1 downto 0);
       i_memreadwrite : in std_logic;
       i_Zero	    : in std_logic;
       i_rd	    : in std_logic_vector(4 downto 0);
       i_flush      : in std_logic;
       i_flush2     : in std_logic;
       i_stall      : in std_logic;
       o_WB	    : out std_logic_vector(3 downto 0);
       o_M	    : out std_logic_vector(2 downto 0);
       o_Zero	    : out std_logic;
       o_alu        : out std_logic_vector(N-1  downto 0);
       o_ExMemMUX   : out std_logic_vector(N-1 downto 0);
       o_rdrtMUX    : out std_logic_vector(4 downto 0);
       o_NewPC	    : out std_logic_vector(N-1 downto 0);
       o_rd	    : out std_logic_vector(4 downto 0);
       o_jrmuxout   : out std_logic_vector(N-1 downto 0);
       o_memreadwrite : out std_logic;
       o_halt	    : out std_logic);
end EXmem;

architecture behavior of EXmem is

  component dffg_N
    generic(N : integer := 32);
    port(i_CLK        : in std_logic;     
         i_RST        : in std_logic;    
         i_WE         : in std_logic;    
         i_D          : in std_logic_vector(N-1 downto 0);   
         o_Q          : out std_logic_vector(N-1 downto 0));   
  end component;

  component dffg
    port(
       i_CLK        : in std_logic;     
       i_RST        : in std_logic;   
       i_WE         : in std_logic;  
       i_D          : in std_logic;     
       o_Q          : out std_logic);
  end component;

  signal temp_we, temp_zeroReg, temp_halt_Reg, temp_memreadwrite_Reg : std_logic;
  signal temp_muxReg, temp_aluReg, temp_nextpcReg, temp_jrmux_Reg : std_logic_vector(31 downto 0);
  signal temp_rdrtMuxReg, temp_rdReg : std_logic_vector(4 downto 0);
  signal temp_WBReg : std_logic_vector(3 downto 0);
  signal temp_MEMReg : std_logic_vector(2 downto 0);

begin

  temp_we         <= ((i_stall) or not(i_flush));
  temp_zeroReg    <= i_Zero when (i_flush='1') else '0';
  temp_muxReg     <= i_ExMemMUX when (i_flush='1') else x"00000000";
  temp_rdrtMuxReg <= i_rdrtMUX when (i_flush='1') else "00000";
  temp_rdReg      <= i_rd when (i_flush2='1') else "00000"; --this should go to 0
  temp_aluReg     <= i_alu when (i_flush='1') else x"00000000";
  temp_WBReg      <= i_WB when (i_flush='1') else "0000";
  temp_MEMReg     <= i_M when (i_flush='1') else "000";
  temp_nextpcReg  <= i_NewPC when (i_flush='1') else x"00000000";
  temp_jrmux_Reg  <= i_jrmuxout when (i_flush='1') else x"00000000";
  temp_halt_Reg   <= i_halt when (i_flush='1') else '0';--
  temp_memreadwrite_Reg <= i_memreadwrite when (i_flush='1') else '0';

  RegZero: dffg 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_zeroReg,
           o_Q   => o_Zero);

  RegMux: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_muxReg,
           o_Q   => o_ExMemMUX);

  rdrtMuxReg: dffg_N 
  generic map(N => 5)
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_rdrtMuxReg,
           o_Q   => o_rdrtMUX);

  RegRD: dffg_N
  generic map(N => 5)
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_rdReg,
           o_Q   => o_rd);

  RegALU: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
           i_WE  => temp_we,
           i_D   => temp_aluReg,
           o_Q   => o_alu);

  RegWB: dffg_N
  generic map(N => 4) 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => temp_we,
           i_D   => temp_WBReg,
           o_Q   => o_WB);

  RegMEM: dffg_N
  generic map(N => 3) 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => temp_we,
           i_D   => temp_MEMReg,
           o_Q   => o_M);

  RegNewPC: dffg_N 
  port map(i_CLK => i_CLK, 
           i_RST => i_RST,
	   i_WE  => temp_we,
           i_D   => temp_nextpcReg,
           o_Q   => o_NewPC);

  jrmux_Reg : dffg_N
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_jrmux_Reg,
      o_Q     => o_jrmuxout);

  RegHalt : dffg
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_halt_Reg,
      o_Q     => o_halt);

  memreadwrite_Reg : dffg
  port map(
      i_CLK   => i_CLK,
      i_RST   => i_RST,
      i_WE    => temp_we,
      i_D     => temp_memreadwrite_Reg,
      o_Q     => o_memreadwrite); 
  
end behavior;
