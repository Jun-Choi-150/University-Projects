package edu.iastate.cs228.hw3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Node;

public class Tester {


	@Test
	public void pdfExample() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");	 n.add("2"); 
		n.add("C");	 n.add("D");  n.add("E");    n.add("F");
		n.remove(2); n.remove(2);
		//How do I go to the next node?
		n.remove(5);
		//After calling remove(5) you are at the start for the pdf example
		
		n.add("V");
		assertEquals("[(A, B, -, -), (C, D, E, V)]",n.toStringInternal());
		n.add("W");
		assertEquals("[(A, B, -, -), (C, D, E, V), (W, -, -, -)]",n.toStringInternal());
		n.add(2,"X");
		assertEquals("[(A, B, X, -), (C, D, E, V), (W, -, -, -)]",n.toStringInternal());
		n.add(2,"Y");
		assertEquals("[(A, B, Y, X), (C, D, E, V), (W, -, -, -)]",n.toStringInternal());
		n.add(2,"Z");
		assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V), (W, -, -, -)]",n.toStringInternal());
		System.out.println(n.toStringInternal());


		//Examples removing elements from a list

		//Removes W
		n.remove(9);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V)]",n.toStringInternal());

		//Removes Y
		n.remove(3);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (X, C, -, -), (D, E, V, -)]",n.toStringInternal());

		//Removes X (mini merge)
		n.remove(3);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (C, D, -, -), (E, V, -, -)]",n.toStringInternal());
		System.out.println(n.toStringInternal());

		//Removes E (No merge with predecessor Node)
		n.remove(5);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (C, D, -, -), (V, -, -, -)]",n.toStringInternal());
		System.out.println(n.toStringInternal());

		//Removes C (Full merge with successor Node)
		n.remove(3);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (D, V, -, -)]",n.toStringInternal());
		System.out.println(n.toStringInternal());

	}
}