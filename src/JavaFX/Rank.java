package JavaFX;

public enum Rank {
    // Order matters! Do not change
    Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;

    /**
     * Returns the value of a card based on rank
     *
     * @param rank the card's rank
     * @return an in representing the value of the card
     */
    public static int getRankValue(Rank rank) {
        int num = rank.ordinal();
        if (num >= 10) {
            return 11;
        } else {
            return num + 1;
        }
    }

    /**
     * Converts the rank text to a number if it is a number written out in
     * words.
     *
     * @param rank the rank of the card
     * @return A string representing the integer number of the card. Will
     * return the original rank string if it cannot be represented as an
     * integer.
     */
    public static String rankTextToNumber(Rank rank) {
        int num = rank.ordinal();
        if (num >= 1 && num <= 9) {
            return Integer.toString(num + 1);
        } else {
            return rank.toString();
        }
    }

    /**
     * Returns a rank based on the card's number representation (not based on
     * the value of the card!)
     *
     * @param num the number representation (not value)
     * @return the rank of the card
     */
    public static Rank representationNumToRank(int num) {
        return values()[num - 1];
    }
}
