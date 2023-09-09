package hw3;

import static api.Direction.*;
import static api.Orientation.*;

import java.util.ArrayList;

import api.Cell;
import api.CellType;
import api.Direction;
import api.Move;
import api.Orientation;

/*
 * @author Wonjun Choi
 * 
 */

/**
 * Represents a board in the Block Slider game. A board contains a 2D grid of
 * cells and a list of blocks that slide over the cells.
 */
public class Board {
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left cornner of the board.
	 */
	private Cell[][] grid;

	/**
	 * A list of blocks that are positioned on the board.
	 */
	private ArrayList<Block> blocks;

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of blocks on the board.
	 */
	private ArrayList<Move> moveHistory;

	/*
	 * An instance variable that counts the total number of moves.
	 */
	private int totalMove;

	/*
	 * An object representing the block and cell that user currently being grabbed.
	 */
	private Block curGrabBlock;
	private Cell curGrabCell;

	/*
	 * An boolean variable to check if the game is over or not
	 */
	private boolean gameOver = false;

	/*
	 * A boolean to check if a block can be put into that cell or not
	 */
	private boolean canPlace = false;

	/*
	 * Instance variable to determine board size
	 */
	private int boardCol;
	private int boardRow;

	/**
	 * Constructs a new board from a given 2D array of cells and list of blocks. The
	 * cells of the grid should be updated to indicate which cells have blocks
	 * placed over them (i.e., setBlock() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param blocks list of blocks already containing row-column position which
	 *               should be placed on the board
	 */
	public Board(Cell[][] grid, ArrayList<Block> blocks) {
		// TODO

		// Set default values
		this.grid = grid;
		this.blocks = blocks;
		this.moveHistory = new ArrayList<Move>();
		totalMove = 0;
		boardCol = grid[0].length;
		boardRow = grid.length;

		/*
		 * Set Horizontal Blocks or Vertical Blocks on the gird
		 */
		for (int i = 0; i < blocks.size(); i++) {

			Block temp = blocks.get(i);
			int tempCol = temp.getFirstCol();
			int tempRow = temp.getFirstRow();
			int tempLength = temp.getLength();

			if (temp.getOrientation() == HORIZONTAL) {

				for (int j = 0; j < tempLength; j++) {

					grid[tempRow][tempCol + j].setBlock(temp);
				}
			}

			if (temp.getOrientation() == VERTICAL) {

				for (int j = 0; j < tempLength; j++) {

					grid[tempRow + j][tempCol].setBlock(temp);
				}
			}
		}
	}

	/**
	 * 
	 * Constructs a new board from a given 2D array of String descriptions.
	 * <p>
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBlocks(desc));
	}

	/**
	 * 
	 * Models the user grabbing a block over the given row and column. The purpose
	 * of grabbing a block is for the user to be able to drag the block to a new
	 * position, which is performed by calling moveGrabbedBlock(). This method
	 * records two things: the block that has been grabbed and the cell at which it
	 * was grabbed.
	 * 
	 * @param row row to grab the block from
	 * @param col column to grab the block from
	 */
	public void grabBlockAtCell(int row, int col) {
		// TODO

		curGrabBlock = grid[row][col].getBlock();
		curGrabCell = grid[row][col];

	}

	/**
	 * Set the currently grabbed block to null.
	 */
	public void releaseBlock() {
		// TODO
		curGrabBlock = null;
		curGrabCell = null;
	}

	/**
	 * 
	 * Returns the currently grabbed block.
	 * 
	 * @return the current block
	 * 
	 */
	public Block getGrabbedBlock() {
		// TODO
		return curGrabBlock;
	}

	/**
	 * Returns the currently grabbed cell.
	 * 
	 * @return the current cell
	 */
	public Cell getGrabbedCell() {
		// TODO
		return curGrabCell;
	}

