package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Wonjun Choi
 *
 */
class TownTest {

	/**
	 * About townConstructorTest1 / townConstructorTest2
	 * 
	 * The first town after town creation is as follows.     
	 *  O R O R
	 *  E E C O
	 *  E S O S
	 *  E O R R
	 * 
	 */
	
	/**
	 * Test to see if the constructor works
	 * @throws FileNotFoundException
	 */
	@Test
	public void townConstructorTest1() throws FileNotFoundException {
		
		Town town = new Town("ISP4x4.txt");
		assertEquals(State.OUTAGE, town.grid[0][0].who());
		assertEquals(State.EMPTY, town.grid[1][1].who());
		assertEquals(State.OUTAGE, town.grid[2][2].who());
		assertEquals(State.RESELLER, town.grid[3][3].who());
		
	}


	/**
	 * Test to see if the constructor works
	 */
	@Test
	public void townConstructorTest2() {
		Town town = new Town(4, 4);
		town.randomInit(10);
		assertEquals(State.OUTAGE, town.grid[0][0].who());
		assertEquals(State.EMPTY, town.grid[1][1].who());
		assertEquals(State.OUTAGE, town.grid[2][2].who());
		assertEquals(State.RESELLER, town.grid[3][3].who());
	}
}
