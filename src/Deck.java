
// Teju Nareddy, 5/5/13

import java.util.Random;

/**
 * A class that contains the 52 standard cards. <<<DO NOT USE THIS CLASS FOR THE STACK OF CARDS TO DRAW FROM! SEE STACK CLASS>>>
 *
 * @author Teju
 *
 */
public class Deck {
    private Card[] cards;

    private Random rand = new Random();
    private int spot = 0;

    /**
     * Creates a new standard Deck of 52 cards. Cards are not shuffled!
     */
    public Deck() {
        cards = new Card[52];
        int index = -1;
        for (int num = 0; num < 13; num++) {
            cards[++index] = new Card(num + 1, Card.Suit.Spade); // Spade -->
                                                                 // Black
            cards[++index] = new Card(num + 1, Card.Suit.Club); // Club -->
                                                                // Black
            cards[++index] = new Card(num + 1, Card.Suit.Heart); // Heart -->
                                                                 // Red
            cards[++index] = new Card(num + 1, Card.Suit.Diamond); // Diamond
                                                                   // --> Red
        }
    }

    /**
     * Gets the Card at the top of the deck Precondition: Shuffle must be called first for an ordered deck. Otherwise, a random card may not be selected. Precondition: The deck
     * must have at least one Card left. Use getNumberOfCardsLeft() to check. Postcondition: Will return a Card that has not been drawn yet.
     *
     * @throws IndexOutOfBoundsException
     *             If there are no more cards left in the deck
     * @return A Card (Spade, Club, Heart, Diamond)
     */
    public Card getCard() {
        if (getNumberOfCardsLeft() == 0) {
            throw new IndexOutOfBoundsException("All cards have been taken already!");
        }

        Card c = cards[spot];
        spot++;
        return c;
    }

    /**
     * Returns the number of cards left in the deck
     *
     * @return
     */
    public int getNumberOfCardsLeft() {
        return cards.length - spot;
    }

    /**
     * Shuffles the Deck. All 52 cards are included in the deck again. Shuffles with random swapping. Postcondition: The Deck is shuffled.
     *
     * @param amount
     *            The number of times to shuffle the deck. Should be an integer between 1 and 15 (inclusive).
     */
    public void shuffle(int amount) {
        spot = 0;

        if (amount > 15) {
            amount = 15;
        }
        if (amount < 1) {
            amount = 1;
        }

        for (int times = 1; times <= amount; times++) {
            for (int i = 0; i < cards.length; i++) {
                int spot = rand.nextInt(52);
                Card temp = cards[i];
                cards[i] = cards[spot];
                cards[spot] = temp;
            }
        }
    }
}
