package hw3;

import static api.Direction.*;

import api.Direction;
import api.Orientation;

/*
 * @author Wonjun Choi
 * 
 */

/**
 * Represents a block in the Block Slider game.
 */
public class Block {

	/*
	 * Instance variables that are the basic elements needed for a block
	 */
	private int firstRow, firstCol, length;

	/*
	 * enum to indicates whether the block shape is horizontal or vertical
	 */
	private Orientation orientaiton;

	/*
	 * Instance variable to return to the initial position when the block is initialized
	 */
	private int originRow, originCol;

	/**
	 * Constructs a new Block with a specific location relative to the board. The
	 * upper/left most corner of the block is given as firstRow and firstCol. All
	 * blocks are only one cell wide. The length of the block is specified in cells.
	 * The block can either be horizontal or vertical on the board as specified by
	 * orientation.
	 * 
	 * @param firstRow    the first row that contains the block
	 * @param firstCol    the first column that contains the block
	 * @param length      block length in cells
	 * @param orientation either HORIZONTAL or VERTICAL
	 */
	public Block(int firstRow, int firstCol, int length, Orientation orientation) {
		// TODO
		this.firstRow = firstRow;
		this.firstCol = firstCol;
		this.length = length;
		this.orientaiton = orientation;

		originRow = firstRow;
		originCol = firstCol;

	}

	/**
	 * Resets the position of the block to the original firstRow and firstCol values
	 * that were passed to the constructor during initialization of the the block.
	 */
	public void reset() {
		// TODO
		firstRow = originRow;
		firstCol = originCol;
	}

	/**
	 * Move the blocks position by one cell in the direction specified. The blocks
	 * first column and row should be updated. The method will only move VERTICAL
	 * blocks UP or DOWN and HORIZONTAL blocks RIGHT or LEFT. Invalid movements are
	 * ignored.
	 * 
	 * @param dir direction to move (UP, DOWN, RIGHT, or LEFT)
	 */
	public void move(Direction dir) {
		// TODO

		// Block move left or right
		if (orientaiton == Orientation.HORIZONTAL) {

			if (dir == LEFT) {
				firstCol -= 1;
			} else if (dir == RIGHT) {
				firstCol += 1;
			}

		}

		// Block move up or down
		if (orientaiton == Orientation.VERTICAL) {

			if (dir == UP) {
				firstRow -= 1;
			} else if (dir == DOWN) {
				firstRow += 1;
			}

		}
	}

	/**
	 * Gets the first row of the block on the board.
	 * 
	 * @return first row
	 */
	public int getFirstRow() {
		// TODO
		return firstRow;
	}

	/**
	 * Sets the first row of the block on the board.
	 * 
	 * @param firstRow first row
	 */
	public void setFirstRow(int firstRow) {
		// TODO
		this.firstRow = firstRow;
	}

	/**
	 * Gets the first column of the block on the board.
	 * 
	 * @return first column
	 */
	public int getFirstCol() {
		// TODO
		return firstCol;
	}

	/**
	 * Sets the first column of the block on the board.
	 * 
	 * @param firstCol first column
	 */
	public void setFirstCol(int firstCol) {
		// TODO
		this.firstCol = firstCol;
	}

	/**
	 * Gets the length of the block.
	 * 
	 * @return length measured in cells
	 */
	public int getLength() {
		// TODO
		return length;
	}

	/**
	 * Gets the orientation of the block.
	 * 
	 * @return either VERTICAL or HORIZONTAL
	 */
	public Orientation getOrientation() {
		// TODO
		return orientaiton;
	}

	@Override
	public String toString() {
		return "(row=" + getFirstRow() + ", col=" + getFirstCol() + ", len=" + getLength() + ", ori=" + getOrientation()
				+ ")";
	}
}
