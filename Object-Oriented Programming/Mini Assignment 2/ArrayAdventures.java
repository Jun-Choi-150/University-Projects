package mini2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utilities for working with arrays.
 * 
 * 
 * @author Wonjun Choi
 */

public class ArrayAdventures {
	// disable instantiation
	private ArrayAdventures() { }
	
	/**
	 * Remove an element at index pos from the array. All elements to the right of
	 * the given position are shifted down, and the last cell of the array is filled
	 * with a zero. For example, if arr = [1, 10, 3, 5, 7], after invoking
	 * remove(arr, 2), arr should be [1, 10, 5, 7, 0]. If pos is out of bounds, the
	 * method does nothing.
	 * 
	 * @param arr the array from which to remove an element
	 * @param pos the position at which the element should be removed
	 */
	public static void remove(int[] arr, int pos)
	{
		if(pos<arr.length) {
			for(int i=pos; i<arr.length-1; i++) {
				arr[i] = arr [i+1];
			}
			arr[arr.length-1] = 0;
		}
		
	//	System.out.println(Arrays.toString(arr));
	}

	/**
	 * Remove an element at index pos from the array. All elements to the left of
	 * the given position are shifted up, and the first cell of the array is filled
	 * with a zero. For example, if arr = [1, 10, 3, 5, 7], after invoking
	 * removeAndShiftUp(arr, 2), arr should be [0, 1, 10, 5, 7]. If pos is out of
	 * bounds, the method does nothing.
	 * 
	 * @param arr the array from which to remove an element
	 * @param pos the position at which the element should be removed
	 */
	public static void removeAndShiftUp(int[] arr, int pos)
	{
		if(pos<arr.length) {
			for(int i=pos-1; -1<i; i--) {
				arr[i+1] = arr [i];
			}
			arr[0] = 0;
		}
	}

	/**
	 * Removes all odd numbers from the array. You must maintain the order of all
	 * the remaining elements in the array and shift them down, and pad the array
	 * with zeros. For example, if arr = [1, -10, 3, 7, 4], after invoking
	 * removeOddElements(arr), arr should become [-10, 4, 0, 0, 0].
	 * 
	 * @param arr the array from which to remove the selected elements
	 */
	public static void removeOddElements(int[] arr) {
	
		int count = 0;
		
		ArrayList<Integer> elementsList = new ArrayList<Integer>();

		for(int i=0; i<arr.length; i++) {
			if(Math.abs(arr[i])%2==1) {
				arr[i] = 0;
				count ++;
			}
		}
		
		for(int i=0; i<arr.length; i++) {
			if(arr[i]!=0) {
				elementsList.add(arr[i]);
			}
		}
		
		for(int i=0; i<count; i++) {
			elementsList.add(0);
		}
		
		for(int i=0; i<arr.length; i++) {
			arr[i] = elementsList.get(i);
		}
		
	}

	/**
	 * Finds the runs in the given array and returns a boolean array of the same
	 * length indicating which elements are part of a run, where a run is defined
	 * as a sequence of three or more adjacent repeated values. For example, if arr
	 * = [6, 2, 5, 5, 5, 5, 4, 4, 5, 2, 2, 2, 1], then arr contains two runs, 5 5 5
	 * 5 and 2 2 2, and the method would return a boolean array [false, false, true,
	 * true, true, true, false, false, false, true, true, true, false].
	 * 
	 * @param arr the array in which to find the runs
	 * @return a boolean array indicating the positions of the run(s)
	 */
	public static boolean[] findRuns(int[] arr)
	{
		boolean [] runs = new boolean [arr.length];
		
		ArrayList<Integer> firstIndex = new ArrayList<Integer>();
		ArrayList<Integer> lastIndex = new ArrayList<Integer>();
		
		if(arr.length==3) {
			if(arr[0]==arr[1]) {
				if(arr[1]==arr[2]) {
					firstIndex.add(0);
					lastIndex.add(2);
				}
			}
			
		}
		else if(arr.length>3) {
			for(int i=0; i<arr.length-2; i++) {
				if(arr[i]==arr[i+1]) {
					if(arr[i]==arr[i+2]) {
						firstIndex.add(i);
						int temp = arr[i];
						i = i+2;
						if(i+1!=arr.length) {
							while(temp==arr[i+1]) {
								i ++;
								if(i+1==arr.length) {
									break;
								}
							}
						}
						lastIndex.add(i);
					}
				}
			}
		}
		
		if(firstIndex.size()!=0) {
			for(int i=0; i<firstIndex.size(); i++) {	
				for(int j=firstIndex.get(i); j<lastIndex.get(i)+1; j++) {
					runs [j] = true;
				}
			}				
		}
		
		return runs;
	}

