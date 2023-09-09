package hw4;

import api.Card;
import api.Hand;

/*
 * @author Wonjun Choi
 */

public class AllPrimesEvaluator extends AbstractEvaluator {
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public AllPrimesEvaluator(int ranking, int handSize) {
		super(ranking, handSize);
		evalName = "All Primes";
		minNumCards = 5;
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {

		int count = 0;

		if (mainCards.length == cardsRequired()) {

			for (int i = 0; i < mainCards.length; i++) {

				if (mainCards[i].getRank() < 2) {
					return false;
				}

				if (mainCards[i].getRank() == 2) {
					count++;
					continue;
				}

				for (int j = 2; j < mainCards[i].getRank(); j++) {

					if (mainCards[i].getRank() % j == 0) {
						return false;
					}
				}

				count++;

			}

			if (count == mainCards.length) {
				return true;
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

			if (canSatisfy(mainCards) == true) {
				AllPrimesEvaluator temp = new AllPrimesEvaluator(super.getRanking(), super.handSize());
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
		AllPrimesEvaluator temp = new AllPrimesEvaluator(super.getRanking(), super.handSize());
		Hand hand = new Hand(fiveMain, sideCards, temp);
		return hand;
	}

}
