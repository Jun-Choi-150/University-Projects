package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 *  
 * @author Wonjun Choi
 *
 */

public class MsgTree {
	
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;

	// Reading direction about tree 
	private static final int DOWN = -1;
	private static final int UP = 1;
	private static int DIR;

	// Encoded character storage field
	private static String pattern;
	
	/**
	 * 
	 * Constructor that takes an encoded string and constructs the tree
	 * 
	 * @param encodingString
	 */
	public MsgTree(String encodingString) {

		// check available encoding
		if (encodingString == null || encodingString.length() < 2) {
			return;
		}

		// Set field to hold pattern string 
		pattern = encodingString;

		// Set - Start Point
		Stack<MsgTree> stack = new Stack<>();
		this.payloadChar = encodingString.charAt(0);
		DIR = DOWN;
		MsgTree curRootNode = this;
		stack.push(this);

		//Make a tree 
		for (int index = 1; index < encodingString.length(); index++) {

			// Next rootNode
			MsgTree checkingNode = new MsgTree(encodingString.charAt(index));

			// LEFT
			if (DIR == DOWN) {

				curRootNode.left = checkingNode;

				if (checkingNode.payloadChar == '^') {
					DIR = DOWN;
					curRootNode = stack.push(checkingNode);
				} else {
					DIR = UP;
					if (!stack.empty()) {
						curRootNode = stack.pop();
					}
				}
			}
			
			// RIGHT
			else {

				curRootNode.right = checkingNode;

				if (checkingNode.payloadChar == '^') {
					DIR = DOWN;
					curRootNode = stack.push(checkingNode);
				} else {
					DIR = UP;
					if (!stack.empty()) {
						curRootNode = stack.pop();
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * Constructor for a single node with null children
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;

	}
	
	/**
	 * 
	 * void method to to print characters and their binary codes
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {

		System.out.println("--------------------------------");
		System.out.println("     character \t\tcode ");
		System.out.println("--------------------------------");

		for (char c : pattern.toCharArray()) {
			if (c != '^') {
				System.out.println("\t" + (c == '\n' ? "\\n" : c + " ") + "\t\t" + trackBinaryCode(root, code, c));
			}
		}

	}

	/**
	 * 
	 * A method that decodes the binary code of encoding and outputs a message
	 * 
	 * @param codes
	 * @param msg
	 */
	
	public void decode(MsgTree codes, String msg) {

		if (codes == null) {
			throw new IllegalArgumentException();
		}

		MsgTree curRoot = codes;
		String codeNum = "";
		StringBuilder decodingString = new StringBuilder();

		for (int i = 0; i < msg.length(); i++) {
			if (msg.charAt(i) == '0') {

				curRoot = curRoot.left;
				codeNum = codeNum + "0";
			} else {
				curRoot = curRoot.right;
				codeNum = codeNum + "1";
			}

			if (curRoot.payloadChar != '^') {

				decodingString.append(curRoot.payloadChar);
;
				// Reset
				curRoot = codes;
				codeNum = "";
			}
		}

		System.out.println("\n");
		System.out.println("MESSAGE:  ");
		System.out.println(decodingString.toString());
		System.out.println("\n");
		statistc(msg, decodingString.toString());
	}

	
	/**
	 * 
	 * Helper methods for use in printCodes method
	 * Helper method to get the encoding binary code for a specific character
	 * 
	 * @param root
	 * @param binaryCode
	 * @param ch
	 * @return decoded binary code
	 */
	private static String trackBinaryCode(MsgTree root, String binaryCode, char ch) {

		if (root == null) {
			throw new IllegalArgumentException();
		}

		MsgTree curRoot = root;
		String codeNum = "";

		for (int i = 0; i < binaryCode.length(); i++) {
			if (binaryCode.charAt(i) == '0') {

				curRoot = curRoot.left;
				codeNum = codeNum + "0";
			} else {
				curRoot = curRoot.right;
				codeNum = codeNum + "1";
			}

			if (curRoot.payloadChar != '^') {

				if (curRoot.payloadChar == ch) {
					break;
				}
				curRoot = root;
				codeNum = "";
			}
		}

		return codeNum;
	}

	
	/**
	 * Extra Credit method 
	 * 
	 * Statistics output methods based on encoded and decoded string data
	 * 
	 * @param encodeStr
	 * @param decodeStr
	 */
	private void statistc(String encodeStr, String decodeStr) {
		System.out.println("STATISTICS:");
		System.out.println(String.format("Avg bits/char:\t\t%.1f", encodeStr.length() / (double) decodeStr.length()));
		System.out.println("Total Characters:\t" + decodeStr.length());
		System.out.println(String.format("Space Saving:\t\t%.1f%%", (1d - decodeStr.length() / (double) encodeStr.length()) * 100));
	}

}
