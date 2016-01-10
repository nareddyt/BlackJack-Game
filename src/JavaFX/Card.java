package JavaFX;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Class that represents a Card.
 *
 * @author Teju Nareddy
 */
public class Card implements Comparable<Card> {

    private final String imageDir = "src/cardImages/";
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

        String imageString = imageDir + suit.toString() + "s_" + Rank.rankTextToNumber(rank) + ".png";
        image = new Image(imageString);
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Image getImage() {
        return image;
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

    @Override
    public int compareTo(Card o) {
        if (o.suit.equals(this.suit)) {
            return this.rank.ordinal() - o.rank.ordinal();
        } else {
            return this.suit.ordinal() - o.suit.ordinal();
        }
    }
}
