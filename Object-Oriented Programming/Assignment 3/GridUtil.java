package hw3;

import static api.Orientation.*;
import static api.CellType.*;

import java.util.ArrayList;

import api.Cell;
import api.CellType;
import api.Orientation;

/*
 * @author Wonjun Choi
 * 
 */

/**
 * Utilities for parsing string descriptions of a grid.
 */
public class GridUtil {
	/**
	 * Constructs a 2D grid of Cell objects given a 2D array of cell descriptions.
	 * String descriptions are a single character and have the following meaning.
	 * <ul>
	 * <li>"*" represents a wall.</li>
	 * <li>"e" represents an exit.</li>
	 * <li>"." represents a floor.</li>
	 * <li>"[", "]", "^", "v", or "#" represent a part of a block. A block is not a
	 * type of cell, it is something placed on a cell floor. For these descriptions
	 * a cell is created with CellType of FLOOR. This method does not create any
	 * blocks or set blocks on cells.</li>
	 * </ul>
	 * The method only creates cells and not blocks. See the other utility method
	 * findBlocks which is used to create the blocks.
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a 2D array of cells the represent the grid without any blocks present
	 */
	public static Cell[][] createGrid(String[][] desc) {
		// TODO

		Cell[][] Cell = new Cell[desc.length][desc[0].length];

		for (int i = 0; i < desc.length; i++) {
			for (int j = 0; j < desc[0].length; j++) {

				if (desc[i][j].equals("*")) {
					Cell[i][j] = new Cell(WALL, i, j);
				} else if (desc[i][j].equals("e")) {
					Cell[i][j] = new Cell(EXIT, i, j);
				} else if (desc[i][j].equals(".")) {
					Cell[i][j] = new Cell(FLOOR, i, j);
				} else {
					Cell[i][j] = new Cell(FLOOR, i, j);
				}

			}
		}
		return Cell;
	}

	/**
	 * Returns a list of blocks that are constructed from a given 2D array of cell
	 * descriptions. String descriptions are a single character and have the
	 * following meanings.
	 * <ul>
	 * <li>"[" the start (left most column) of a horizontal block</li>
	 * <li>"]" the end (right most column) of a horizontal block</li>
	 * <li>"^" the start (top most row) of a vertical block</li>
	 * <li>"v" the end (bottom most column) of a vertical block</li>
	 * <li>"#" inner segments of a block, these are always placed between the start
	 * and end of the block</li>
	 * <li>"*", ".", and "e" symbols that describe cell types, meaning there is not
	 * block currently over the cell</li>
	 * </ul>
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a list of blocks found in the given grid description
	 */
	public static ArrayList<Block> findBlocks(String[][] desc) {
		// TODO

		ArrayList<Block> infoBlock = new ArrayList<Block>();

		// Find to Horizontal Block, and Add to list after making a block object
		for (int i = 0; i < desc.length; i++) {
			for (int j = 0; j < desc[0].length; j++) {

				if (desc[i][j].equals("[")) {
					int fRow = i;
					int fCol = j;
					int leng = 1;

					j += 1;

					while (desc[i][j].equals("#")) {
						j++;
						leng++;
					}

					if (desc[i][j].equals("]")) {
						leng++;
					}

					infoBlock.add(new Block(fRow, fCol, leng, HORIZONTAL));

				}

			}
		}

		// Find to Vertical Block, and Add to list after making a block object
		for (int i = 0; i < desc.length; i++) {
			for (int j = 0; j < desc[0].length; j++) {

				int temp = 0;

				if (desc[i][j].equals("^")) {

					temp = i + 1;
					int fRow = 0;
					int fCol = 0;
					int leng = 1;

					while (desc[temp][j].equals("#")) {
						temp++;
						leng++;
					}

					if (desc[temp][j].equals("v")) {
						fRow = i;
						fCol = j;
						leng++;
					}

					infoBlock.add(new Block(fRow, fCol, leng, VERTICAL));
				}

			}
		}

		return infoBlock;
	}
}
