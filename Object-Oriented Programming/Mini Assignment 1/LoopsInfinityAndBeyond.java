package mini1;

import java.util.Scanner;

/**
 * Utility class with a bunch of static methods for loop practice.
 * 
 * @author Wonjun Choi
 */
public class LoopsInfinityAndBeyond {

	// disable instantiation
	private LoopsInfinityAndBeyond() { }
	
	/**
	 * Returns a new string in which every character in the given string that
	 * is not already repeated consecutively is doubled.
	 * <p> 
	 * For example:
	 * <pre>{@code
	 * "attribute1" -> "aattrriibbuuttee11"
	 * "AAA Bonds" -> "AAA  BBoonnddss"
	 * }</pre>
	 * 
	 * @param text given starting string
	 * @return string with characters doubled
	 */
	public static String doubleChars(String text)
	{
		String finalText = "";	
		int count = 0;
		
		if(text.length()==1) {
			finalText += text;
			finalText += text;
		}
		
		else {
			
			for(int i=1; i<text.length(); i++) {
				
				if(text.charAt(i-1)==text.charAt(i)) {	
					count +=1;
					finalText += text.charAt(i-1);
					
				}
				else if(count>0) {
					finalText += text.charAt(i-1);
					count = 0;
				}
				
				else if(text.charAt(i-1)!=text.charAt(i)) {
					finalText += text.charAt(i-1);
					finalText += text.charAt(i-1);
					
					if (i==text.length()-1) {
						if(text.charAt(i-1)!=text.charAt(i)) {
							finalText += text.charAt(i);
							finalText += text.charAt(i);
						}
					}
				}			
			}
		}
		
		return finalText;
	}

	/**
	 * Returns a year with the highest value, given a string containing pairs
	 * of years and values (doubles). If there are no pairs, the method returns
	 * -1. In the case of a tie, the first year with the highest value is
	 * returned. Assumes the given string follows the correct format.
	 * <p>
	 * For example, given the following string, the year 1995 is returned.
	 * <pre>
	 * 1990 75.6 1991 110.6 1995 143.6 1997 62.3
	 * </pre>
	 * 
	 * @param data given string containing year-value pairs
	 * @return first year associated with the highest value, or -1 if no pair
	 *         given
	 */
	public static int maxYear(String data)
	{
		
		double highestValue = 0;
		int year=0;
		int year2=0;
		
		String [] array = data.split(" ");
		
		if(array.length%2==0) {
			
			for(int i=0; i<array.length; i++) {
				
				if(i%2==1) {
					double a = Double.parseDouble(array[i]);
					int check = 0;
					if(highestValue<a) {
						highestValue = a;
						year = Integer.parseInt(array[i-1]);
						check = i;
					}
					else if(highestValue==a) {
						year2 = Integer.parseInt(array[i-1]);
						if(year2<year) {
							year = year2;
						}					
					}
				}
			}
		}
		
		else {
			return -1;
		}
		
		return year;	
	}
	
	/**
	 * Returns the number of iterations required until <code>n</code> is equal to 1,
	 * where each iteration updates <code>n</code> in the following way:
	 * 
	 * <pre>
	 *     if n is even
	 *         divide it in half
	 *     else
	 *         multiply n by three and add 1
	 * </pre>
	 * 
	 * For example, given <code>n == 6</code>, the successive values of
	 * <code>n</code> would be 3, 10, 5, 16, 8, 4, 2, 1, so the method returns 8. If
	 * <code>n</code> is less than 1, the method returns -1.
	 * <p>
	 * <em>(Remark:</em> How do we know this won't be an infinite loop for some
	 * values of <code>n</code>? In general, we don't: in fact this is a well-known
	 * open problem in mathematics. However, it has been checked for numbers up to
	 * 10 billion, which covers the range of possible values of the Java
	 * <code>int</code> type.)
	 * 
	 * @param n given starting number
	 * @return number of iterations required to reach <code>n == 1</code>, or -1 if
	 *         <code>n</code> is not positive
	 */
	public static int collatzCounter(int n)
	{
		int count = 0;
		
		if(n<1) {
			return -1;
		}
	
		while (n != 1) {

			if (n % 2 == 0) {
				n = n / 2;
				count += 1;
			}

			else if (n % 2 != 0) {
				n = (3 * n) + 1;
				count += 1;
			}

		}
		return count;
	}
	
