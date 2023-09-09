package hw4;

import java.util.Arrays;
import api.Card;
import api.Hand;

/*
 * @author Wonjun Choi
 */

public class FullHouseEvaluator extends AbstractEvaluator {
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public FullHouseEvaluator(int ranking, int handSize) {
		super(ranking, handSize);
		evalName = "Full House";
		minNumCards = 5;
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {

		int count = 0;
		Card[] overlapCheck = new Card[1];

		Arrays.sort(mainCards);

		OnePairEvaluator onePair = new OnePairEvaluator(super.getRanking(), super.handSize());
		ThreeOfAKindEvaluator triple = new ThreeOfAKindEvaluator(super.getRanking(), super.handSize());

		Card[] tempOnepair = new Card[2];
		Card[] tempTriple = new Card[3];

		for (int i = 0; i < mainCards.length - 2; i++) {

			tempTriple[0] = mainCards[i];
			tempTriple[1] = mainCards[i + 1];
			tempTriple[2] = mainCards[i + 2];

			if (triple.canSatisfy(tempTriple) == true) {
				count++;
				break;
			}
		}

		if (count == 1) {
			overlapCheck[0] = tempTriple[0];
		} else {
			return false;
		}

		for (int i = 0; i < mainCards.length - 1; i++) {

			if ((mainCards[i].compareTo(overlapCheck[0]) != 0)) {

				tempOnepair[0] = mainCards[i];
				tempOnepair[1] = mainCards[i + 1];

				if (onePair.canSatisfy(tempOnepair) == true) {
					count++;
					break;
				}
			}
		}

		if (mainCards.length == cardsRequired()) {
			if (count == 2) {
				return true;
			}
			return false;
		} else {
			return false;
		}

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
				FullHouseEvaluator temp = new FullHouseEvaluator(super.getRanking(), super.handSize());
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
		FullHouseEvaluator temp = new FullHouseEvaluator(super.getRanking(), super.handSize());
		Hand hand = new Hand(fiveMain, sideCards, temp);
		return hand;
	}

}
