
// Teju Nareddy, 5/5/13

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Abstract class that has four subclasses. Contains abstract and concrete methods. Cannot be instantiated.
 *
 * @author Teju
 */
public class Card implements Comparable<Card> {

    public static enum Suit {
        Club, Diamond, Heart, Spade
    }

    private final String imageDir = "src/cardImages/";
    private Image img;
    private int representation;
    private Suit suit;

    /**
     * @param num
     *            Number of the card (1 = Ace, 2 - 10 = normal, 11 = Jack, 12 = Queen, 13 = King)
     * @param s
     *            Suit s (Suit.Spade, Suit.Diamond, Suit.Club, Suit.Heart)
     */
    public Card(int num, Suit s) {
        representation = num;
        suit = s;

        String imageString = imageDir + suit.toString() + "s_" + getRank() + ".png";
        img = Toolkit.getDefaultToolkit().getImage(imageString);
    }

    @Override
    /**
     * Compares current instantiated card with explicit card. First compares the numbers, and if equal, will compare the suits
     */
    public int compareTo(Card c) {
        if (getRepresentation() == c.getRepresentation()) {
            return getSuit().compareTo(c.getSuit());
        } else if (getRepresentation() > c.getRepresentation()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }

        Card c = (Card) obj;
        if (getRepresentation() == c.getRepresentation() && getSuit() == c.getSuit()) {
            return true;
        }
        return false;
    }

    /**
     * Returns the color of the card
     *
     * @return Color
     */
    public Color getColor() {
        if (suit.equals(Suit.Club) || suit.equals(Suit.Spade)) {
            return Color.BLACK;
        }
        return Color.RED;
    }

    /**
     * Returns the card's image
     *
     * @return Image
     */
    public Image getImage() {
        return img;
    }

    /**
     * Will return the actually in-game worthiness number. Ace = 1, 2-10 = 2-10, Jack, Queen, King = 10
     *
     * @return An integer between 1 and 10
     */
    public int getNumber() {
        if (getRepresentation() == 1) {
            return 11;
        } else if (getRepresentation() < 10) {
            return getRepresentation();
        } else {
            return 10;
        }
    }

    /**
     * Returns a String representation of the rank of the card. Returns this.getNumber() if 2 <= this <= 10.
     *
     * @return Ace, the number, Jack, Queen, or King
     */
    public String getRank() {
        if (representation == 1) {
            return "Ace";
        } else if (representation <= 10) {
            return Integer.toString(representation);
        } else if (representation == 11) {
            return "Jack";
        } else if (representation == 12) {
            return "Queen";
        } else if (representation == 13) {
            return "King";
        } else {
            throw new IllegalArgumentException("Card Number = " + representation + "??? Programming ERROR!");
        }
    }

    /**
     * Returns the number representation of the card. Note: Use this.getRank() for the String representation.
     *
     * @return Ace = 1, 2 - 10 = normal, 11 = Jack, 12 = Queen, 13 = King
     */
    public int getRepresentation() {
        return representation;
    }

    /**
     * Will return an enum (Suit) of the suit of the Card.
     *
     * @return Spade, Club, Heart, Diamond
     */
    public Suit getSuit() {
        return suit;
    }

    @Override
    public int hashCode() {
        return suit.hashCode() + representation;
    }

    /**
     * Returns a string that accurately represents the card
     */
    @Override
    public String toString() {
        return getRank() + " of " + getSuit().toString();
    }

}
