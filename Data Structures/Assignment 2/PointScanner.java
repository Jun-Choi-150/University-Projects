package edu.iastate.cs228.hw2;

import java.io.BufferedWriter;
import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *  
 * @author Wonjun Choi
 *
 */

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		} else {
			points = new Point[pts.length];

			for (int i = 0; i < pts.length; i++) {
				points[i] = new Point(pts[i]);
			}

			sortingAlgorithm = algo;
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException 
	{
		// TODO
		sortingAlgorithm = algo;
		int count = 0;

		File file = new File(inputFileName);
		Scanner scCount = new Scanner(file);
		Scanner scRead = new Scanner(file);

		while (scCount.hasNextInt()) {
			scCount.nextInt();
			count++;
		}
		if (count % 2 == 1) {
			scCount.close();
			scRead.close();
			throw new InputMismatchException();
		}

		points = new Point[count / 2];

		for (int i = 0; i < count / 2; i++) {
			points[i] = new Point(scRead.nextInt(), scRead.nextInt());
		}

		scCount.close();
		scRead.close();
	}
		

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 */
	public void scan()
	{
		// TODO  
		AbstractSorter aSorter = null; 
		
		long scanStart, scanEnd;
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 

		try {
			if(sortingAlgorithm == Algorithm.InsertionSort) {
				aSorter = new InsertionSorter(points);
			}
			else if(sortingAlgorithm == Algorithm.SelectionSort) {
				aSorter = new SelectionSorter(points);
			}
			else if(sortingAlgorithm == Algorithm.MergeSort) {
				aSorter = new MergeSorter(points);
			}
			else if(sortingAlgorithm == Algorithm.QuickSort) {
				aSorter = new QuickSorter(points);
			}
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}

		//     a) call setComparator() with an argument 0 or 1.
		//     b) call sort(). 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime.
		
		// X
		aSorter.setComparator(0);
		scanStart = System.nanoTime();
		aSorter.sort();
		scanEnd = System.nanoTime();
		
		Point medianX = aSorter.getMedian();
		int x = medianX.getX();
		scanTime += scanEnd - scanStart;
		
		// Y 
		aSorter.setComparator(1);
		scanStart = System.nanoTime();
		aSorter.sort();
		scanEnd = System.nanoTime();
		
		Point medianY = aSorter.getMedian();
		int y = medianY.getY();
		scanTime += scanEnd - scanStart;

		//Median points
		medianCoordinatePoint = new Point(x, y);
	
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		// TODO 
		return String.format("%s\t%d\t%d\t", sortingAlgorithm.toString(), points.length, scanTime);
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		// TODO
		return "MCP:("+medianCoordinatePoint.getX()+", "+medianCoordinatePoint.getY()+")" ;  

	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		// TODO 

		try {
			
			if(sortingAlgorithm == Algorithm.SelectionSort) {
			    BufferedWriter writer = new BufferedWriter(new FileWriter("./MCP_Selection.txt"));
			    writer.write(toString());
			    writer.close();
			}
			else if(sortingAlgorithm == Algorithm.InsertionSort) {
			    BufferedWriter writer = new BufferedWriter(new FileWriter("./MCP_Insertion.txt"));
			    writer.write(toString());
			    writer.close();
			}
			else if(sortingAlgorithm == Algorithm.MergeSort) {
			    BufferedWriter writer = new BufferedWriter(new FileWriter("./MCP_Merge.txt"));
			    writer.write(toString());
			    writer.close();
			}
			else {
			    BufferedWriter writer = new BufferedWriter(new FileWriter("./MCP_Quick.txt"));
			    writer.write(toString());
			    writer.close();
			}
		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}	

	

		
}
