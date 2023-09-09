package edu.iastate.cs228.hw1;

/**
 * 
 * @author Wonjun Choi
 *
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ISPBusinessTest {

	
	/**
	 * 
	 * This is First Town   
	 * O R O R
	 * E E C O
	 * E S O S
	 * E O R R
	 * 
	 * Town after update
	 * E E E E
	 * C C O E
	 * C O E O
	 * C E E E
	 * 
	 */
	@Test
	public void testUpdatePlain() {

		Town town = new Town(4, 4);
		town.randomInit(10);
		town = ISPBusiness.updatePlain(town);
		assertEquals(State.EMPTY, town.grid[0][0].who());

	}
	
	/**
	 * Test to check profit 
	 * by forming a town of a simple two-dimensional array
	 */
	@Test
	public void testGetProfit() {

		Town town = new Town(2, 2);		
		
		town.grid[0][0] = new Empty(town,0,0);
		town.grid[0][1] = new Reseller(town,0,1);
		town.grid[1][0] = new Casual(town,1,0);
		town.grid[1][1] = new Outage(town,1,1);
		
		assertEquals(1, ISPBusiness.getProfit(town));

	}
	
}
