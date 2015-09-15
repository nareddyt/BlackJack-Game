import java.util.ArrayList;

// Teju Nareddy, 8/24/13

public class BlackjackTools {
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

    public static int getNumberOfCardsInHand(Hand h) {
        return h.getCards().size();
    }

    public static int getNumberOfCardsInPlayer(Player p) {
        int cards = 0;

        for (int i = 0; i < getNumberOfHandsInPlayer(p); i++) {
            cards += p.getHandList().get(i).getCards().size();
        }
        return cards;
    }

    public static int getNumberOfHandsInPlayer(Player p) {
        return p.getHandList().size();
    }

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

    public static boolean hasSplit(Player p) {
        if (p.getHandList().size() == 1) {
            return false;
        } else if (p.getHandList().size() == 2) {
            return true;
        } else {
            throw new IllegalArgumentException("Player " + p.toString()
                    + " has " + p.getHandList().size() + " hands!");
        }
    }

    public static boolean isBlackJack(Hand h) {
        ArrayList<Card> cards = h.getCards();
        if (cards.size() == 2 && getSumOfCardsInHand(h) == 21) {
            return true;
        }
        return false;
    }

    public static boolean isIn(Player p) {
        if (getHighestHand(p.getHandList()) == null) {
            return false;
        }
        return true;
    }
}
