/* Darragh O'Keeffe
 * 14702321
 * COMP 30050 Assignment 3
 * 15/02/2017
 */

package poker;

public class HandOfCards {
	//constant for number of cards in hand
	private static final int HAND_SIZE = 5;
	
	//HandOfCards instance variables
	private PlayingCard[] cards = new PlayingCard[HAND_SIZE];
	private DeckOfCards deck;

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
				if(cards[0].getGameValue()==cards[4].getGameValue()+4 && 
						cards[1].getGameValue()==cards[3].getGameValue()+2 &&
						cards[2].getGameValue()!=cards[1].getGameValue() &&
						cards[2].getGameValue()!=cards[3].getGameValue()){
					
					return true;
				}
				
				// check for special case of ace-low straight
				if(cards[0].getGameValue()==14 && cards[1].getGameValue()==2 && cards[2].getGameValue()==3
					&& cards[3].getGameValue()==4 && cards[4].getGameValue()==5){
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
		
			//if first then cards at positions 0, 1 and 2 will have the same value, and cards at 3 and 4
		if ((cards[0].getGameValue()==cards[2].getGameValue() &&
				cards[3].getGameValue()==cards[4].getGameValue())
			//if second then cards at positions 0 and 1 will have the same value, and cards at 2, 3 and 4	
			|| (cards[0].getGameValue()==cards[1].getGameValue() &&
				cards[2].getGameValue()==cards[4].getGameValue())){
			
			return true;
		}
		return false;	
	}
	
	public boolean isStraight(){
		if (!isStraightFlush() && !isRoyalFlush()){
			
			// check if cards are a straight by checking GameValue
			// a straight should have GameValue of last card equal to value of first plus four,
			// 	GameValue of second last equal to value of second plus two, and GameValue of
			//	middle card not equal to values of cards beside it
			if(cards[0].getGameValue()==cards[4].getGameValue()+4 && 
					cards[1].getGameValue()==cards[3].getGameValue()+2 &&
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
	
	public boolean isThreeOfAKind(){
		//three possibilities for three of a kind, first 3 cards are the same, last 3 cards
		//  are the same or three middle cards are the same.
		
		if (!isFullHouse() && !isFourOfAKind()){
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
		if (!isFourOfAKind() && !isFullHouse()  && !isThreeOfAKind() && !isTwoPair()){
			
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
		//returns true if none of the above boolean functions do, false otherwise
		if (!isRoyalFlush() && !isStraightFlush() && !isFourOfAKind() && !isFullHouse()  &&
				!isFlush() && !isStraight() && !isThreeOfAKind() && !isTwoPair() && !isOnePair()){
			return true;
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
	
	
	//for testing 100,000 handOfCards objects are created and a count of how many of the boolean methods
	// return true for that hand is kept. If none or more than one return true then the results of all
	// boolean methods are printed to the console to allow errors to be corrected
	public static void main(String[] args){
		for (int i=0;i<100000;i++){
			DeckOfCards deck = new DeckOfCards();
			HandOfCards hand = new HandOfCards(deck);
			PlayingCard[] cards = new PlayingCard[HAND_SIZE];
			int numTestsPassed = 0;
			if (hand.isRoyalFlush()) { numTestsPassed++; };
			if (hand.isStraightFlush()) { numTestsPassed++; };
			if (hand.isFourOfAKind()) { numTestsPassed++; };
			if (hand.isFullHouse()) { numTestsPassed++; };
			if (hand.isFlush()) { numTestsPassed++; };
			if (hand.isStraight()) { numTestsPassed++; };
			if (hand.isThreeOfAKind()) { numTestsPassed++; };
			if (hand.isTwoPair()) { numTestsPassed++; };
			if (hand.isOnePair()) { numTestsPassed++; };
			if (hand.isHighHand()) { numTestsPassed++; };
			
			if (numTestsPassed!=1){
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
		}
	}
}
