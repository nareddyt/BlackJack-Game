import java.util.Random;

// Teju Nareddy, 5/8/13
// TODO make sure stack will not run out of cards

/**
 * A class that holds multiple decks. THIS CLASS SHOULD BE USED TO DRAW CARDS, NOT DECK! Remember to call shuffle after creation.
 *
 * @author Teju
 *
 */
public class Stack {
    private Deck[] decks;

    private Random rand = new Random();

    public Stack(int numOfDecks) {
        decks = new Deck[numOfDecks];
        for (int i = 0; i < decks.length; i++) {
            decks[i] = new Deck();
        }
    }

    /**
     * Get the next card in the Stack
     *
     * @return Card
     */
    public Card getCard() {
        int index = rand.nextInt(decks.length);
        return decks[index].getCard();
    }

    /**
     * Number of cards left in the Stack
     *
     * @return int representing the number of cards left
     */
    public int getNumberOfCardsLeft() {
        int total = 0;
        for (Deck d : decks) {
            total += d.getNumberOfCardsLeft();
        }

        return total;
    }

    /**
     * Shuffles the whole Stack
     *
     * @param amount
     *            Number of times the stack should be shuffled between 1 and 15
     */
    public void shuffle(int amount) {
        for (Deck d : decks) {
            d.shuffle(amount);
            // Note that this is not a true shuffle, as the decks are not
            // shuffled in between each other
        }
    }
}