	/**
	 * Returns true if the cell at the given row and column is available for a block
	 * to be placed over it. Blocks can only be placed over floors and exits. A
	 * block cannot be placed over a cell that is occupied by another block.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a block, otherwise false
	 */
	public boolean canPlaceBlock(int row, int col) {
		// TODO

		// A block can be placed in that cell.
		if ((grid[row][col].isFloor()) || (grid[row][col].isExit())) {
			canPlace = true;
		}

		// A block can not be placed in that cell.
		if ((grid[row][col].isWall()) || (grid[row][col].hasBlock())) {
			canPlace = false;
		}
		return canPlace;
	}

	/**
	 * Returns the number of moves made so far in the game.
	 * 
	 * @return the number of moves
	 */

	public int getMoveCount() {
		// TODO
		return totalMove;
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */
	public int getRowSize() {
		// TODO
		return boardRow;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		// TODO
		return boardCol;

	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCell(int row, int col) {
		// TODO
		return grid[row][col];

	}

	/**
	 * Returns a list of all blocks on the board.
	 * 
	 * @return a list of all blocks
	 */
	public ArrayList<Block> getBlocks() {
		// TODO
		return blocks;

	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a block
	 * over an exit, false otherwise.
	 * 
	 * @return true if the game is over
	 * 
	 */
	public boolean isGameOver() {
		// TODO

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {

				if (grid[i][j].isExit()) {

					if (grid[i][j].hasBlock()) {

						gameOver = true;
						break;
					} else {
						break;
					}
				}
			}
		}

		return gameOver;
	}

	/**
	 * 
	 * 
	 * Moves the currently grabbed block by one cell in the given direction. A
	 * horizontal block is only allowed to move right and left and a vertical block
	 * is only allowed to move up and down. A block can only move over a cell that
	 * is a floor or exit and is not already occupied by another block. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No block is currently grabbed by the user.</li>
	 * <li>A block is currently grabbed by the user, but the block is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does the following:
	 * <ul>
	 * <li>Moves the block object by calling its move method.</li>
	 * <li>Sets the block for the grid cell that the block is being moved into.</li>
	 * <li>For the grid cell that the block is being moved out of, sets the block to
	 * null.</li>
	 * <li>Moves the currently grabbed cell by one cell in the same moved direction.
	 * The purpose of this is to make the currently grabbed cell move with the block
	 * as it is being dragged by the user.</li>
	 * <li>Adds the move to the end of the moveHistory list.</li>
	 * <li>Increment the count of total moves made in the game.</li>
	 * </ul>
	 * 
	 * 
	 * @param dir the direction to move
	 */
	public void moveGrabbedBlock(Direction dir) {
		// TODO

		/*
		 * Before moving, check to see if the game is over or if there are any blocks
		 * user are currently grabbing.
		 */

		if ((isGameOver() == false) && (curGrabBlock != null)) {

			//Variables created to keep the code as simple
			int tempRow = curGrabBlock.getFirstRow();
			int tempCol = curGrabBlock.getFirstCol();
			int tempLength = curGrabBlock.getLength();
			
			//When the user grabbed the block which is a horizontal block
			if (curGrabBlock.getOrientation() == HORIZONTAL) {

				//When the direction of movement is left
				if (dir == LEFT) {
					if (canPlaceBlock(tempRow, tempCol - 1)) {
						//Move block
						curGrabBlock.move(dir);
						//Update grid
						grid[tempRow][tempCol - 1].setBlock(curGrabBlock);
						grid[tempRow][tempCol + tempLength - 1].setBlock(null);
						totalMove++;
						moveHistory.add(new Move(curGrabBlock, dir));
					}

				}
				//When the direction of movement is right
				if (dir == RIGHT) {
					if (canPlaceBlock(tempRow, tempCol + tempLength)) {
						//Move block
						curGrabBlock.move(dir);
						//Update grid
						grid[tempRow][tempCol + tempLength].setBlock(curGrabBlock);
						grid[tempRow][tempCol].setBlock(null);
						totalMove++;
						moveHistory.add(new Move(curGrabBlock, dir));
					}
				}
			}

			//When the user grabbed the block which is a Vertical block
			if (curGrabBlock.getOrientation() == VERTICAL) {

				//When the direction of movement is up
				if (dir == UP) {
					if (canPlaceBlock(tempRow - 1, tempCol)) {
						//Move block
						curGrabBlock.move(dir);
						//Update grid
						grid[tempRow - 1][tempCol].setBlock(curGrabBlock);
						grid[tempRow + tempLength - 1][tempCol].setBlock(null);
						totalMove++;
						moveHistory.add(new Move(curGrabBlock, dir));
					}

				}
				
				//When the direction of movement is down
				if (dir == DOWN) {
					if (canPlaceBlock(tempRow + tempLength, tempCol)) {
						//Move block
						curGrabBlock.move(dir);
						//Update grid
						grid[tempRow + tempLength][tempCol].setBlock(curGrabBlock);
						grid[tempRow][tempCol].setBlock(null);
						totalMove++;
						moveHistory.add(new Move(curGrabBlock, dir));
					}
				}
			}
		}
	}

	/**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each block object. It also updates each grid cells by calling
	 * their setBlock method to either set a block if one is located over the cell
	 * or set null if no block is located over the cell.
	 */
	public void reset() {
		// TODO

		// Reset total moving count
		totalMove = 0;

		// Reset moving history
		moveHistory.clear();

		// Reset about Game over and canPlace variable
		gameOver = false;
		canPlace = false;

		// Reset Block
		for (int i = 0; i < blocks.size(); i++) {

			blocks.get(i).reset();
		}

		// Reset Grid
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j].clearBlock();
			}
		}

