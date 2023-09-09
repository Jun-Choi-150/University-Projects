package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *  
 * @author Wonjun Choi
 *
 */

public class Main {

	public static void main(String[] args) throws IOException {

		System.out.println("Please enter the filename to decode: ");
		Scanner sc = new Scanner(System.in);
		String file = sc.nextLine();
		sc.close();

		String content = new String(Files.readAllBytes(Paths.get(file))).trim();
		int pos = content.lastIndexOf('\n');
		String pattern = content.substring(0, pos); 
		String binCode = content.substring(pos).trim(); 
		
		MsgTree root = new MsgTree(pattern);
		MsgTree.printCodes(root, binCode);
		root.decode(root, binCode);
	}
}
