package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes that store
 * multiple items per node. Rules for adding and removing elements ensure that
 * each node (except possibly the last one) is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int nodeSize;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public StoutList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize number of elements that may be stored in each node, must be
	 *                 an even number
	 */
	public StoutList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0) {
			throw new IllegalArgumentException();
		}

		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		this.nodeSize = nodeSize;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public StoutList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.nodeSize = nodeSize;
		this.size = size;
	}

	/*
	 * @return size
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * A boolean method for adding a node in a doubly linked list.
	 */
	@Override
	public boolean add(E item) {

		if (item == null) {
			throw new NullPointerException();
		}

		if (size == 0) {
			Node n = new Node();
			n.addItem(item);
			head.next = n;
			n.previous = head;
			n.next = tail;
			tail.previous = n;
		} else {
			if (tail.previous.count >= nodeSize) {
				Node n = new Node();
				Node temp = tail.previous;
				n.addItem(item);
				temp.next = n;
				n.previous = temp;
				n.next = tail;
				tail.previous = n;
			} else {
				tail.previous.addItem(item);
			}
		}
		size++;
		return true;
	}

	@Override
	public void add(int pos, E item) {
		if (item == null) {
			throw new NullPointerException();
		}
		if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
		}

		NodeInfo findNode = find(pos);
		nodeInfoAdd(findNode.node, findNode.offset, item);
	}

	@Override
	public E remove(int pos) {
		// TODO Auto-generated method stub
		if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
		}

		NodeInfo n = find(pos);
		E e = n.node.data[n.offset];

		if (n.node.next == tail && n.node.count == 1) {
			Node predecessor = n.node.previous;
			predecessor.next = n.node.next;
			n.node.next.previous = predecessor;
			n.node = null;
		} else if (n.node.next == tail || n.node.count > nodeSize / 2) {
			n.node.removeItem(n.offset);
		} else {
			n.node.removeItem(n.offset);
			Node succesor = n.node.next;

			if (succesor.count > nodeSize / 2) {
				n.node.addItem(succesor.data[0]);
				succesor.removeItem(0);
			}

			else if (succesor.count <= nodeSize / 2) {
				for (int i = 0; i < succesor.count; i++) {
					n.node.addItem(succesor.data[i]);
				}
				n.node.next = succesor.next;
				succesor.next.previous = n.node;
				succesor = null;
			}
		}
		size--;
		return e;
	}

	/**
	 * Sort all elements in the stout list in the NON-DECREASING order. You may do
	 * the following. Traverse the list and copy its elements into an array,
	 * deleting every visited node along the way. Then, sort the array by calling
	 * the insertionSort() method. (Note that sorting efficiency is not a concern
	 * for this project.) Finally, copy all elements from the array back to the
	 * stout list, creating new nodes for storage. After sorting, all nodes but
	 * (possibly) the last one must be full of elements.
	 * 
	 * Comparator<E> must have been implemented for calling insertionSort().
	 */
	public void sort() {
		// TODO
	}

	/**
	 * Sort all elements in the stout list in the NON-INCREASING order. Call the
	 * bubbleSort() method. After sorting, all but (possibly) the last nodes must be
	 * filled with elements.
	 * 
	 * Comparable<? super E> must be implemented for calling bubbleSort().
	 */
	public void sortReverse() {
		// TODO
	}

	@Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new StoutListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes and the position of the iterator.
	 *
	 * @param iter an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < nodeSize; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements in an
	 * array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */
		// Unchecked warning unavoidable.
		public E[] data = (E[]) new Comparable[nodeSize];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the number of
		 * elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset. Precondition: count
		 * < nodeSize
		 * 
		 * @param item element to be added
		 */
		void addItem(E item) {
			if (count >= nodeSize) {
				return;
			}
			data[count++] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " + count + " to
			// node " + Arrays.toString(data));
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements to the
		 * right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset array index at which to put the new element
		 * @param item   element to be added
		 */
		void addItem(int offset, E item) {
			if (count >= nodeSize) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			++count;
			data[offset] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " + offset + " to
			// node: " + Arrays.toString(data));
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting elements
		 * left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < nodeSize; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	/*
	 * As a class suggested by PDF, it is a class that stores node information
	 * (node, relative position of data within the node).
	 */
	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	/*
	 * As a method suggested by PDF, Helper method that provides node information
	 * corresponding to the entered location
	 */
	private NodeInfo find(int pos) {
		Node temp = head.next;
		int totalIndex = 0;

		while (temp != tail) {
			if (totalIndex + temp.count <= pos) {
				totalIndex += temp.count;
				temp = temp.next;
				continue;
			}

			NodeInfo nodeInfo = new NodeInfo(temp, pos - totalIndex);
			return nodeInfo;

		}
		return null;
	}

	/*
	 * As a method suggested by PDF, Helper methods that help node info method
	 */
	private void nodeInfoAdd(Node n, int offset, E item) {
		if (n.next == tail) {
			add(item);
		}
		if (offset == 0) {
			if (n.previous.count < nodeSize && n.previous != head) {
				n.previous.addItem(item);
				size++;
				return;
			} else if (n == tail && n.previous.count == nodeSize) {
				add(item);
				size++;
				return;
			}
		}
		if (n.count < nodeSize) {
			n.addItem(offset, item);
		} else {
			Node newOne = new Node();
			Node oldOne = n.next;
			int M = nodeSize/2;

			for (int i = 0; i < M; i++) {
				newOne.addItem(n.data[M]);
				n.removeItem(M);
			}

			n.next = newOne;
			newOne.previous = n;
			newOne.next = oldOne;
			oldOne.previous = newOne;

			if (offset <= M) {
				n.addItem(offset, item);
			}
			if (M < offset) {
				n.addItem(offset - n.count / 2, item);
			}
		}
		size++;
	}

	private class StoutListIterator implements ListIterator<E> {

		final int DIR_PREV = -1;
		final int DIR_NONE = 0;
		final int DIR_NEXT = 1;

		int cursor;

		int prevMoveDir;

		E[] fullList;

		/**
		 * Default constructor
		 */
		public StoutListIterator() {
			// TODO
			cursor = 0;
			prevMoveDir = DIR_NONE;
			setFullListIterator();
		}

		/**
		 * Constructor finds node at a given position.
		 * 
		 * @param pos
		 */
		public StoutListIterator(int pos) {
			// TODO
			cursor = pos;
			prevMoveDir = DIR_NONE;
			setFullListIterator();
		}

		/*
		 * Helper method to puts the data of all nodes into the one list
		 */
		private void setFullListIterator() {

			fullList = (E[]) new Comparable[size];

			Node set = head.next;
			int setIndex = 0;

			while (set != tail) {

				for (int i = 0; i < set.count; i++) {
					fullList[setIndex++] = set.data[i];
				}

				set = set.next;
			}

		}

		@Override
		public boolean hasPrevious() {

			if (cursor <= 0) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public boolean hasNext() {
			// TODO
			if (cursor >= size) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public E previous() {
			// TODO
			if (cursor <= 0) {
				throw new NoSuchElementException();
			}

			prevMoveDir = DIR_PREV;

			return fullList[--cursor];
		}

		@Override
		public E next() {
			// TODO
			if (cursor >= size) {
				throw new NoSuchElementException();
			}

			prevMoveDir = DIR_NEXT;

			return fullList[cursor++];
		}

		@Override
		public void remove() {
			// TODO
			if (prevMoveDir == DIR_NONE) {
				throw new IllegalStateException();
			} else if (prevMoveDir == DIR_NEXT) {
				StoutList.this.remove(cursor - 1);
				setFullListIterator();
				prevMoveDir = DIR_NONE;
				if (cursor > 0) {
					cursor--;
				}
			} else if (prevMoveDir == DIR_PREV) {
				StoutList.this.remove(cursor);
				setFullListIterator();
				prevMoveDir = DIR_NONE;
			}
		}

		/*
		 * @return Previous index value relative to cursor
		 */
		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		/*
		 * @return Next index value relative to cursor
		 */
		@Override
		public int nextIndex() {
			return cursor;
		}

		/*
		 * Set method to help add element based on cursor
		 */
		@Override
		public void set(E e) {
			if (prevMoveDir == DIR_NEXT) {
				fullList[cursor - 1] = e;
			} else if (prevMoveDir == DIR_PREV) {
				fullList[cursor] = e;
			} else {
				throw new IllegalStateException();
			}
		}

		/*
		 * Add method to add element based on cursor
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}

			StoutList.this.add(cursor, e);
			cursor++;
			setFullListIterator();
			prevMoveDir = DIR_NONE;
		}

	}

	/**
	 * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
	 * order.
	 * 
	 * @param arr  array storing elements from the list
	 * @param comp comparator used in sorting
	 */
	private void insertionSort(E[] arr, Comparator<? super E> comp) {

		E temp;
		int j;

		for (int i = 1; i < arr.length; i++) {
			temp = arr[i];
			j = i - 1;

			while (j >= 0 && comp.compare(arr[j], temp) > 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = temp;
		}

	}

	/**
	 * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
	 * description of bubble sort please refer to Section 6.1 in the project
	 * description. You must use the compareTo() method from an implementation of
	 * the Comparable interface by the class E or ? super E.
	 * 
	 * @param arr array holding elements from the list
	 */
	private void bubbleSort(E[] arr) {
		E temp;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j].compareTo(arr[j + 1]) < 0) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}

}