		// Set blocks on the Grid
		for (int i = 0; i < blocks.size(); i++) {

			Block temp = blocks.get(i);
			int tempCol = temp.getFirstCol();
			int tempRow = temp.getFirstRow();
			int tempLength = temp.getLength();

			if (temp.getOrientation() == HORIZONTAL) {

				for (int j = 0; j < tempLength; j++) {

					grid[tempRow][tempCol + j].setBlock(temp);
				}
			}

			else if (temp.getOrientation() == VERTICAL) {

				for (int j = 0; j < tempLength; j++) {

					grid[tempRow + j][tempCol].setBlock(temp);
				}
			}

		}
	}

	/**
	 * Returns a list of all legal moves that can be made by any block on the
	 * current board. If the game is over there are no legal moves.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {
		// TODO

		ArrayList<Move> possibleMoves = new ArrayList<Move>();

		if (gameOver == false) {

			for (int i = 0; i < blocks.size(); i++) {

				// Horizontal Block Case
				if (blocks.get(i).getOrientation() == HORIZONTAL) {

					int tempRow = blocks.get(i).getFirstRow();
					int leftCow = blocks.get(i).getFirstCol() - 1;
					int rightCow = blocks.get(i).getFirstCol() + blocks.get(i).getLength();

					// Left Cell
					if ((canPlaceBlock(tempRow, leftCow))) {

						possibleMoves.add(new Move(blocks.get(i), LEFT));

					}

					// Right Cell
					if ((canPlaceBlock(tempRow, rightCow))) {

						possibleMoves.add(new Move(blocks.get(i), RIGHT));

					}
				}

				// Vertical Block Case
				else {

					int tempCow = blocks.get(i).getFirstCol();
					int upRow = blocks.get(i).getFirstRow() - 1;
					int downRow = blocks.get(i).getFirstRow() + blocks.get(i).getLength();

					// Left Cell
					if ((canPlaceBlock(upRow, tempCow))) {

						possibleMoves.add(new Move(blocks.get(i), UP));

					}

					// Right Cell
					if ((canPlaceBlock(downRow, tempCow))) {

						possibleMoves.add(new Move(blocks.get(i), DOWN));

					}
				}
			}
		}

		return possibleMoves;
	}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		// TODO
		return moveHistory;
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>grabs the moved block and calls moveGrabbedBlock passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBlock twice</li>
	 * <li>if required, sets is game over to false</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		// TODO

	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}
