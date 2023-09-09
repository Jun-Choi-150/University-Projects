package edu.iastate.cs228.hw1;

/**
 * 
 * @author Wonjun Choi
 *
 */
public class Casual extends TownCell {

	private int r, c;

	public Casual(Town p, int r, int c) {
		super(p, r, c);
		this.r = r;
		this.c = c;
	}

	/*
	 * The who() method is a return method that tells user what the state of the current cell is.
	 */
	@Override
	public State who() {
		State Casual = State.CASUAL;

		return Casual;
	}

	/*
	 * The next() method checks the current census and follows the rule of current
	 * cell type to determine which class to return.
	 */
	@Override
	public TownCell next(Town tNew) {

		census(nCensus);

		if (nCensus[EMPTY] + nCensus[OUTAGE] < 2) {
			Reseller Reseller = new Reseller(tNew, r, c);
			return Reseller;
		} else if (nCensus[RESELLER] > 0) {
			Outage Outage = new Outage(tNew, r, c);
			return Outage;
		} else if (nCensus[STREAMER] > 0) {
			Streamer Streamer = new Streamer(tNew, r, c);
			return Streamer;
		} else if (nCensus[CASUAL] > 4) {
			Streamer Streamer = new Streamer(tNew, r, c);
			return Streamer;
		}

		Casual Casual = new Casual(tNew, r, c);
		return Casual;
	}

}
