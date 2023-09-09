package edu.iastate.cs228.hw1;

/**
 * 
 * @author Wonjun Choi 
 * 
 * The TownCell class is an abstract class. 
 * It has common features that object of cell that will fit into Town's 2D grid should have. 
 * This class is for the function of determining what state the object is for each object 
 * and what type of the next object should be by examining the surrounding neighbors for each object.
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;

	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;

	public static final int NUM_CELL_TYPE = 5;

	// Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}

	/**
	 * Checks all neigborhood cell types in the neighborhood. Refer to homework pdf
	 * for neighbor definitions (all adjacent neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 * 
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0;
		nCensus[EMPTY] = 0;
		nCensus[CASUAL] = 0;
		nCensus[OUTAGE] = 0;
		nCensus[STREAMER] = 0;

		for (int c = -1; c < 2; c++) {

			if ((col + c) < 0 || (col + c) > plain.getLength() - 1) {
				continue;
			}

			for (int r = -1; r < 2; r++) {
				if ((row + r) < 0 || (row + r) > plain.getWidth() - 1) {
					continue;
				}

				if (c == 0 && r == 0) {
					continue;
				}

				if (plain.grid[col + c][row + r].who() == State.RESELLER) {
					nCensus[RESELLER]++;
				} else if (plain.grid[col + c][row + r].who() == State.EMPTY) {
					nCensus[EMPTY]++;
				} else if (plain.grid[col + c][row + r].who() == State.CASUAL) {
					nCensus[CASUAL]++;
				} else if (plain.grid[col + c][row + r].who() == State.OUTAGE) {
					nCensus[OUTAGE]++;
				} else if (plain.grid[col + c][row + r].who() == State.STREAMER) {
					nCensus[STREAMER]++;
				}
			}
		}

	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
