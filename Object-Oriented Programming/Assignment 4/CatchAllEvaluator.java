package hw4;

import api.Card;
import api.Hand;

/*
 * @author Wonjun Choi
 */

public class CatchAllEvaluator extends AbstractEvaluator {
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public CatchAllEvaluator(int ranking, int handSize) {
		super(ranking, handSize);
	}

	@Override
	public Hand getBestHand(Card[] allCards) {

		OnePairEvaluator onePair = new OnePairEvaluator(super.getRanking(), super.handSize());
		ThreeOfAKindEvaluator triple = new ThreeOfAKindEvaluator(super.getRanking(), super.handSize());
		FourOfAKindEvaluator fourCards = new FourOfAKindEvaluator(super.getRanking(), super.handSize());
		FullHouseEvaluator fullHouse = new FullHouseEvaluator(super.getRanking(), super.handSize());
		StraightEvaluator strght = new StraightEvaluator(super.getRanking(), super.handSize(), 13);
		StraightFlushEvaluator strghtFlush = new StraightFlushEvaluator(super.getRanking(), super.handSize(), 13);
		AllPrimesEvaluator allPrime = new AllPrimesEvaluator(super.getRanking(), super.handSize());

		onePair.canSubsetSatisfy(allCards);
		triple.canSubsetSatisfy(allCards);
		fourCards.canSubsetSatisfy(allCards);
		fullHouse.canSubsetSatisfy(allCards);
		strght.canSubsetSatisfy(allCards);
		strghtFlush.canSubsetSatisfy(allCards);
		allPrime.canSubsetSatisfy(allCards);

		return null;
	}
}
