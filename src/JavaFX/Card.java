package JavaFX;

import javafx.scene.image.Image;

/**
 * Class that represents a Card.
 *
 * @author Teju Nareddy
 */
public class Card implements Comparable<Card> {
    public enum Suit {
        Club, Diamond, Heart, Spade
    }

    public enum Rank {
        // Order matters! Do not change
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
    }

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

        String imageString = imageDir + suit.toString() + "s_" + Tools.rankTextToNumber(rank) + ".png";
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

    @Override
    public int compareTo(Card o) {
        if (o.suit.equals(this.suit)) {
            return this.rank.ordinal() - o.rank.ordinal();
        } else {
            return this.suit.ordinal() - o.suit.ordinal();
        }
    }
}
