package hw4;

import java.util.Arrays;

import api.Card;
import api.Hand;

/*
 * @author Wonjun Choi
 */

public class StraightEvaluator extends AbstractEvaluator {
	private int maxCardRank;

	/**
	 * Constructs the evaluator. Note that the maximum rank of the cards to be used
	 * must be specified in order to correctly evaluate a straight with ace high.
	 * 
	 * @param ranking     ranking of this hand
	 * @param handSize    number of cards in a hand
	 * @param maxCardRank largest rank of any card to be used
	 */
	public StraightEvaluator(int ranking, int handSize, int maxCardRank) {

		super(ranking, handSize);
		evalName = "Straight";
		minNumCards = 5;
		this.maxCardRank = maxCardRank;
		fiveMain = new Card[5];

	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {

		int count = 0;

		Arrays.sort(mainCards);

		if (mainCards.length == cardsRequired()) {

			if (mainCards[0].getRank() == 1) {

				if ((mainCards[1].getRank() == 13) || (mainCards[1].getRank() == 5)) {
					for (int i = 1; i < mainCards.length - 1; i++) {
						if (mainCards[i].getRank() - 1 == mainCards[i + 1].getRank()) {
							count++;
						}
					}
					if (count == 3) {
						return true;
					} else {
						return false;
					}
				}

			}

			else {
				for (int i = 0; i < mainCards.length - 1; i++) {
					if (mainCards[i].getRank() - 1 == mainCards[i + 1].getRank()) {
						count++;
					}
				}
				if (count == 4) {
					return true;
				} else {
					return false;
				}
			}

		}

		return false;

	}

	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {

		return super.canSubsetSatisfy(allCards);
	}

	@Override
	public Hand createHand(Card[] allCards, int[] subset) {

		if ((allCards.length < handSize) || (minNumCards != subset.length)) {
			return null;
		} else {
			super.createHand(allCards, subset);

			if (canSubsetSatisfy(allCards) == true) {
				StraightEvaluator temp = new StraightEvaluator(super.getRanking(), super.handSize(), maxCardRank);
				Hand hand = new Hand(fiveMain, sideCards, temp);
				return hand;
			} else {
				return null;
			}
		}

	}
	
	@Override
	public Hand getBestHand(Card[] allCards) {

		canSubsetSatisfy(allCards);
		StraightEvaluator temp = new StraightEvaluator(super.getRanking(), super.handSize(),maxCardRank);
		Hand hand = new Hand(fiveMain, sideCards, temp);
		return hand;
	}

}
