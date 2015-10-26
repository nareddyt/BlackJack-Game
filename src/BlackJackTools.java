import java.util.ArrayList;

// Teju Nareddy, 8/24/13

public class BlackJackTools {
    /**
     * Returns the highest card in a hand
     *
     * @param h
     * @return
     */
    public static Card getHighestCard(Hand h) {
        int high = 0;
        Card highest = null;
        ArrayList<Card> cards = h.getCards();
        for (Card c : cards) {
            int temp = c.getNumber();
            if (temp > high) {
                high = temp;
                highest = c;
            }
        }
        return highest;
    }

    /**
     * Returns the highest hand
     *
     * @param hands
     * @return
     */
    public static Hand getHighestHand(ArrayList<Hand> hands) {
        int high = 0;
        Hand highest = null;
        for (Hand h : hands) {
            int temp = getSumOfCardsInHand(h);
            if (temp > high && temp <= 21) {
                high = temp;
                highest = h;
            }
        }
        return highest;
    }

    public static int getHighestNumberOfCardsInHand(Player p) {
        int high = 0;
        for (Hand h : p.getHandList()) {
            int temp = getNumberOfCardsInHand(h);
            if (temp > high) {
                high = temp;
            }
        }

        return high;
    }

    /**
     * Returns the player with the highest hand (not including players that are out)
     *
     * @param players
     * @return
     */
    public static Player getHighestPlayer(ArrayList<Player> players) {
        int high = 0;
        Player highest = null;
        for (Player p : players) {
            Hand h = getHighestHand(p.getHandList());
            int temp = getSumOfCardsInHand(h);
            if (temp > high && temp <= 21) {
                high = temp;
                highest = p;
            }
        }
        return highest;
    }

    /**
     * Returns the number of aces in a hand
     *
     * @param hand
     * @return
     */
    public static int getNumberOfAcesInHand(Hand hand) {
        int aces = 0;
        ArrayList<Card> cards = hand.getCards();
        for (Card c : cards) {
            if (c.getRepresentation() == 1) {
                aces++;
            }
        }
        return aces;
    }

    /**
     * Returns the number of cards in a hand
     *
     * @param h
     * @return
     */
    public static int getNumberOfCardsInHand(Hand h) {
        return h.getCards().size();
    }

    /**
     * Returns the number of cards a player has
     *
     * @param p
     * @return
     */
    public static int getNumberOfCardsInPlayer(Player p) {
        int cards = 0;

        for (int i = 0; i < getNumberOfHandsInPlayer(p); i++) {
            cards += p.getHandList().get(i).getCards().size();
        }
        return cards;
    }

    /**
     * Returns the number of hands a player has
     *
     * @param p
     * @return
     */
    public static int getNumberOfHandsInPlayer(Player p) {
        return p.getHandList().size();
    }

    /**
     * Returns the sum of all the cards in a hand. Takes into consideration aces
     *
     * @param hand
     * @return
     */
    public static int getSumOfCardsInHand(Hand hand) {
        ArrayList<Card> cards = hand.getCards();
        if (cards == null || cards.size() == 0) {
            return -1;
        }
        int total = 0;

        for (Card c : cards) {
            total += c.getNumber();
        }

        int aces = getNumberOfAcesInHand(hand);
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    /**
     * Determines if the player has split his Hand into two
     *
     * @param p
     * @return
     */
    public static boolean hasSplit(Player p) {
        if (p.getHandList().size() == 1) {
            return false;
        } else if (p.getHandList().size() == 2) {
            return true;
        } else {
            throw new IllegalArgumentException("Player " + p.toString() + " has " + p.getHandList().size() + " hands!");
        }
    }

    /**
     * Determines if a hand is a BlackJack combination (not a true BlackJack)
     *
     * @param h
     * @return
     */
    public static boolean isBlackJack(Hand h) {
        ArrayList<Card> cards = h.getCards();
        if (cards.size() == 2 && getSumOfCardsInHand(h) == 21) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether the player is still in the game (Has at least one hand <= 21)
     *
     * @param p
     * @return
     */
    public static boolean isIn(Player p) {
        if (getHighestHand(p.getHandList()) == null) {
            return false;
        }
        return true;
    }
}
