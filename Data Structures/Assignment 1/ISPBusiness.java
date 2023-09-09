package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Wonjun Choi
 *
 * The ISPBusiness class performs simulation over a grid plain with
 * cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {

	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());

		for (int l = 0; l < tNew.getLength(); l++) {
			for (int w = 0; w < tNew.getWidth(); w++) {
				tNew.grid[l][w] = tOld.grid[l][w].next(tNew);

			}
		}

		return tNew;
	}

	/**
	 * Returns the profit for the current state in the town grid.
	 * 
	 * @param town
	 * @return number of cell of casual
	 */
	public static int getProfit(Town town) {

		int numCasual = 0;

		for (int l = 0; l < town.getLength(); l++) {
			for (int w = 0; w < town.getWidth(); w++) {
				if (town.grid[l][w].who() == State.CASUAL) {
					numCasual++;
				}
			}
		}
		return numCasual;
	}

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements
	 * of grid via an input file (option: 1) or wants to generate it randomly
	 * (option: 2).
	 * 
	 * Depending on the user choice, create the Town object using respective
	 * constructor and if user choice is to populate it randomly, then populate the
	 * grid here.
	 * 
	 * Finally: For 12 billing cycle calculate the profit and update town object
	 * (for each cycle). Print the final profit in terms of %. You should print the
	 * profit percentage with two digits after the decimal point: Example if profit
	 * is 35.5600004, your output should be:
	 *
	 * 35.56%
	 * 
	 * Note that this method does not throw any exception, so you need to handle all
	 * the exceptions in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String ans;
		int numAns;

		String[] info = new String[3];

		int rowNum, colNum, seedInt;
		double totalProfit = 0.0;
		int itr = 1, count = 1;

		while (true) {

			try {
				System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with see");
				numAns = sc.nextInt();
				if ((numAns < 1) || (numAns > 2)) {
					sc = new Scanner(System.in);
					continue;
				}
				break;

			} catch (InputMismatchException e) {

				System.out.println("Error: " + e);
				sc = new Scanner(System.in);
				continue;
			}
		}

		//Option 1
		if (numAns == 1) {
			sc = new Scanner(System.in);
			System.out.println("Please enter file path");
			String filename = sc.nextLine();

			try {
				Town town = new Town(filename);
				while (count < 13) {
					if (count == 1) {
						System.out.println(" Start:");
					} else {
						System.out.println(" After itr: " + itr);
					}

					System.out.println(town.toString());
					System.out.println("Profit: " + getProfit(town));
					totalProfit += getProfit(town);
					town = updatePlain(town);
					count++;
					itr++;
				}

				totalProfit = (totalProfit * 100) / (12 * town.getLength() * town.getWidth());
				totalProfit = Math.round(totalProfit * 100) / 100.0;

				System.out.println(String.format("%.2f%%", totalProfit));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Error: File not found");
			}

		}

		//Option 2 
		else if (numAns == 2) {
			sc = new Scanner(System.in);

			while (true) {
				System.out.println("Provide rows, cols and seed integer separated by spaces: ");
				ans = sc.nextLine();

				info = ans.split(" ");

				try {
					rowNum = Integer.parseInt(info[0]);
					colNum = Integer.parseInt(info[1]);
					seedInt = Integer.parseInt(info[2]);

				} catch (NumberFormatException e) {
					System.out.println("Error: " + e);
					continue;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: " + e);
					continue;
				}

				break;
			}

			Town town = new Town(colNum, rowNum);
			town.randomInit(seedInt);

			while (count < 13) {
				if (count == 1) {
					System.out.println(" Start:");
				} else {
					System.out.println(" After itr: " + itr);
				}

				System.out.println(town.toString());
				System.out.println("Profit: " + getProfit(town));
				totalProfit += getProfit(town);
				town = updatePlain(town);
				count++;
				itr++;
			}

			totalProfit = (totalProfit * 100) / (12 * rowNum * colNum);
			totalProfit = Math.round(totalProfit * 100) / 100.0;

			System.out.println(String.format("%.2f%%", totalProfit));

		}

	}
}
