package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Wonjun Choi
 *
 */

/**
 * 
 * This class implements the version of the quick sort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		// TODO 
		super(pts);
	}
		

	/**
	 * Carry out quick sort on the array points[] of the AbstractSorter class.  
	 * 
	 */
	@Override 
	public void sort()
	{
		// TODO 
		quickSortRec(0, points.length-1);
	}
	
	
	/**
	 * Operates on the sub-array of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the sub-array
	 * @param last   ending index of the sub-array
	 */
	private void quickSortRec(int first, int last)
	{
		// TODO
		if(first<last) {
			int pivot = partition(first, last);
			
			quickSortRec(first, pivot-1);
			quickSortRec(pivot+1, last);
		}
	}
	
	
	/**
	 * Operates on the sub-array of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return pivot (Index +1 in the middle of the array)
	 */
	private int partition(int first, int last) {
		// TODO

		Point pivot = points[last];
		int i = first - 1;
		int j;

		for (j = first; j < last; j++) {
			if (pointComparator.compare(points[j], pivot) < 0) {
				i++;
				swap(i, j);
			}
		}

		swap(i + 1, last);

		return i + 1;
	}
		


	
	// Other private methods if needed ...
}
