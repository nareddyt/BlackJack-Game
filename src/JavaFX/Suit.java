package JavaFX;

import javafx.scene.paint.Color;

public enum Suit {
    Club, Diamond, Heart, Spade;

    /**
     * Returns a color based on the suit.
     *
     * @param suit the suit of the card
     * @return JavaFX color: Black or Red
     */
    public static Color getColor(Suit suit) {
        if (suit.equals(Suit.Club) || suit.equals(Suit.Spade)) {
            return Color.BLACK;
        }
        return Color.RED;
    }
}
