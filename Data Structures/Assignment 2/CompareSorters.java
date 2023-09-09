package edu.iastate.cs228.hw2;

/**
 *  
 * @author Wonjun Choi
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		// TODO 

		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       PointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort.  
		
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() 
		//        method in the PointScanner class.  
		//
		//     c) After all four scans are done for the input, print out the statistics table from
		//		  section 2.
		//
		// A sample scenario is given in Section 2 of the project description.
		
		try {
			
			Scanner sc = new Scanner(System.in);
			PointScanner[] scanners = new PointScanner[4]; 
			
			System.out.println("Performances of Four Sorting Algorithms in Point Scanning\n");
			int key;
			int count = 1;
			
			
			do {
				System.out.println("Keys:\t1 (random integers)\t2 (file input)\t3 (exit)");
				System.out.print("Enter the key: ");
				key = sc.nextInt();
				
				if(key==1) {
					System.out.println("Trial "+count+": "+key);
					System.out.print("Please enter the number of points: ");
					int num = sc.nextInt();
					
					Random rand = new Random ();
					Point[] pts = generateRandomPoints(num, rand);

					scanners[0] = new PointScanner(pts, Algorithm.SelectionSort);
					scanners[1] = new PointScanner(pts, Algorithm.InsertionSort);
					scanners[2] = new PointScanner(pts, Algorithm.MergeSort);
					scanners[3] = new PointScanner(pts, Algorithm.QuickSort);
					
					for(int i=0; i<4; i++) {
						scanners[i].scan();
						scanners[i].writeMCPToFile();
					}
					
					System.out.println("algorithm\tsize\ttime (ns)");
					System.out.println("------------------------------------");
					for(int i=0; i<4; i++) {
						System.out.println(scanners[i].stats());
					}
					System.out.println("------------------------------------");
						
				}
				else if(key==2) {
					System.out.println("Trial "+count+": "+key);
					System.out.println("Points from a file");
					System.out.print("File name: ");
					String inputFileName = sc.next();

					scanners[0] = new PointScanner(inputFileName, Algorithm.SelectionSort);
					scanners[1] = new PointScanner(inputFileName, Algorithm.InsertionSort);
					scanners[2] = new PointScanner(inputFileName, Algorithm.MergeSort);
					scanners[3] = new PointScanner(inputFileName, Algorithm.QuickSort);
					
					for(int i=0; i<4; i++) {
						scanners[i].scan();
						scanners[i].writeMCPToFile();
					}
					
					System.out.println("algorithm\tsize\ttime\t(ns)");
					System.out.println("------------------------------------");
					for(int i=0; i<4; i++) {
						System.out.println(scanners[i].stats());
					}
					System.out.println("------------------------------------");

				}
				
				count++;
			}while(key !=3);
			
			sc.close();
	
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(InputMismatchException e) {
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		// TODO

		Point[] pts = new Point[numPts];

		for (int i = 0; i < numPts; i++) {

			pts[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);

		}

		return pts;

	}
	
}
