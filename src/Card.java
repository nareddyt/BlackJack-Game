// Teju Nareddy, 5/5/13

import java.awt.Color;
import java.awt.Image;

/**
 * Abstract class that has four subclasses. Contains abstract and concrete methods. Cannot be instantiated.
 *
 * @author Teju
 */
public abstract class Card implements Comparable<Card>
{
	/**
	 * Cannot be constructed, use the subclasses for instantiation (Spade, Heart, Diamond, Club)
	 *
	 * @param num
	 *            Number of the card (1 = Ace, 2 - 10 = normal, 11 = Jack, 12 = Queen, 13 = King)
	 */
	public Card(int num)
	{
		representation = num;
	}
	
	private int representation;
	
	@Override
	/**
	 * Compares current instantiated card with explicit card. First compares the numbers, and if equal, will compare the suits.
	 * To compare suits, this method uses String.compareTo().
	 * Overall, checks numerical, then alphabetical order for the cards.
	 */
	public int compareTo(Card c)
	{
		if (getRepresentation() == c.getRepresentation())
			return getSuit().compareTo(c.getSuit());
		else if (getRepresentation() > c.getRepresentation())
			return 1;
		else
			return -1;
	}
	
	/**
	 * Returns whether the current instantiated card is equal to the explicit card.
	 *
	 * @param c
	 *            The card to check this with
	 * @return
	 *         True: Number, color, and suit match
	 *         False: Any of these do not match
	 */
	public boolean equals(Card c)
	{
		if (getRepresentation() == c.getRepresentation() && getColor().equals(c.getColor()) && getSuit() == c.getSuit())
			return true;
		return false;
	}
	
	public abstract Color getColor();
	
	public abstract Image getImage();
	
	/**
	 * Will return the actually in-game worthiness number. Ace = 1, 2-10 = 2-10, Jack, Queen, King = 10
	 *
	 * @return An integer between 1 and 10
	 */
	public int getNumber()
	{
		if (getRepresentation() == 1)
			return 11;
		else if (getRepresentation() < 10)
			return getRepresentation();
		else
			return 10;
	}
	
	/**
	 * Returns a String representation of the rank of the card. Returns this.getNumber() if 2 <= this <= 10.
	 *
	 * @return Ace, the number, Jack, Queen, or King
	 */
	public String getRank()
	{
		if (representation == 1)
			return "Ace";
		else if (representation <= 10)
			return Integer.toString(representation);
		else if (representation == 11)
			return "Jack";
		else if (representation == 12)
			return "Queen";
		else if (representation == 13)
			return "King";
		else
			throw new IllegalArgumentException("Card Number = " + representation + "??? Programming ERROR!");
	}
	
	/**
	 * Returns the number representation of the card. Note: Use this.getRank() for the String representation.
	 *
	 * @return Ace = 1, 2 - 10 = normal, 11 = Jack, 12 = Queen, 13 = King
	 */
	public int getRepresentation()
	{
		return representation;
	}
	
	/**
	 * Will return a String of the suit of the Card.
	 *
	 * @return Spade, Club, Heart, Diamond
	 */
	public abstract String getSuit();
	
	@Override
	public abstract String toString();
	
}
