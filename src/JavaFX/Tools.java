package JavaFX;

/**
 * @author Teju Nareddy
 */
public class Tools {
    public static String rankTextToNumber(Card.Rank rank) {
        String toReturn = "";

        int num = rank.ordinal();
        if (num >= 2 && num <= 10) {
            toReturn = Integer.toString(num);
        } else {
            toReturn = rank.toString();
        }

        return toReturn;
    }
}
