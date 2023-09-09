package edu.iastate.cs228.hw1;

/**
 * 
 * @author Wonjun Choi
 *
 */
public class Reseller extends TownCell {

	private int r, c;

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
		this.r = r;
		this.c = c;
	}

	/*
	 * The who() method is a return method that tells user what the state of the current cell is.
	 */
	@Override
	public State who() {

		State Reseller = State.RESELLER;

		return Reseller;
	}

	/*
	 * The next() method checks the current census and follows the rule of current
	 * cell type to determine which class to return.
	 */
	@Override
	public TownCell next(Town tNew) {

		census(nCensus);

		if ((nCensus[CASUAL] < 4) || (nCensus[EMPTY] > 2)) {
			Empty Empty = new Empty(tNew, r, c);
			return Empty;
		} else if (nCensus[CASUAL] > 4) {
			Streamer Streamer = new Streamer(tNew, r, c);
			return Streamer;
		}

		Reseller Reseller = new Reseller(tNew, r, c);
		return Reseller;

	}

}
