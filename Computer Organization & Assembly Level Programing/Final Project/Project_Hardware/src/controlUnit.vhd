library IEEE;
use IEEE.std_logic_1164.all;

entity controlUnit is

  port(opcode     : in std_logic_vector(5 downto 0);
       func       : in std_logic_vector(5 downto 0);
       aluCtrl    : out std_logic_vector(3 downto 0);
       Branch     : out std_logic;
       beqS       : out std_logic;
       j	  : out std_logic;
       jr         : out std_logic;
       jal	  : out std_logic;
       memRead    : out std_logic;
       memToReg   : out std_logic;
       memWrite   : out std_logic;
       aluSrc     : out std_logic;
       regWrite   : out std_logic;
       signExtend : out std_logic;
       regDst     : out std_logic;
       reset	  : out std_logic;
       sub	  : out std_logic;
       ignrOvfl   : out std_logic;
       moven	  : out std_logic;
       halt       : out std_logic);
       
end controlUnit;

architecture mixed of controlUnit is

begin

process(opcode, func)

begin
        aluCtrl    <= "0000";
        regDst     <= '0';
        aluSrc     <= '0';
        memToReg   <= '0';
        regWrite   <= '0';
        memRead    <= '0';
        memWrite   <= '0';
	signExtend <= '0';
        Branch     <= '0';
	beqS       <= '0';
	j          <= '0';
	jr         <= '0';
	jal        <= '0';
	moven      <= '0';
	sub	   <= '0';
	ignrOvfl   <= '0';
	reset	   <= '0';

--------------------------------R Format------------------------------------

    if opcode = "000000" then 

		--add
        if func = "100000" then 
            aluCtrl    <= "0000";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '1';
            Branch     <= '0';
            j          <= '0';
	    jr         <= '0';
            jal        <= '0';
		
		--addu
		elsif func = "100001" then 
            aluCtrl    <= "0000";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
	    ignrOvfl   <= '1';

		--sub
		elsif func = "100010" then 
            aluCtrl    <= "0000";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '1';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
            jal        <= '0';
			
		--subu
		elsif func = "100011" then 
            aluCtrl    <= "0000";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
	    ignrOvfl   <= '1';

		--and
		elsif func = "100100" then 
            aluCtrl    <= "0001";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
		
		--or		
		elsif func = "100101" then 
            aluCtrl    <= "1011";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
	
		--xor
		elsif func = "100110" then 
            aluCtrl    <= "1001";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
            jr         <= '0';
	    jal        <= '0';
            ignrOvfl   <= '1';

		--movn
		elsif func = "001011" then --100110
            aluCtrl    <= "1101";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
	    moven      <= '1';
	    ignrOvfl   <= '1';
	
		--nor
		elsif func = "100111" then 
            aluCtrl    <= "1100";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
	    ignrOvfl   <= '1';
	
		--sll
		elsif func = "000000" then  --0x2
            aluCtrl    <= "1110";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
            ignrOvfl    <= '1';
			
		--srl
		elsif func = "000010" then --000000 
            aluCtrl    <= "1110";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
	    ignrOvfl    <= '1';
	
		--sra
		elsif func = "000011" then --000001
            aluCtrl    <= "1110";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
	    jr         <= '0';
	    jal        <= '0';
	    ignrOvfl    <= '1';
			
		--slt
		elsif func = "101010" then 
            aluCtrl    <= "1010";
            regDst     <= '1';
            aluSrc     <= '0';
            memToReg   <= '0';
            regWrite   <= '1';
            memRead    <= '0';
            memWrite   <= '0';
	    signExtend <= '0';
            Branch     <= '0';
	    j          <= '0';
            jr         <= '0';
            jal        <= '0';
            ignrOvfl    <= '1';

	   
		--jr
	elsif func = "001000" then 
           aluCtrl    <= "0000";
           regDst     <= '0';
           aluSrc     <= '0';
           memToReg   <= '0';
           regWrite   <= '0';
           memRead    <= '0';
           memWrite   <= '0';
	   signExtend <= '0';
           Branch     <= '0';
	   j          <= '0';
           jr         <= '1';
           jal        <= '0';
    end if;