	/**
	 * Removes all the runs in an array and then pads to the end of array with
	 * zeros, where a run is defined as a sequence of three or more adjacent
	 * repeated values. For example, if arr = [6, 2, 5, 5, 5, 5, 4, 4, 5, 2, 2, 2,
	 * 1], then arr contains two runs, 5 5 5 5 and 2 2 2, and after invoking
	 * removeRuns(arr) the resulting array would be [6, 2, 4, 4, 5, 1, 0, 0, 0, 0,
	 * 0, 0, 0]
	 * 
	 * @param arr the array from which to remove the runs
	 */
	public static void removeRuns(int[] arr) {
		
		ArrayList<Integer> firstIndex = new ArrayList<Integer>();
		ArrayList<Integer> lastIndex = new ArrayList<Integer>();
		ArrayList<Integer> notRuns = new ArrayList<Integer>();
		
		int count = 0;
		int sum = 0;
		
		if(arr.length==3) {
			if(arr[0]==arr[1]) {
				if(arr[1]==arr[2]) {
					firstIndex.add(0);
					lastIndex.add(2);
				}
			}
			
		}
		else if(arr.length>3) {
			for(int i=0; i<arr.length-2; i++) {
				if(arr[i]==arr[i+1]) {
					if(arr[i]==arr[i+2]) {
						firstIndex.add(i);
						int temp = arr[i];
						i = i+2;
						if(i+1!=arr.length) {
							while(temp==arr[i+1]) {
								i ++;
								if(i+1==arr.length) {
									break;
								}
							}
						}
						lastIndex.add(i);
					}
				}
			}
		}
		
		if(firstIndex.size()!=0) {
			for(int i=0; i<firstIndex.size(); i++) {	
				for(int j=firstIndex.get(i); j<lastIndex.get(i)+1; j++) {
					arr [j] = 0;
					sum += lastIndex.get(i)-firstIndex.get(i)+1;
				}
			}
		}
		
		for(int i=0; i<arr.length; i++) {
			if(arr[i]!=0) {
				notRuns.add(arr[i]);
			}
		}
		
		for(int i=0; i<sum+1; i++) {
				notRuns.add(0);
		}
		
		for(int i=0; i<arr.length; i++) {
			arr[i] = notRuns.get(i);
		}
				
	}
	
	/**
	 * Returns a string representation of the given 2D array.
	 * The format is demonstrated below. Each value is formated
	 * with a minimum width of 7 characters, for example,
	 * {@code String.format("%7d", value); }
	 * <p> 
	 * Example output:
	 * <pre>
	 * {      5,     56, 234235,   9867}
	 * </pre>
	 * 
	 * @param arr the given array
	 * @return a string representation of the given array
	 */
	public static String toString1D(int[] arr) {
		
		String [] output = new String [arr.length];
		ArrayList<String> arrNumber = new ArrayList<String>();
		
		for(int i=0; i<arr.length; i++) {
			arrNumber.add(String.format("%7d", arr[i]));
		}
		
		for(int i=0; i<arr.length; i++) {
			output[i] = arrNumber.get(i);
		}
				
		String answer = "";
		answer += "{";
			
		for(int i=0; i<arr.length; i++) {
			answer += output[i];
			answer += ",";
		}

		String answer2 = answer.substring(0,answer.length()-1)+"}";
		
		if(arr.length==0) {
			answer2 = "{}";
		}

		return answer2;
	}

	/**
	 * Extra Credit (5 Points)
	 * <p> 
	 * Returns a string representation of the given 2D array.
	 * The format is demonstrated below.
	 * <p>
	 * Example output:
	 * <pre>
	 * {{      5,     56, 234235,   9867},
	 *  {    262,  39485,    -10,     78},
	 *  {     56,      1,   9837,   8712},
	 *  {     87,    -25,      0,   9820}} 
	 * </pre>
	 * 
	 * @param arr the given array
	 * @return a string representation of the given array
	 */
	public static String toString2D(int[][] arr) {
				
		ArrayList<String> arrNumber = new ArrayList<String>();
		
		String [][] output = new String [arr.length][];
		
		int count=0;
		
		for(int i=0; i<arr.length; i++) {
			String a="";
			output[i] = new String [] {a};;

		}
			
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				arrNumber.add(String.format("%7d", arr[i][j]));
			}
		}
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				output[i][j] = arrNumber.get(count);
				count++;
			}
		}

		
		String answer = "";
					
		for(int i=0; i<arr.length; i++) {
			answer += "{";
			for(int j=0; j<arr[i].length; j++) {
				answer += output[i][j];
				answer += ",";
			}
			answer += "},";
		}

		String answer2 = answer.substring(0,answer.length()-1)+"}";
		String answer3 = "{";
		answer3 += answer2;
		
		if(arr.length==0) {
			answer3 = "{}";
		}
		
		return answer3;
	
	}

	/**
	 * Extra Credit (5 Points)
	 * <p> 
	 * Exchanges the elements of one row with those in a different row in a given 2D
	 * array. Each element remains in the same column. Assumes that all rows are the
	 * same length and row indexes are in bounds.
	 * 
	 * @param arr  the array whose rows should be exchanged
	 * @param row1 one of the rows to swap
	 * @param row2 other row to swap
	 */
	public static void swapRows(int[][] arr, int row1, int row2) {

	}
     
	/**
	 * Extra Credit (5 Points)
	 * <p> 
	 * Returns true if the given array of indexes represent values
	 * in an increasing order.
	 * <p> 
	 * The elements of the array <b>indexes</b> are indented to be
	 * used as indexes into the array <b>values</b>.
	 * <p>
	 * For example, if values={44, 70, 28, 91, 10} and indexes={0, 1, 2}
	 * then the selected values are {44, 70, 28}. These values
	 * are not in increasing order so the method returns false.
	 * <p>
	 * On the other hand, if indexes={4, 2, 3} the selected values are
	 * {10, 28, 91}. These values are in increasing order so the
	 * method return true.
	 * <p>
	 * Assumes that all indexes are in bounds of the values array.
	 * If indexes is empty the method returns true.
	 * 
	 * @param values an array of values
	 * @param indexes an array of element that are to be 
	 * @return a string representation of the given array
	 */
	public static boolean isIncreasing(int[] values, int[] indexes) {
		return false;
	}
}
