package JavaFX;

public enum Rank {
    // Order matters! Do not change
    Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;

    public static String rankTextToNumber(Rank rank) {
        int num = rank.ordinal();
        if (num >= 1 && num <= 9) {
            return Integer.toString(num + 1);
        } else {
            return rank.toString();
        }
    }

    public static int rankTextToInt(Rank rank) {
        int num = rank.ordinal();
        if (num >= 10) {
            return 11;
        } else {
            return num + 1;
        }
    }

    public static Rank representationNumToRank(int num) {
        return values()[num - 1];
    }
}
