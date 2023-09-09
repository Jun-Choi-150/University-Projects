package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Wonjun Choi
 * 
 * The Town class creates Town objects of two types: files or user input. 
 * Also, it has a method that outputs the Town object so that the user can check it.
 *
 */
public class Town {

	private int length, width; // Row and col (first and second indices)
	public TownCell[][] grid;
	public int seed;

	/**
	 * Constructor to be used when user wants to generate grid randomly, with the
	 * given seed. This constructor does not populate each cell of the grid (but
	 * should assign a 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {

		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of
	 * catching it. Ensure that you close any resources (like file or scanner) which
	 * is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {

		File file = new File(inputFileName);
		Scanner sc = new Scanner(file);
		int leng = sc.nextInt();
		int wid = sc.nextInt();

		this.length = leng;
		this.width = wid;
		grid = new TownCell[leng][wid];

		String[] rows = new String[wid];

		sc.nextLine();

		for (int l = 0; l < leng; l++) {

			String s = sc.nextLine();
			s = s.replace("\t", "");
			rows = s.split(" ");

			for (int w = 0; w < wid; w++) {

				if (rows[w].equals("R")) {
					grid[l][w] = new Reseller(getTown(), w, l);
				} else if (rows[w].equals("E")) {
					grid[l][w] = new Empty(getTown(), w, l);
				} else if (rows[w].equals("C")) {
					grid[l][w] = new Casual(getTown(), w, l);
				} else if (rows[w].equals("O")) {
					grid[l][w] = new Outage(getTown(), w, l);
				} else if (rows[w].equals("S")) {
					grid[l][w] = new Streamer(getTown(), w, l);
				}
			}
		}

		sc.close();

	}

	/**
	 * Returns object of current Town
	 * 
	 * @return object of current Town
	 */
	public Town getTown() {
		return this;
	}

	/**
	 * Returns width of the grid.
	 * 
	 * @return width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns length of the grid.
	 * 
	 * @return length of the grid
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns seed number 
	 * 
	 * @return seed number 
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following
	 * class object: Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {

		this.seed = seed;

		Random rand = new Random(seed);

		rand.setSeed(seed);

		for (int leng = 0; leng < length; leng++) {
			for (int wid = 0; wid < width; wid++) {

				int num = rand.nextInt(100) % 5;

				if (num == 0) {
					grid[leng][wid] = new Reseller(getTown(), wid, leng);
				} else if (num == 1) {
					grid[leng][wid] = new Empty(getTown(), wid, leng);
				} else if (num == 2) {
					grid[leng][wid] = new Casual(getTown(), wid, leng);
				} else if (num == 3) {
					grid[leng][wid] = new Outage(getTown(), wid, leng);
				} else if (num == 4) {
					grid[leng][wid] = new Streamer(getTown(), wid, leng);
				}
			}
		}

	}

	/**
	 * Output the town grid. For each square, output the first letter of the cell
	 * type. Each letter should be separated either by a single space or a tab. And
	 * each row should be in a new line. There should not be any extra line between
	 * the rows.
	 */
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();

		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {

				if (grid[l][w].who() == State.RESELLER) {
					buff.append("R");
				} else if (grid[l][w].who() == State.EMPTY) {
					buff.append("E");
				} else if (grid[l][w].who() == State.CASUAL) {
					buff.append("C");
				} else if (grid[l][w].who() == State.OUTAGE) {
					buff.append("O");
				} else if (grid[l][w].who() == State.STREAMER) {
					buff.append("S");
				}

				if (w < width - 1) {
					buff.append("\t");
				}
			}

			if (l < length - 1) {
				buff.append("\n");
			}
		}

		return buff.toString();
	}
}
