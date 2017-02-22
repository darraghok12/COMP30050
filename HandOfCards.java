/* Darragh O'Keeffe
 * 14702321
 * COMP 30050 Assignment 3/4
 * 22/02/2017
 */

package poker;

import java.util.Vector;

public class HandOfCards {
	//constant for number of cards in hand
	private static final int HAND_SIZE = 5;
	
	//base values for each type of hand, increments of 1 million
	// chosen so that no type of hand can outscore a higher ranked hand
	private static final int ROYAL_FLUSH_DEFAULT_VALUE = 9000000;
	private static final int STRAIGHT_FLUSH_DEFAULT_VALUE = 8000000;
	private static final int FOUR_OF_A_KIND_DEFAULT_VALUE = 7000000;
	private static final int FULL_HOUSE_DEFAULT_VALUE = 6000000;
	private static final int FLUSH_DEFAULT_VALUE = 5000000;
	private static final int STRAIGHT_DEFAULT_VALUE = 4000000;
	private static final int THREE_OF_A_KIND_DEFAULT_VALUE = 3000000;
	private static final int TWO_PAIR_DEFAULT_VALUE = 2000000;
	private static final int ONE_PAIR_DEFAULT_VALUE = 1000000;
	private static final int HIGH_HAND_DEFAULT_VALUE = 0;
	
	/* Values used to weight cards in a hand. Chosen as powers of 14 because highest gameValue
	 *  of a card is 14 and any gameValue multiplied by a weight will be higher than the sum
	 *  of all lower weights multiplied by the highest possible gameValues
	 */ 
	private static final int FIRST_CARD_WEIGHT = (int) Math.pow(14, 4);
	private static final int SECOND_CARD_WEIGHT = (int) Math.pow(14, 3);
	private static final int THIRD_CARD_WEIGHT = (int) Math.pow(14, 2);
	private static final int FOURTH_CARD_WEIGHT = (int) Math.pow(14, 1);
	private static final int FIFTH_CARD_WEIGHT = (int) Math.pow(14, 0);
	
	
	//HandOfCards instance variables
	private PlayingCard[] cards = new PlayingCard[HAND_SIZE];
	private DeckOfCards deck;
	private int gameValue = 0;

	//constructor draws HAND_SIZE number of cards from the deck 
	public HandOfCards(DeckOfCards deck){
		this.deck = deck;		
		for (int i=0;i<HAND_SIZE;i++){
			cards[i]=deck.deal();
		}
		sort();
	}
	
	// uses insertion sort algorithm to sort cards by GameValue from lowest to highest
	//  with lowest value card being in cards[0]
	private void sort(){
		int j;
		PlayingCard temp;
		for (int i=1;i<HAND_SIZE;i++){
			j = i;
		    while (j>0 && cards[j-1].getGameValue()>cards[j].getGameValue()){
		        temp = cards[j];
		        cards[j]=cards[j-1];
		        cards[j-1]=temp;
		        j = j-1;
		    }
		}
	}
	
	public boolean isRoyalFlush(){
		//check if cards are A, 10, J, Q, K by GameValues
		if (cards[0].getGameValue()==10 && cards[1].getGameValue()==11 && cards[2].getGameValue()==12
				&& cards[3].getGameValue()==13 && cards[4].getGameValue()==14){
			
			//check if all cards have same suit
			if(cards[0].getSuit()==cards[1].getSuit() && cards[0].getSuit()==cards[2].getSuit() &&
					cards[0].getSuit()==cards[3].getSuit() && cards[0].getSuit()==cards[4].getSuit()){
				
				return true;
			}
		}
		return false;
	}
	
