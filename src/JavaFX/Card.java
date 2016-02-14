package JavaFX;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Class that represents a Card.
 *
 * @author Teju Nareddy
 */
public class Card implements Cloneable, Comparable<Card> {

    private final String imageDir = "/src/cardImages/";
    private Image image;
    private Suit suit;
    private Rank rank;

    /**
     * @param rank Rank of the Card
     * @param suit  Suit of the card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        image = new Image("file:" + System.getProperty("user.dir") +
                imageDir + suit.toString() + "s_" + Rank.rankTextToNumber
                (rank) + ".png");
    }

    @Override
    public int compareTo(Card o) {
        if (o.suit.equals(this.suit)) {
            return this.rank.ordinal() - o.rank.ordinal();
        } else {
            return this.suit.ordinal() - o.suit.ordinal();
        }
    }

    /**
     * Returns the color of the card
     *
     * @return Color
     */
    public Color getColor() {
        return Suit.getColor(suit);
    }

    public Image getImage() {
        return image;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public int hashCode() {
        return 17 * rank.hashCode() + 19 * suit.hashCode() + 31;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Card)) {
            return false;
        }

        Card c = (Card) o;
        return c.rank.equals(this.rank) && c.suit.equals(this.suit);
    }

    @Override
    public Card clone() {
        return new Card(rank, suit);
    }

    @Override
    public String toString() {
        return Rank.rankTextToNumber(rank) + " of " + suit.toString();
    }
}
