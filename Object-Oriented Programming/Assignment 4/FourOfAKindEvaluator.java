package hw4;

import api.Card;
import api.Hand;

/**
 * Evaluator for a hand containing (at least) four cards of the same rank. The
 * number of cards required is four.
 * 
 * The name of this evaluator is "Four of a Kind".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class FourOfAKindEvaluator extends AbstractEvaluator {

	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public FourOfAKindEvaluator(int ranking, int handSize) {
		super(ranking, handSize);
		evalName = "Four of a Kind";
		minNumCards = 4;
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {
		if (mainCards.length == cardsRequired()) {
			if ((mainCards[0].compareToIgnoreSuit(mainCards[1]) == 0)
					&& (mainCards[0].compareToIgnoreSuit(mainCards[2]) == 0)
					&& (mainCards[0].compareToIgnoreSuit(mainCards[3]) == 0)) {
				return true;
			} else {
				return false;
			}
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

			if (canSatisfy(mainCards) == true) {
				OnePairEvaluator temp = new OnePairEvaluator(super.getRanking(), super.handSize());
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
		FourOfAKindEvaluator temp = new FourOfAKindEvaluator(super.getRanking(), super.handSize());
		Hand hand = new Hand(fiveMain, sideCards, temp);
		return hand;
	}
}
