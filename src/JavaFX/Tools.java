package JavaFX;

/**
 * Created by nareddyt on 1/10/16.
 */
public class Tools {
    public static String valueTextToNumber(Card.Value value) {
        String toReturn = "";

        int num = value.ordinal();
        if (num >= 2 && num <= 10) {
            toReturn = Integer.toString(num);
        } else {
            toReturn = value.toString();
        }

        return toReturn;
    }
}