	/**
	 * Returns a new string in which every word in the given string is doubled. A
	 * word is defined as a contiguous group of non-space (i.e., ' ') characters
	 * that starts with an alphabetic letter and are surrounded by spaces and/or
	 * the start or end of the given string. Assumes the given string does not
	 * contain more than one consecutive white-space.
	 * <p> 
	 * For example:
	 * <pre>{@code
	 * "the time time" -> "the the time time time time"
	 * "The answer is 42." -> "The The answer answer is is 42."
	 * "A. runtime = 10ms" -> "A. A. runtime runtime = 10ms"
	 * }</pre>
	 * 
	 * @param text given starting string
	 * @return new string in which words are doubled
	 */
	public static String doubleWords(String text) {
		String[] array = text.split(" ");
		String answer = "";

		if (text != "") {
			for (int i = 0; i < array.length; i++) {
				String a = array[i];
				int num = (int) a.charAt(0);

				if ((num > 64 && num < 91) || (num > 96 && num < 123)) {

					answer += array[i];
					answer += " ";
					answer += array[i];
					answer += " ";
				}

				else {
					answer += array[i];
					answer += " ";
				}

			}

			answer = answer.trim();

		}

		return answer;
	}
		

	/**
	 * Returns true if string t can be obtained from string s by removing exactly
	 * one vowel character. The vowels are the letters 'a', 'e', 'i', 'o'
	 * and 'u'. Vowels can be matched in either upper or lower case, for example,
	 * 'A' is treated the same as 'a'. If s contains no vowels the method returns
	 * false.
	 * <p>
	 * For example:
	 * <pre>{@code
	 * "banana" and "banna" returns true
	 * "Apple" and "ppl" returns false
	 * "Apple" and "pple" returns true
	 * }</pre>
	 * 
	 * @param s longer string
	 * @param t shorter string
	 * @return true if removing one vowel character from s produces the string t
	 */
	public static boolean oneVowelRemoved(String s, String t) {

			for (int i = 0; i < s.length(); i++) {

				StringBuffer str = new StringBuffer(s);
				
				if ((s.charAt(i) == 'a') || (s.charAt(i) == 'e') || (s.charAt(i) == 'i') || (s.charAt(i) == 'o')
						|| (s.charAt(i) == 'u') || (s.charAt(i) == 'A') || (s.charAt(i) == 'E') || (s.charAt(i) == 'I')
						|| (s.charAt(i) == 'O') || (s.charAt(i) == 'U')) {

					StringBuffer a = str.deleteCharAt(i);
					String b = a.toString();

					if (t.equals(b)) {
						return true;
					}
		
				}

			}

		return false;
	}
	
	/**
	 * Returns a new string in which a UFO pattern in the given string is
	 * shifted one character to the right. The UFO pattern starts with a
	 * {@code '<'}, has one or more {@code '='} and ends with a {@code '>'}. The pattern may wrap
	 * around from the end to the beginning of the string, for example
	 * {@code ">  <="}. Any other characters from the given string remain in place.
	 * If the UFO moves over top of another character, that character is
	 * removed. If there are multiple UFO patterns, only the one that starts
	 * farthest to the left is moved.
	 * <p> 
	 * For example:
	 * <pre>{@code
	 * " <=>  *   . <=> " -> 
	 * "  <=> *   . <=> "
	 * 
	 * "   <=>*   .     " ->
	 * "    <=>   .     "
	 * 
	 * ">.   x     .  <=" ->
	 * "=>   x     .   <"
	 * 
	 * " <= <===>   .   " ->
	 * " <=  <===>  .   "
	 * }</pre>
	 * 
	 * @param space given string
	 * @return a new string with a UFO pattern moved one to the right
	 */
	

