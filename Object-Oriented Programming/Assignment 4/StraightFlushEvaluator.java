package hw4;

import api.Card;
import api.Hand;

/*
 * @author Wonjun Choi
 */

public class StraightFlushEvaluator extends AbstractEvaluator {

	private int maxCardRank;

	/**
	 * Constructs the evaluator. Note that the maximum rank of the cards to be used
	 * must be specified in order to correctly evaluate a straight with ace high.
	 * 
	 * @param ranking     ranking of this hand
	 * @param handSize    number of cards in a hand
	 * @param maxCardRank largest rank of any card to be used
	 */
	public StraightFlushEvaluator(int ranking, int handSize, int maxCardRank) {
		super(ranking, handSize);
		evalName = "Straight Flush";
		minNumCards = 5;
		this.maxCardRank = maxCardRank;
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {

		int count = 0;

		StraightEvaluator straight = new StraightEvaluator(super.getRanking(), super.handSize(), maxCardRank);

		if (straight.canSatisfy(mainCards) == true) {

			for (int i = 1; i < mainCards.length; i++) {

				if (mainCards[0].getSuit() == mainCards[i].getSuit()) {
					count++;
				}
			}

			if (count == 4) {
				return true;
			} else {
				return false;
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
		StraightFlushEvaluator temp = new StraightFlushEvaluator(super.getRanking(), super.handSize(),maxCardRank);
		Hand hand = new Hand(fiveMain, sideCards, temp);
		return hand;
	}

}