	public boolean isStraightFlush(){
		if (!isRoyalFlush()){
			
			if(cards[0].getSuit()==cards[1].getSuit() && cards[0].getSuit()==cards[2].getSuit() &&
					cards[0].getSuit()==cards[3].getSuit() && cards[0].getSuit()==cards[4].getSuit()){
				
				// check if cards are a straight by checking gameValue
				// a straight should have gameValue of last card equal to value of first plus four,
				// 	gameValue of second last equal to value of second plus two, and gameValue of
				//	middle card not equal to values of cards beside it
				if(cards[0].getGameValue()+4==cards[4].getGameValue() && 
						cards[1].getGameValue()+2==cards[3].getGameValue() &&
						cards[2].getGameValue()!=cards[1].getGameValue() &&
						cards[2].getGameValue()!=cards[3].getGameValue()){
					
					return true;
				}
				
				// check for special case of ace-low straight
				if(cards[0].getGameValue()==2 && cards[1].getGameValue()==3 && cards[2].getGameValue()==4
					&& cards[3].getGameValue()==5 && cards[4].getGameValue()==14){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isFourOfAKind(){
		//check if first four cards are the same and last is different
		// or if last 4 cards are the same and first is different
		if ((cards[0].getGameValue()==cards[3].getGameValue() &&
				cards[0].getGameValue()!=cards[4].getGameValue()) 
				|| 
				(cards[0].getGameValue()!=cards[1].getGameValue() &&
				cards[1].getGameValue()==cards[4].getGameValue())){
			
			return true;
		}
		return false;
	}
	
	public boolean isFullHouse(){
		//two possibilities for full house, there are three of the lower value card and two of the higher
		// or two of the lower and three of the higher
		
		
		//	if first then cards at positions 0, 1 and 2 will have the same value, and cards at 3 and 4
		if ((cards[0].getGameValue()==cards[2].getGameValue() &&
				cards[3].getGameValue()==cards[4].getGameValue())
				//if second then cards at positions 0 and 1 will have the same value, and cards at 2, 3 and 4	
				|| (cards[0].getGameValue()==cards[1].getGameValue() &&
				cards[2].getGameValue()==cards[4].getGameValue())){
		
			return true;
		}
		return false;	
	}
	
	public boolean isFlush(){
		if (!isRoyalFlush() && !isStraightFlush()){
			
			//check if all cards have same suit
			if(cards[0].getSuit()==cards[1].getSuit() && cards[0].getSuit()==cards[2].getSuit() &&
					cards[0].getSuit()==cards[3].getSuit() && cards[0].getSuit()==cards[4].getSuit()){
				
				return true;
			}
		}
		return false;
	}
	
	public boolean isStraight(){
		if (!isRoyalFlush() && !isStraightFlush()){
			
			// check if cards are a straight by checking GameValue
			// a straight should have GameValue of last card equal to value of first plus four,
			// 	GameValue of second last equal to value of second plus two, and GameValue of
			//	middle card not equal to values of cards beside it
			if(cards[0].getGameValue()+4==cards[4].getGameValue() && 
					cards[1].getGameValue()+2==cards[3].getGameValue() &&
					cards[2].getGameValue()!=cards[1].getGameValue() &&
					cards[2].getGameValue()!=cards[3].getGameValue()){
				
				return true;
			}
			
			// check for Ace-Low straight, excluded from first search because it is done by GameValue
			if (cards[0].getGameValue()==2 && cards[1].getGameValue()==3 && cards[2].getGameValue()==4
					&& cards[3].getGameValue()==5 && cards[4].getGameValue()==14){
				return true;
			}
			
		}
		return false;		
	}
	
	public boolean isThreeOfAKind(){
		//three possibilities for three of a kind, first 3 cards are the same, last 3 cards
		//  are the same or three middle cards are the same.
		
		if (!isFourOfAKind() && !isFullHouse()){
			if (cards[0].getGameValue()==cards[2].getGameValue() ||
				cards[1].getGameValue()==cards[3].getGameValue() ||
				cards[2].getGameValue()==cards[4].getGameValue()){
				
				return true;
			}
		}
		return false;
	}
	
	public boolean isTwoPair(){
		// cases where both pairs are identical or odd card is not odd are caught in
		//  isFourOfAKind() and isFullHouse()
		if (!isFourOfAKind() && !isFullHouse()){

			//three possibilities, the odd card is at position 0, 2 or 4
					//pairs at 0 and 1, 2 and 3, odd card at 4
			if ((cards[0].getGameValue()==cards[1].getGameValue() && 
					cards[2].getGameValue()==cards[3].getGameValue()) 
				
					//pairs at 0 and 1, 3 and 4, odd card at 2
				|| (cards[0].getGameValue()==cards[1].getGameValue() && 
					cards[3].getGameValue()==cards[4].getGameValue()) 
					
					//pairs at 1 and 2, 3 and 4, odd card at 0
				|| (cards[1].getGameValue()==cards[2].getGameValue() && 
					cards[3].getGameValue()==cards[4].getGameValue())){
				
				return true;
			}
		}
		return false;
	}
	
	public boolean isOnePair(){
		// four possibilities for one pair, cards at positions 0 and 1, 1 and 2,
		//  2 and 3, or 3 and 4 are the same
		if (!isFourOfAKind() && !isFullHouse()  &&
				!isFlush() && !isStraight() && !isThreeOfAKind() && !isTwoPair()){
			
			if (cards[0].getGameValue()==cards[1].getGameValue() ||
					cards[1].getGameValue()==cards[2].getGameValue() ||
					cards[2].getGameValue()==cards[3].getGameValue() ||
					cards[3].getGameValue()==cards[4].getGameValue()){
				
				return true;
			}
			 
		}
		return false;
	}
	
	
	public boolean isHighHand(){
		//returns true if not a straight or a flush and no 2 cards are equal
		if (!isStraight() && !isFlush() && !isStraightFlush() && !isRoyalFlush()){
			if (cards[0].getGameValue()!=cards[1].getGameValue() &&
					cards[1].getGameValue()!=cards[2].getGameValue() &&
					cards[2].getGameValue()!=cards[3].getGameValue() &&
					cards[3].getGameValue()!=cards[4].getGameValue()){
						
				return true;
			}
		}
		return false;
	}
	
	//returns string with string representation of all 5 cards in the hand
	public String toString(){
		String output = "";
		for (int i=0;i<HAND_SIZE;i++){
			output += cards[i].toString()+" ";
		}
		return output;
	}
	
	//returns the instance of deck used to initialise the hand
	public DeckOfCards getDeck(){
		return deck;
	}
	
	/* returns an int representing the gameValue of the hand
	 * Official poker rules state that the suit of cards be irrelevant when
	 *  deciding the value of a hand so identical hands wil result in a split pot.
	 * We were also advised in class to take this approach to valuing hands
	 * When testing my code I found that I was calling this method often to sort
	 *  hands which was making it very inefficient. To improve this I added a private
	 *  variable gameValue and a public method getGameValue which returns this variable.
	 *  This setGameValue method is now private and is called by getGameValue if gameValue
	 *  equals 0 (as it does after construction).
	 * In future assignments this gameValue variable can be updated whenever a new card is
	 *  dealt to the hand 
	 */ 
	private void setGameValue(){
		int value = 0;
		
		//each card multiplied by a weight according to its gameValue with highest
		// gameValue receiving the highest weight
		if (isHighHand()){
			value = cards[4].getGameValue()*FIRST_CARD_WEIGHT 
					+ cards[3].getGameValue()*SECOND_CARD_WEIGHT
					+ cards[2].getGameValue()*THIRD_CARD_WEIGHT 
					+ cards[1].getGameValue()*FOURTH_CARD_WEIGHT
					+ cards[0].getGameValue()*FIFTH_CARD_WEIGHT
					+ HIGH_HAND_DEFAULT_VALUE;
		}
		
		//the cards in the pair will receive the highest weight with each single card
		// after receiving the next highest weight
		if (isOnePair()){
			//four possibilities for onePair organisation, the pair at
			// 0 and 1, 1 and 2, 2 and 3, or 3 and 4
			if (cards[0].getGameValue()==cards[1].getGameValue()){ //Pair at 0 and 1
				value = cards[0].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[4].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[3].getGameValue()*THIRD_CARD_WEIGHT 
						+ cards[2].getGameValue()*FOURTH_CARD_WEIGHT
						+ ONE_PAIR_DEFAULT_VALUE;
			}
			if (cards[1].getGameValue()==cards[2].getGameValue()){ //Pair at 1 and 2
				value = cards[1].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[4].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[3].getGameValue()*THIRD_CARD_WEIGHT 
						+ cards[0].getGameValue()*FOURTH_CARD_WEIGHT
						+ ONE_PAIR_DEFAULT_VALUE;
			}
			if (cards[2].getGameValue()==cards[3].getGameValue()){ //Pair at 2 and 3
				value = cards[2].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[4].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[1].getGameValue()*THIRD_CARD_WEIGHT 
						+ cards[0].getGameValue()*FOURTH_CARD_WEIGHT
						+ ONE_PAIR_DEFAULT_VALUE;
			}
			if (cards[3].getGameValue()==cards[4].getGameValue()){ //Pair at 3 and 4
				value = cards[3].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[2].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[1].getGameValue()*THIRD_CARD_WEIGHT 
						+ cards[0].getGameValue()*FOURTH_CARD_WEIGHT
						+ ONE_PAIR_DEFAULT_VALUE;
			}
		}
		
		//the cards in the pair with higher gameValue will receive the highest weight,
		// followed by the other pair and then the single card
		if (isTwoPair()){
			
			//Three possibilities for twoPair organisation, with the odd card at position
			// 0, 2 or 4 and the pairs around it
					//pairs at 0 and 1, 2 and 3, odd card at 4
			if (cards[0].getGameValue()==cards[1].getGameValue() && 
					cards[2].getGameValue()==cards[3].getGameValue()){
				
				value = cards[3].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[1].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[4].getGameValue()*THIRD_CARD_WEIGHT 
						+ TWO_PAIR_DEFAULT_VALUE;
			}
				
					//pairs at 0 and 1, 3 and 4, odd card at 2
			if (cards[0].getGameValue()==cards[1].getGameValue() && 
					cards[3].getGameValue()==cards[4].getGameValue()){

				value = cards[4].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[1].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[2].getGameValue()*THIRD_CARD_WEIGHT 
						+ TWO_PAIR_DEFAULT_VALUE;
			}
					
					//pairs at 1 and 2, 3 and 4, odd card at 0
			if (cards[1].getGameValue()==cards[2].getGameValue() && 
					cards[3].getGameValue()==cards[4].getGameValue()){

				value = cards[4].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[2].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[0].getGameValue()*THIRD_CARD_WEIGHT 
						+ TWO_PAIR_DEFAULT_VALUE;
			}
		}
		
		//Cards in the threeOfAKind will receive the highest weight followed by
		// the two remaining cards
		if (isThreeOfAKind()){
			//Three possibilities for threeOfAKind organisation, the matching cards can
			// be at positions 0 1 and 2, 1 2 and 3, or 2 3 and 4 
			if (cards[0].getGameValue()==cards[2].getGameValue()){ //Matching cards at 0 1 and 2
				value = cards[2].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[4].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[3].getGameValue()*THIRD_CARD_WEIGHT 
						+ THREE_OF_A_KIND_DEFAULT_VALUE;
			}
			if (cards[1].getGameValue()==cards[3].getGameValue()){ //Matching cards at 1 2 and 3
				value = cards[3].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[4].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[0].getGameValue()*THIRD_CARD_WEIGHT 
						+ THREE_OF_A_KIND_DEFAULT_VALUE;
			}
			if (cards[2].getGameValue()==cards[4].getGameValue()){ //Matching cards at 2 3 and 4
				value = cards[4].getGameValue()*FIRST_CARD_WEIGHT 
						+ cards[1].getGameValue()*SECOND_CARD_WEIGHT
						+ cards[0].getGameValue()*THIRD_CARD_WEIGHT 
						+ THREE_OF_A_KIND_DEFAULT_VALUE;
			}
		}
		
		//The value of a straight will be decided by the highest gameValue of the cards in the hand,
		// except in the case of the ace-low straight, which will have a value of 5+straight_default
		if (isStraight()){
			//special case of ace-low straight
			if (cards[4].getGameValue()==14 && cards[3].getGameValue()==5){
				value = cards[3].getGameValue() + STRAIGHT_DEFAULT_VALUE; 
			}
			//all other cases
			else {
				value = cards[4].getGameValue() + STRAIGHT_DEFAULT_VALUE;
			}
		}
		
		//scores for a flush are decided in the same way as highHand, with most valuable card
		// receiving the highest weight etc.
		if (isFlush()){
			value = cards[4].getGameValue()*FIRST_CARD_WEIGHT 
					+ cards[3].getGameValue()*SECOND_CARD_WEIGHT
					+ cards[2].getGameValue()*THIRD_CARD_WEIGHT 
					+ cards[1].getGameValue()*FOURTH_CARD_WEIGHT
					+ cards[0].getGameValue()*FIFTH_CARD_WEIGHT
					+ FLUSH_DEFAULT_VALUE;
		}
		
		//cards in the threeOfAkind will receive the highest weight and cards
		// in the pair will receive the secoond highest weight
		if (isFullHouse()){
			//Two possibilities for organisation of a fullHouse, three matching cards
			// at the start of the hand, or at the end of the hand
				//case where first three cards match
			if (cards[0].getGameValue()==cards[2].getGameValue()){
				value = cards[0].getGameValue()*FIRST_CARD_WEIGHT
						+ cards[4].getGameValue()*SECOND_CARD_WEIGHT
						+ FULL_HOUSE_DEFAULT_VALUE;
			}
				//case where last three cards match
			else {
				value = cards[4].getGameValue()*FIRST_CARD_WEIGHT
						+ cards[0].getGameValue()*SECOND_CARD_WEIGHT
						+ FULL_HOUSE_DEFAULT_VALUE;
			}
		}
		
		//cards in the fourOfAKind will receive the highest weight and the odd card will
		// receive the next highest weight
		if (isFourOfAKind()){
			//Two possibilities for fourOfAKind organisation, odd card at position 0 or position 4
				//case where odd card is at position 4
			if (cards[0].getGameValue()==cards[3].getGameValue()){
				value = cards[0].getGameValue()*FIRST_CARD_WEIGHT
						+ cards[4].getGameValue()*SECOND_CARD_WEIGHT
						+ FOUR_OF_A_KIND_DEFAULT_VALUE;
			}
				//case where odd card is at position 0
			else {
				value = cards[4].getGameValue()*FIRST_CARD_WEIGHT
						+ cards[0].getGameValue()*SECOND_CARD_WEIGHT
						+ FOUR_OF_A_KIND_DEFAULT_VALUE;
			}
		}
		
		//straightFlush hands are scored in the same way as a straight, with the value being
		// decided by the card with highest gameValue, except in the case of the ace-low straight
		if (isStraightFlush()){
			//check for ace_low straight
			if (cards[4].getGameValue()==14){
				value = cards[3].getGameValue() + STRAIGHT_FLUSH_DEFAULT_VALUE;
			}
			//all other possibilities
			else {
				value = cards[4].getGameValue() + STRAIGHT_FLUSH_DEFAULT_VALUE;
			}
		}
		
		//as royalFlush hands only differ by suit they all receive the same gameValue which is simply
		// the default value for the hand, to rank them as the most valuable hand
		if (isRoyalFlush()){
			value = ROYAL_FLUSH_DEFAULT_VALUE;
		}
		
		gameValue =  value;
	}
	
	public int getGameValue(){
		if (gameValue==0){
			setGameValue();
		}
		return gameValue;
	}
	
	// returns a string containing the type of the hand
	public String getType(){
		String output = "";
		if (gameValue==0){ setGameValue(); }
		if (gameValue>=HIGH_HAND_DEFAULT_VALUE){ output = "High Hand";}
		if (gameValue>=ONE_PAIR_DEFAULT_VALUE){ output = "One Pair";}
		if (gameValue>=TWO_PAIR_DEFAULT_VALUE){ output = "Two Pair";}
		if (gameValue>=THREE_OF_A_KIND_DEFAULT_VALUE){ output = "Three Of A Kind";}
		if (gameValue>=STRAIGHT_DEFAULT_VALUE){ output = "Straight";}
		if (gameValue>=FLUSH_DEFAULT_VALUE){ output = "Flush";}
		if (gameValue>=FULL_HOUSE_DEFAULT_VALUE){ output = "Full House";}
		if (gameValue>=FOUR_OF_A_KIND_DEFAULT_VALUE){ output = "Four Of A Kind";}
		if (gameValue>=STRAIGHT_FLUSH_DEFAULT_VALUE){ output = "StraightFlush";}
		if (gameValue>=ROYAL_FLUSH_DEFAULT_VALUE){ output = "Royal Flush";}
		return output;
	}
	
	/* For testing hands of cards are generated until there are a set number (testSize) of each type of hand, 
	 *  each in their own vector (so they can be counted). If more than one of the boolean check methods for
	 *  a hand returns true, or if none of them return true, an error message is printed to the console along
	 *  with the results of each of these checks.
	 * Once testSize of each hand have been generated they are sorted into the sortedResults array by their 
	 *  gameValue, and all are printed to the console, where checks for errors can be carried out 
	 * Note: It may take a while for results to appear depending on testSize, as it takes ~4,000,000 hands
	 *  to generate ten of each hand randomly.
	 */

	public static void main(String[] args){
		
		//vectors to hold, count and sort hands
		Vector<HandOfCards> results = new Vector<HandOfCards>();
		Vector<HandOfCards> sortedResults = new Vector<HandOfCards>();
		Vector<HandOfCards> highHandResults = new Vector<HandOfCards>();
		Vector<HandOfCards> onePairResults = new Vector<HandOfCards>();
		Vector<HandOfCards> twoPairResults = new Vector<HandOfCards>();
		Vector<HandOfCards> threeOfAKindResults = new Vector<HandOfCards>();
		Vector<HandOfCards> straightResults = new Vector<HandOfCards>();
		Vector<HandOfCards> flushResults = new Vector<HandOfCards>();
		Vector<HandOfCards> fullHouseResults = new Vector<HandOfCards>();
		Vector<HandOfCards> fourOfAKindResults = new Vector<HandOfCards>();
		Vector<HandOfCards> straightFlushResults = new Vector<HandOfCards>();
		Vector<HandOfCards> royalFlushResults = new Vector<HandOfCards>();
		
		boolean testingErrorsFound = false; //will become true if none of or more than 1 boolean returns true for a hand
		int handsCreated = 0; //counts iterations of while loop below (number of hands created before vectors are full)
		int testSize = 5; //number of each type of hands required to do a comparison
		
		while (royalFlushResults.size()<testSize || straightFlushResults.size()<testSize || fourOfAKindResults.size()<testSize ||
				fullHouseResults.size()<testSize || straightResults.size()<testSize || flushResults.size()<testSize ||
				threeOfAKindResults.size()<testSize || twoPairResults.size()<testSize || onePairResults.size()<testSize ||
				highHandResults.size()<testSize)
		{
			DeckOfCards deck = new DeckOfCards();
			HandOfCards hand = new HandOfCards(deck);
			int numTestsPassed = 0; //counts how many boolean check methods return true
			int handValue = hand.getGameValue();
			if (handValue>=ROYAL_FLUSH_DEFAULT_VALUE) {
				if (royalFlushResults.size()<testSize){ royalFlushResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=STRAIGHT_FLUSH_DEFAULT_VALUE && handValue<ROYAL_FLUSH_DEFAULT_VALUE) {
				if (straightFlushResults.size()<testSize){ straightFlushResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=FOUR_OF_A_KIND_DEFAULT_VALUE && handValue<STRAIGHT_FLUSH_DEFAULT_VALUE) {
				if (fourOfAKindResults.size()<testSize){ fourOfAKindResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=FULL_HOUSE_DEFAULT_VALUE && handValue<FOUR_OF_A_KIND_DEFAULT_VALUE) {
				if (fullHouseResults.size()<testSize){ fullHouseResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=FLUSH_DEFAULT_VALUE && handValue<FULL_HOUSE_DEFAULT_VALUE) {
				if (flushResults.size()<testSize){ flushResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=STRAIGHT_DEFAULT_VALUE && handValue<FLUSH_DEFAULT_VALUE) {
				if (straightResults.size()<testSize){ straightResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=THREE_OF_A_KIND_DEFAULT_VALUE && handValue<STRAIGHT_DEFAULT_VALUE) {
				if (threeOfAKindResults.size()<testSize){ threeOfAKindResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=TWO_PAIR_DEFAULT_VALUE && handValue<THREE_OF_A_KIND_DEFAULT_VALUE) {
				if (twoPairResults.size()<testSize){ twoPairResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=ONE_PAIR_DEFAULT_VALUE && handValue<TWO_PAIR_DEFAULT_VALUE) {
				if (onePairResults.size()<testSize){ onePairResults.add(hand); }
				numTestsPassed++; 
			}
			if (handValue>=HIGH_HAND_DEFAULT_VALUE && handValue<ONE_PAIR_DEFAULT_VALUE) {
				if (highHandResults.size()<testSize){ highHandResults.add(hand); }
				numTestsPassed++; 
			}
			
			
			if (numTestsPassed!=1){
				testingErrorsFound = true;
				System.out.println("Testing Hand :" + hand.toString());
				System.out.println("isRoyalFlush(): " + hand.isRoyalFlush());
				System.out.println("isStraightFlush(): " + hand.isStraightFlush());
				System.out.println("isFourOfAKind(): " + hand.isFourOfAKind());
				System.out.println("isFullHouse(): " + hand.isFullHouse());
				System.out.println("isFlush(): " + hand.isFlush());
				System.out.println("isStraight(): " + hand.isStraight());
				System.out.println("isThreeOfAKind(): " + hand.isThreeOfAKind());
				System.out.println("isTwoPair(): " + hand.isTwoPair());
				System.out.println("isOnePair(): " + hand.isOnePair());
				System.out.println("isHighHand(): " + hand.isHighHand() + "\n");
			}
			handsCreated++;
			
		}
		
		if (!testingErrorsFound){
			System.out.println("No handOfCards had errors in its classification testing\n");
		}
		
		//add all results to the results vector
		results.addAll(royalFlushResults);
		results.addAll(straightFlushResults);
		results.addAll(fourOfAKindResults);
		results.addAll(fullHouseResults);
		results.addAll(flushResults);
		results.addAll(straightResults);
		results.addAll(threeOfAKindResults);
		results.addAll(twoPairResults);
		results.addAll(onePairResults);
		results.addAll(highHandResults);
		
		//sort results by removing the hand with the highest gameValue from results and adding it to sortedResults
		while (!results.isEmpty()){
			int maxValue = 0;
			int maxIndex = 0;
			for (int k=0;k<results.size();k++){
				if(results.elementAt(k).getGameValue()>maxValue){
					maxValue = results.elementAt(k).getGameValue();
					maxIndex = k;
				}
			}
			sortedResults.add(results.remove(maxIndex));
		}
		
		//print results
		for (HandOfCards h: sortedResults){
			System.out.println("Hand: "+h.toString()+"\t Value: "+h.getGameValue()+", \t"+h.getType());
		}
		System.out.println("\nNumber of hands generated to get results: "+handsCreated);
	}
}
