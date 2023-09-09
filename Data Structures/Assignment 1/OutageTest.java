package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Wonjun Choi
 *
 */
class OutageTest {

	/*
	 * A test to check if the status of the OUTAGE object is OUTAGE.
	 */
	@Test
	void testState() {
		Town t = new Town(2,2);
        t.grid[0][0] = new Outage(t,0,0);
        assertEquals(State.OUTAGE, t.grid[0][0].who());
	}
		
	/*
	 * Test Case - OUTAGE Rule 1
	 */
	@Test
	void testNext1() {
		Town town = new Town(2,2);
		town.grid[0][0] = new Outage(town,0,0);
		town.grid[0][1] = new Casual(town,0,1);
		town.grid[1][0] = new Empty(town,1,0);
		town.grid[1][1] = new Reseller(town,1,1);
	    assertEquals(State.EMPTY, town.grid[0][0].next(town).who());
	}

}
