package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Wonjun Choi
 *
 */
class ResellerTest {

	/*
	 * A test to check if the status of the Reseller object is Reseller.
	 */
	@Test
	void testState() {
		Town t = new Town(2,2);
        t.grid[0][0] = new Reseller(t,0,0);
        assertEquals(State.RESELLER, t.grid[0][0].who());
	}
		
	/*
	 * Test Case - Reseller Rule 1 
	 */
	@Test
	void testNext1() {
		Town town = new Town(2,2);
		town.grid[0][0] = new Reseller(town,0,0);
		town.grid[0][1] = new Casual(town,0,1);
		town.grid[1][0] = new Empty(town,1,0);
		town.grid[1][1] = new Outage(town,1,1);
	    assertEquals(State.EMPTY, town.grid[0][0].next(town).who());
	}
	
	/*
	 * Test Case - Reseller Rule 2 
	 */
	@Test
	void testNext2() {
		Town town = new Town(3, 3);
		town.grid[0][0] = new Casual(town,0,0);
		town.grid[0][1] = new Casual(town,0,1);
		town.grid[0][2] = new Empty(town,0,2);
		town.grid[1][0] = new Casual(town,1,0);
		town.grid[1][1] = new Reseller(town,1,1);
		town.grid[1][2] = new Empty(town,1,2);
		town.grid[2][0] = new Casual(town,2,0);
		town.grid[2][1] = new Empty(town,2,1);	
		town.grid[2][2] = new Empty(town,2,2);
	    assertEquals(State.EMPTY, town.grid[1][1].next(town).who());
	}
	
	/*
	 * Test Case - Additional Rule 6.b
	 */
	@Test
	void testNext6b() {
		Town town = new Town(3, 3);
		town.grid[0][0] = new Casual(town,0,0);
		town.grid[0][1] = new Casual(town,0,1);
		town.grid[0][2] = new Empty(town,0,2);
		town.grid[1][0] = new Casual(town,1,0);
		town.grid[1][1] = new Reseller(town,1,1);
		town.grid[1][2] = new Outage(town,1,2);
		town.grid[2][0] = new Casual(town,2,0);
		town.grid[2][1] = new Casual(town,2,1);	
		town.grid[2][2] = new Empty(town,2,2);
	    assertEquals(State.STREAMER, town.grid[1][1].next(town).who());
	}
	
	/*
	 * Test Case - Last Rule (Rule 7)
	 */
	@Test
	void testNext7() {
		Town town = new Town(3, 3);
		town.grid[0][0] = new Casual(town,0,0);
		town.grid[0][1] = new Casual(town,0,1);
		town.grid[0][2] = new Empty(town,0,2);
		town.grid[1][0] = new Outage(town,1,0);
		town.grid[1][1] = new Reseller(town,1,1);
		town.grid[1][2] = new Outage(town,1,2);
		town.grid[2][0] = new Casual(town,2,0);
		town.grid[2][1] = new Casual(town,2,1);	
		town.grid[2][2] = new Empty(town,2,2);
	    assertEquals(State.RESELLER, town.grid[1][1].next(town).who());
	}


}
