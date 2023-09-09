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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	private Point[] temp;
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		// TODO  
		super(pts);
	}


	/**
	 * Perform merge sort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		// TODO
		mergeSortRec(points);
		//mainSort(points, points.length-1);
	}

	
	/**
	 * This is a recursive method that carries out merge sort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted sub-arrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		temp = new Point[pts.length];
		
		merge(pts, 0, pts.length-1);
		
		temp = null;
	
	}

	// Other private methods if needed ...
	private void merge(Point[] a, int left, int right) {

		if(left<right) {
			int center = (left+right)/2;
			int i;
			int p=0;
			int j=0;
			int k=left;
			
			merge(a, left, center);
			merge(a, center+1, right);
			
			//copy a half of array on the temp 
			for(i=left; i<=center; i++) {
				temp[p++] = a[i];
			}
			
			while(i<=right && j<p) {
				
				if(pointComparator.compare(temp[j], a[i])<=0) {
					a[k++] = temp[j++];
				}
				else if(pointComparator.compare(temp[j], a[i])>0) {
					a[k++] = a[i++];
				}
			}
			
			while(j<p) {
				a[k++] = temp[j++];
			}
		}				
	}

}
