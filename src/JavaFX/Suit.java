package JavaFX;

import javafx.scene.paint.Color;

public enum Suit {
    Club, Diamond, Heart, Spade;

    public static Color getColor(Suit suit) {
        if (suit.equals(Suit.Club) || suit.equals(Suit.Spade)) {
            return Color.BLACK;
        }
        return Color.RED;
    }
}
