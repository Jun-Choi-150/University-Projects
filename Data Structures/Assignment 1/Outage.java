package edu.iastate.cs228.hw1;

/**
 * 
 * @author Wonjun Choi
 *
 */
public class Outage extends TownCell {

	private int r, c;

	public Outage(Town p, int r, int c) {
		super(p, r, c);
		this.r = r;
		this.c = c;
	}

	/*
	 * The who() method is a return method that tells user what the state of the current cell is.
	 */
	@Override
	public State who() {

		State Outage = State.OUTAGE;

		return Outage;
	}

	/*
	 * The next() method checks the current census and follows the rule of current
	 * cell type to determine which class to return.
	 */
	@Override
	public TownCell next(Town tNew) {

		Empty Empty = new Empty(tNew, r, c);
		return Empty;

	}

}
