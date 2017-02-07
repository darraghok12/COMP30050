/* Darragh O'Keeffe
 * 14702321
 * COMP 30050 Assignment 2
 * 07/02/2017
 */

package poker;

import java.util.Random;

public class DeckOfCards {
	//Constant to hold possible number of cards
	private static final int DECK_SIZE = 52;
	
	//DeckOfCards instance variables
	private PlayingCard[] deck = new PlayingCard[DECK_SIZE];
	private int cardsUsed;
	
	// Reorganises the deck in a new random order by swapping cards 52^2 times
	public void shuffle(){
		int firstCard, secondCard;
		PlayingCard temp;
		Random randomGenerator = new Random();
		for (int i=0;i<DECK_SIZE*DECK_SIZE;i++){
			firstCard = randomGenerator.nextInt(DECK_SIZE);
			secondCard = randomGenerator.nextInt(DECK_SIZE);
			temp = deck[firstCard];
			deck[firstCard] = deck[secondCard];
			deck[secondCard] = temp;
		}
		this.cardsUsed = 0;
	}
	
	// Returns the card at deck[cardsUsed] if the deck is non-empty
	// Returns a special Null card otherwise
	public PlayingCard deal(){
		if (cardsUsed==DECK_SIZE){
			PlayingCard nullCard = new PlayingCard("null", ' ', 0, 0);
			return nullCard;
		}
		else{
			return deck[cardsUsed++];
		}
	}
	
	// resets and shuffles the deck to begin a new round 
	public void reset(){
		for (int i=0;i<13;i++){
			int faceValue = i+1;
			int gameValue = i+1; 
			String type;
			
			// switch statement sets the value of type to the correct value
			switch(i+1){
				case 1:
					type = "A";
					gameValue = 14;
					break;
				case 11:
					type = "J";
					break;
				case 12:
					type = "Q";
					break;
				case 13:
					type = "K";
					break;
				default:
					type = Integer.toString(i+1);
			}
			
			// On each iteration of the loop, all 4 cards of the same type are made and added to the cardArray
			deck[i] = new PlayingCard(type, PlayingCard.CLUBS, faceValue, gameValue);
			deck[i+13] = new PlayingCard(type, PlayingCard.DIAMONDS, faceValue, gameValue);
			deck[i+26] = new PlayingCard(type, PlayingCard.HEARTS, faceValue, gameValue);
			deck[i+39] = new PlayingCard(type, PlayingCard.SPADES, faceValue, gameValue);
		}
		shuffle();
		this.cardsUsed = 0;
	}
	
	// Returns the number of cards in the deck that can be dealt
	public int cardsLeft(){
		return DECK_SIZE-cardsUsed;
	}
	
	// Returns a string containing the toString representation of all cards
	//  that have not yet been dealt from the deck
	public String toString(){
		String output = "";
		for (int i=cardsUsed;i<DECK_SIZE;i++){
			output += deck[i].toString()+" ";
		}
		return output;
	}
	
	// Empty Public Constructor calls the PlayingCard constructor 52 times and sets cardsUsed=0
	public DeckOfCards(){
		for (int i=0;i<13;i++){
			int faceValue = i+1;
			int gameValue = i+1; 
			String type;
			
			// switch statement sets the value of type to the correct value
			switch(i+1){
				case 1:
					type = "A";
					gameValue = 14;
					break;
				case 11:
					type = "J";
					break;
				case 12:
					type = "Q";
					break;
				case 13:
					type = "K";
					break;
				default:
					type = Integer.toString(i+1);
			}
			
			// On each iteration of the loop, all 4 cards of the same type are made and added to the cardArray
			deck[i] = new PlayingCard(type, PlayingCard.CLUBS, faceValue, gameValue);
			deck[i+13] = new PlayingCard(type, PlayingCard.DIAMONDS, faceValue, gameValue);
			deck[i+26] = new PlayingCard(type, PlayingCard.HEARTS, faceValue, gameValue);
			deck[i+39] = new PlayingCard(type, PlayingCard.SPADES, faceValue, gameValue);
		}
		shuffle();
		this.cardsUsed = 0;
	}
	
	//tests DeckOfCards Class
	public static void main(String[] args){
		DeckOfCards deck = new DeckOfCards();
		
		//Tests for dealing cards and for dealing from an empty deck
		System.out.println("Deal 52 cards and print toString representation");
		for (int i=0;i<52;i++){
			System.out.println(deck.deal().toString());
		}
		System.out.println("\nAttempt to deal a 53rd card and print toString representation");
		System.out.println(deck.deal().toString());
		
		//test for shuffle and deck toString method
		deck.shuffle();
		System.out.println("\nTest shuffle and DeckOfCards.toString() method");
		System.out.println(deck.toString());
	}
}
