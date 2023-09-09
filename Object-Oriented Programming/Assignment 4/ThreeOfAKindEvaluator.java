package hw4;

import api.Card;
import api.Hand;

/*
 * @author Wonjun Choi
 */

public class ThreeOfAKindEvaluator extends AbstractEvaluator {
	
	private int firstIndexSubset, secondIndexSubset, thirdIndexSubset;

	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public ThreeOfAKindEvaluator(int ranking, int handSize) {
		super(ranking, handSize);
		evalName = "Three of Kind";
		minNumCards = 3;

		firstIndexSubset = 0;
		secondIndexSubset = 0;
		thirdIndexSubset = 0;
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {
		if (mainCards.length == cardsRequired()) {
			if ((mainCards[0].compareToIgnoreSuit(mainCards[1]) == 0)) {
				if ((mainCards[1].compareToIgnoreSuit(mainCards[2]) == 0)) {
					return true;
				}
				return false;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {

		super.canSubsetSatisfy(allCards);

		for (int i = 0; i < subsetIndexs.size(); i++) {

			int[] temp = subsetIndexs.get(i);

			if ((allCards[temp[0]].compareToIgnoreSuit(allCards[temp[1]]) == 0)
					&& ((allCards[temp[1]].compareToIgnoreSuit(allCards[temp[2]]) == 0))) {
				firstIndexSubset = temp[0];
				secondIndexSubset = temp[1];
				thirdIndexSubset = temp[2];
				return true;
			}
		}

		return false;
	}

	@Override
	public Hand createHand(Card[] allCards, int[] subset) {

		super.createHand(allCards, subset);

		if ((allCards.length < handSize) || (minNumCards != subset.length)) {
			return null;
		} else {

			if (canSatisfy(mainCards) == true) {
				ThreeOfAKindEvaluator temp = new ThreeOfAKindEvaluator(super.getRanking(), super.handSize());
				Hand hand = new Hand(mainCards, sideCards, temp);
				return hand;
			} else {
				return null;
			}

		}
	}

	@Override
	public Hand getBestHand(Card[] allCards) {

		canSubsetSatisfy(allCards);
		ThreeOfAKindEvaluator temp = new ThreeOfAKindEvaluator(super.getRanking(), super.handSize());
		Hand hand = new Hand(fiveMain, sideCards, temp);
		return hand;
	}

}
