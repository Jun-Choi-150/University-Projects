package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Wonjun Choi
 *
 */
class TownCellTest {
	
	/*
	 * The first town after town creation is as follows. 
	 * 
	 *      1  
	 *    O R O R
	 *    E E C O
	 * 2  E S O S
	 *    E O R R
	 * 
	 * Based on (2,1), 
	 * the test was configured to check the number of people 
	 * in the surrounding neighborhood by type.
	 * 
	 */
	@Test
	void testCensus() {
		
		Town town = new Town(4, 4);
		town.randomInit(10);
		town.grid[2][1].next(town);
		
		assertEquals(1,town.grid[2][1].nCensus[0]); //1 Reseller
		assertEquals(4,town.grid[2][1].nCensus[1]); //4 Empty
		assertEquals(1,town.grid[2][1].nCensus[2]); //1 Casual
		assertEquals(2,town.grid[2][1].nCensus[3]); //2 OUTATGE
		assertEquals(0,town.grid[2][1].nCensus[4]); //0 Streamer
		
	}		

}
