package hw4;

import java.util.ArrayList;
import java.util.Arrays;

import api.Card;
import api.Hand;
import api.IEvaluator;
import util.SubsetFinder;

/*
 * @author Wonjun Choi
 */

public abstract class AbstractEvaluator implements IEvaluator {
	protected int ranking, handSize;
	protected int minNumCards;
	protected String evalName;

	protected Card[] mainCards, sideCards;
	protected ArrayList<Card[]> subsetCards;
	protected ArrayList<int[]> subsetIndexs;
	protected Card[] fiveMain, fourMain;

	public AbstractEvaluator(int ranking, int handSize) {

		this.ranking = ranking;
		this.handSize = handSize;

		fourMain = new Card[4];
		fiveMain = new Card[5];
	}

	/**
	 * Returns a name for this evaluator.
	 * 
	 * @return name of this evaluator
	 */
	public String getName() {
		return evalName;
	}

	/**
	 * Returns the ranking for this evaluator, where a lower number is considered
	 * "better".
	 * 
	 * @return ranking for this evaluator
	 */
	public int getRanking() {
		return ranking;
	}

	/**
	 * Returns the minimum number of cards needed for a hand produced by this
	 * evaluator (main cards only).
	 * 
	 * @return
	 */
	public int cardsRequired() {
		return minNumCards;
	}

	/**
	 * @return number of cards in a hand
	 */
	public int handSize() {
		return handSize;
	}

	/**
	 * @param mainCards array of cards
	 * @return true if the given cards satisfy this evaluator
	 */
	public boolean canSatisfy(Card[] mainCards) {

		return false;
	}

	/**
	 * @param allCards list of cards to evaluate
	 * @return true if some subset of the given cards satisfy this evaluator
	 */
	public boolean canSubsetSatisfy(Card[] allCards) {

		Arrays.sort(allCards);

		subsetIndexs = new ArrayList<int[]>();

		if (allCards.length >= cardsRequired()) {
			subsetIndexs = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		}

		Card[] tempMain5 = new Card[5];

		for (int i = 0; i < subsetIndexs.size(); i++) {

			int[] temp = subsetIndexs.get(i);

			for (int j = 0; j < temp.length; j++) {

				tempMain5[j] = allCards[temp[j]];
			}

			if (canSatisfy(tempMain5) == true) {

				fiveMain = tempMain5;
				return true;
			}

		}

		return false;
	}

	/**
	 * @param allCards list of cards from which to select the main cards for the
	 *                 hand
	 * @param subset   list of indices of cards to be selected, in ascending order
	 * @return hand whose main cards consist of the indicated subset, or null if the
	 *         indicated subset does not satisfy this evaluator
	 */
	public Hand createHand(Card[] allCards, int[] subset) {

		Arrays.sort(allCards);

		mainCards = new Card[subset.length];
		Card[] temp = new Card[allCards.length - subset.length];
		sideCards = new Card[handSize - subset.length];

		for (int i = 0; i < subset.length; i++) {

			mainCards[i] = allCards[subset[i]];
		}

		for (int i = 0, k = 0; i < allCards.length; i++) {
			int check = 0;
			for (int j = 0; j < mainCards.length; j++) {

				if ((mainCards[j].compareTo(allCards[i]) != 0)) {
					check++;
				}
			}
			if (check == mainCards.length) {
				temp[k++] = allCards[i];
			}

		}

		Arrays.sort(temp);

		for (int i = 0; i < sideCards.length; i++) {
			sideCards[i] = temp[i];
		}

		return null;
	}

	/**
	 * @param allCards list of cards from which to create the hand
	 * @return best possible hand satisfying this evaluator that can be formed from
	 *         the given cards
	 */
	public Hand getBestHand(Card[] allCards) {

		canSubsetSatisfy(allCards);
		return null;
	}

}