--------------------------------I Format------------------------------------

	--addi
    elsif (opcode = "001000") then 
	aluCtrl      <= "0000";
	regDst       <= '0';
        aluSrc       <= '1';
        memToReg     <= '0';
        regWrite     <= '1';
        memRead      <= '0';
        memWrite     <= '0';
	signExtend   <= '1';
        Branch       <= '0';
	j            <= '0';
	jr           <= '0';
	jal          <= '0';
	--ignrOvfl   <= '1';
		
	--addiu
    elsif opcode = "001001" then 
	aluCtrl      <= "0000";
	regDst       <= '0';
        aluSrc       <= '1';
        memToReg     <= '0';
        regWrite     <= '1';
        memRead      <= '0';
        memWrite     <= '0';
	signExtend   <= '1';
        Branch       <= '0';
	j            <= '0';
	jr           <= '0';
	jal          <= '0';
	ignrOvfl   <= '1';
			
	--lw
    elsif opcode = "100011" then 
	aluCtrl     <= "0000";
	regDst      <= '0';
        aluSrc      <= '1';
        memToReg    <= '1';
        regWrite    <= '1';
        memRead     <= '0';
        memWrite    <= '0';
        signExtend  <= '1'; ------------------?
        Branch      <= '0';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';
		
	--sw
    elsif opcode = "101011" then 
	aluCtrl     <= "0000";
	regDst      <= '0';
        aluSrc      <= '1';
        memToReg    <= '0';
        regWrite    <= '0';
        memRead     <= '0';
        memWrite    <= '1';
	signExtend  <= '1';
        Branch      <= '0';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';
		
	--lui
    elsif opcode = "001111" then 
	aluCtrl     <= "0011";
	regDst      <= '0';
        aluSrc      <= '1';
        memToReg    <= '0';
        regWrite    <= '1';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '1'; 
        Branch      <= '0';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';
        ignrOvfl   <= '1';
			
	--andi
    elsif opcode = "001100" then
        aluCtrl     <= "0001";
        regDst      <= '0';
        aluSrc      <= '1';
        memToReg    <= '0';
        regWrite    <= '1';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '0'; -------------?
        Branch      <= '0';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';
		
	--ori
    elsif opcode = "001101" then
	aluCtrl     <= "1011";
        regDst      <= '0';
        aluSrc      <= '1';
        memToReg    <= '0';
        regWrite    <= '1';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '0'; 
        Branch      <= '0';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';
		
	--xori
    elsif opcode = "001110" then 
	aluCtrl     <= "1001";
        regDst      <= '0';
        aluSrc      <= '1';
        memToReg    <= '0';
        regWrite    <= '1';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '0'; ------------?
        Branch      <= '0';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';

	
	--slti	
    elsif opcode = "001010" then 
	aluCtrl     <= "1010";
        regDst      <= '0';
        aluSrc      <= '1';
        memToReg    <= '0';
        regWrite    <= '1';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '1';
        Branch      <= '0';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';
	sub	    <= '1';
        ignrOvfl    <= '1';
	
	--beq	
    elsif opcode = "000100" then 
	aluCtrl     <= "0000";
        regDst      <= '0';
        aluSrc      <= '0';
        memToReg    <= '0';
        regWrite    <= '0';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '0';
        Branch      <= '1';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';
	beqS        <= '1';
       	sub	    <= '1';
        ignrOvfl    <= '1';

			
	--bne
    elsif opcode = "000101" then 
	aluCtrl     <= "0000";
        regDst      <= '0';
        aluSrc      <= '0';
        memToReg    <= '0';
        regWrite    <= '0';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '1';
        Branch      <= '1';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';
       	sub	    <= '1';
	ignrOvfl    <= '1';
		
	--j		
    elsif opcode = "000010" then 
	aluCtrl     <= "0000";
        regDst      <= '0';
        aluSrc      <= '0';
        memToReg    <= '0';
        regWrite    <= '0';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '0';
        Branch      <= '0';
	j           <= '1';
	jr          <= '0';
	jal         <= '0';
		
	--jal		
    elsif opcode = "000011" then --
	aluCtrl     <= "0000";
        regDst      <= '0';
        aluSrc      <= '0';
        memToReg    <= '0';
        regWrite    <= '1';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '1';
        Branch      <= '0';
	j           <= '1';
	jr          <= '0';
	jal         <= '1';
	
	--repl.qb	
    elsif opcode = "011111" then
	aluCtrl     <= "0100";
        regDst      <= '1';
        aluSrc      <= '0';
        memToReg    <= '0';
        regWrite    <= '1';
        memRead     <= '0';
        memWrite    <= '0';
	signExtend  <= '0';
        Branch      <= '0';
	j           <= '0';
	jr          <= '0';
	jal         <= '0';

	--halt
    elsif opcode = "010100" then 
        aluCtrl    <= "0000";
        regDst     <= '0';
        aluSrc     <= '0';
        memToReg   <= '0';
        regWrite   <= '0';
        memRead    <= '0';
        memWrite   <= '0';
	signExtend <= '0';
        Branch     <= '0';
	beqS       <= '0';
	j          <= '0';
	jr         <= '0';
	jal        <= '0';
	moven      <= '0';
	sub	   <= '0';
	ignrOvfl   <= '0';
	reset	   <= '1';
		
    end if;
	
    end process;
  
end mixed;
