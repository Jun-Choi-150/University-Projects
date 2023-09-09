package edu.iastate.cs228.hw1;

/**
 * 
 * @author Wonjun Choi
 *
 */
public class Streamer extends TownCell {

	private int r, c;

	public Streamer(Town p, int r, int c) {
		super(p, r, c);
		this.r = r;
		this.c = c;
	}

	/*
	 * The who() method is a return method that tells user what the state of the current cell is.
	 */
	@Override
	public State who() {

		State Streamer = State.STREAMER;

		return Streamer;
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
		} else if (nCensus[OUTAGE] > 0) {
			Empty Empty = new Empty(tNew, r, c);
			return Empty;
		}

		Streamer Streamer = new Streamer(tNew, r, c);
		return Streamer;

	}

}
