import java.util.Random;

// Teju Nareddy, 5/8/13

/**
 * A class that holds multiple decks. THIS CLASS SHOULD BE USED TO DRAW CARDS, NOT DECK...
 *
 * @author Teju
 *
 */
public class Stack
{
	public Stack(int numOfDecks)
	{
		decks = new Deck[numOfDecks];
		for (int i = 0; i < decks.length; i++)
		{
			decks[i] = new Deck();
		}
	}
	
	private Random rand = new Random();
	
	private Deck[] decks;
	
	public Card getCard()
	{
		int index = rand.nextInt(decks.length);
		return decks[index].getCard();
	}
	
	public int getNumberOfCardsLeft()
	{
		int total = 0;
		for (Deck d : decks)
		{
			total += d.getNumberOfCardsLeft();
		}
		
		return total;
	}
	
	public void shuffle(int amount)
	{
		for (Deck d : decks)
		{
			d.shuffle(amount);
		}
	}
}