	public static String ufo(String space)
	{
		char answer [] = new char [space.length()];

		int spaceLast = space.length()-1;
		int startIndex = 0;
		int lastIndex = 0;
		int count1 = 0;
		int count2 = 0;
		
		boolean ufoCheck = false;
		boolean ufoCheck2 = false;
		
		String ans = "";
		
		//Reverse Case
		if ((space.charAt(0) == '>') || (space.charAt(0) == '=')) {
			
			// > Case
			if (space.charAt(0) == '>') {
				if (space.charAt(spaceLast) == '=') {
					for (int i = spaceLast - 1; 0 < i; i--) {
						
						if (space.charAt(i) == '=') {

						} 
						
						else if (space.charAt(i) == '<') {
							ufoCheck = true;
							startIndex = i;
							lastIndex = 0;
							break;
						}
						
						else {
							ufoCheck = false;
						}
					}
				}
				
			}
			
			// = Case
			else if ((space.charAt(0) == '=')) {

				for (int i = 1; i < space.length() - 1; i++) {
					if (space.charAt(i) == '=') {
						
					} 
					
					else if (space.charAt(i) == '>') {
						count1 = 1;
						lastIndex = i;
						break;
					} 
					
					else {
						ufoCheck = false;
					}
				}
				
				if (count1 == 1) {
					for (int i = spaceLast - 1; 0 < i; i--) {
						if (space.charAt(i) == '=') {

						} 
						else if (space.charAt(i) == '<') {
							ufoCheck = true;
							startIndex = i;
							break;
						}
						else {
							ufoCheck = false;
						}
					}
				}

			}

		}
		
		if (ufoCheck == false) {

			for (int i = 0; i < space.length() - 1; i++) {

				if (space.charAt(i) == '<') {
					if (space.charAt(i + 1) == '=') {
						count2 = 2;
						while (space.charAt(i + count2) == '=') {
							count2++;
						}

						if (space.charAt(i + count2) == '>') {
							ufoCheck2 = true;
							startIndex = i;
							lastIndex = i + count2;
							break;
						}

					}

				}

			}
			
			//Not UFO in String
			for(int i=0; i<space.length(); i++) {
				answer[i] = space.charAt(i);
			}
		}
		
		//Reverse Case 
		if(ufoCheck==true) {
			
			for(int i=0; i<space.length(); i++) {
				answer[i] = space.charAt(i);
			}
			
			char temp;
			temp = space.charAt(space.length()-1);
			
			for(int i=space.length()-2; startIndex-1<i; i--) {
				answer[i+1] = space.charAt(i);
			}
			
			answer[startIndex] = ' ';
			
			for(int i=lastIndex; -1<i; i--) {				
				answer[i+1] = answer[i];
			}
			
			answer[0] = temp;
			
		}
		
		//Normal Case 
		if(ufoCheck2 == true) {
			
			for(int i=0; i<space.length()-1; i++) {
				answer[i] = space.charAt(i);
			}
			
			for(int i=lastIndex; startIndex-1<i; i--) {				
				answer[i+1] = answer[i];
			}
			
			answer[startIndex] = ' ';
			
		}
		
		ans = String.valueOf(answer);
		
		return ans;
	}
		
	
	
	/**
	 * Prints a pattern of <code>2*n</code> rows containing slashes, dashes and backslashes
	 * as illustrated below.
	 * <p>
	 * When {@code n <= 0 }, prints nothing.
	 * <p> 
	 * <code>n = 1</code>
	 * <pre>
	 * \/
	 * /\
	 * </pre>
	 * <p> 
	 * <code>n = 2</code>
	 * <pre>
	 * \--/
	 * -\/
	 * -/\
	 * /--\
	 * </pre>
	 * <p> 
	 * <code>n = 3</code>
	 * <pre>
	 * \----/
	 * -\--/
	 * --\/
	 * --/\
	 * -/--\
	 * /----\
	 * </pre>
	 * 
	 * @param n number of rows in the output
	 * @return 
	 */
	public static void printX(int n) {
		if (n > 0) {

			int num = (2 * n) + 1;

			int count = 0;

			String[][] board = new String[num][num];

			for (int i = 0; i < num - 1; i++) {
				for (int j = 0; j < num - 1; j++) {
					board[i][j] = "-";
				}

			}

			for (int k = 0; k < num - 1; k++) {
				board[k][k] = "\\";
			}

			for (int l = 0; l < num - 1; l++) {
				board[l][num - l - 2] = "/";
			}

			for (int i = 0; i < num - 1; i++) {
				for (int j = 0; j < num - 1; j++) {

					if (count == 2) {
						if ((board[i][j] != "\\") || (board[i][j] != "/")) {

							board[i][j] = "";

						}

					}

					if ((board[i][j] == "\\") || (board[i][j] == "/")) {
						count++;
					}

				}
				count = 0;
			}

			for (int i = 0; i < num - 1; i++) {
				for (int j = 0; j < num - 1; j++) {
					System.out.print(board[i][j]);
				}
				System.out.println();
			}
		}
	}
}
