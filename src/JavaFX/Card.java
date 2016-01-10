package JavaFX;

import java.awt.*;

/**
 * Class that represents a Card.
 *
 * @author Teju Nareddy
 */
public class Card implements Comparable<Card> {

    public enum Suit {
        Club, Diamond, Heart, Spade
    }

    public enum Value {
        // Order matters! Do not change
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
    }

    private final String imageDir = "src/cardImages/";
    private Image img;
    private Suit suit;
    private Value value;

    /**
     * @param value Value of the Card
     * @param suit  Suit of the card
     */
    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;

        String imageString = imageDir + suit.toString() + "s_" + Tools.valueTextToNumber(value) + ".png";
        img = Toolkit.getDefaultToolkit().getImage(imageString);
    }
}
