package JavaFX;

public enum Rank {
    // Order matters! Do not change
    Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;

    public static String rankTextToNumber(Rank rank) {
        int num = rank.ordinal();
        if (num >= 2 && num <= 10) {
            return Integer.toString(num);
        } else {
            return rank.toString();
        }
    }

    public static int rankTextToInt(Rank rank) {
        int num = rank.ordinal();
        if (num >= 11) {
            return 11;
        } else {
            return num;
        }
    }
}
