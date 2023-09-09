package hw1;

/**
 * 
 * @author WonJun Choi
 * 
 */

public class Printer {

	/**
	 * Given Maximum capacity for paper in the printer's tray Given Number of pages
	 * of the document to be printed Number of sheets to add and remove
	 */
	private int trayCapacity, documentPages, sheets;

	/**
	 * Instance variables to know the number of printable sheets, the number of next
	 * pages to be printed, and the total number of pages printed.
	 */
	private int sheetsAvailable, nextPage, totalPages;

	/**
	 * Constructs default values for printer startup
	 * 
	 * @param trayCapacity Set the maximum number of papers that can be placed in a
	 *                     tray
	 */

	public Printer(int trayCapacity) {

		this.trayCapacity = trayCapacity;
		sheets = 0;
		documentPages = 0;
		sheetsAvailable = 0;
		nextPage = 0;
		totalPages = 0;
	}

	/**
	 * 
	 * Void method
	 * 
	 * @param documentPages Number of pages in the document to be printed
	 */

	public void startPrintJob(int documentPages) {

		this.documentPages = Math.min(documentPages, trayCapacity);
		nextPage = 0;

	}

	/**
	 * When printing one page, Void method that calculates the next page, the
	 * accumulated print pages, the number of documents to be printed, and the
	 * number of printable sheets
	 */

	public void printPage() {

		nextPage += Math.min(sheetsAvailable, 1);
		totalPages += Math.min(sheetsAvailable, 1);

		documentPages -= Math.min(sheetsAvailable, 1);
		sheetsAvailable -= Math.min(sheetsAvailable, 1);

		nextPage = Math.min(nextPage, documentPages);

	}

	/**
	 * Void method that after removing the tray, number of sheets available and
	 * sheet updates
	 */

	public void removeTray() {

		sheets = sheetsAvailable;
		sheetsAvailable = 0;
	}

	/**
	 * Void method that after replacing the tray, number of sheets available and
	 * sheet updates
	 */

	public void replaceTray() {

		sheetsAvailable = sheets;
		sheets = 0;
	}

	/**
	 * 
	 * Void method that after adding the paper, number of sheets available and sheet
	 * updates
	 * 
	 * @param sheets Number of sheets to add or remove from the tray
	 */

	public void addPaper(int sheets) {

		this.sheets += sheets;
		sheetsAvailable += sheets;
		sheetsAvailable = Math.min(sheetsAvailable, trayCapacity);

	}

	/**
	 * 
	 * Void method that after removing the paper, number of sheets available and
	 * sheet updates
	 * 
	 * @param sheets Number of sheets to add or remove from the tray
	 */

	public void removePaper(int sheets) {

		this.sheets -= sheets;
		sheetsAvailable -= sheets;
		sheetsAvailable = Math.max(sheetsAvailable, 0);

	}

	/**
	 * Get method to find a number of sheets available
	 * 
	 * @return
	 */

	public int getSheetsAvailable() {
		return sheetsAvailable;
	}

	/**
	 * Get method to find the number of next page
	 * 
	 * @return
	 */

	public int getNextPage() {
		return nextPage;
	}

	/**
	 * Get method to find the total number of pages printed
	 * 
	 * @return
	 */

	public int getTotalPages() {
		return totalPages;
	}
}
