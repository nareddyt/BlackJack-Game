package JavaFX;

import Exceptions.InvalidDeckNumberException;

import java.util.Collections;
import java.util.Stack;

/**
 * Created by nareddyt on 1/15/16.
 */
public class Deck {
    private Stack<Card> cards;

    public Deck(int numberOfDecks) throws InvalidDeckNumberException {
        if (numberOfDecks < 1) {
            throw new InvalidDeckNumberException("User choose " + numberOfDecks + " decks.");
        }

        cards = new Stack<Card>();
        for (int i = 0; i < numberOfDecks; i++) {
            for (int j = 1; j <= 13; j++) {
                cards.push(new Card(Rank.representationNumToRank(j), Suit.Club));
                cards.push(new Card(Rank.representationNumToRank(j), Suit.Diamond));
                cards.push(new Card(Rank.representationNumToRank(j), Suit.Heart));
                cards.push(new Card(Rank.representationNumToRank(j), Suit.Spade));
            }
        }

        //shuffle();
    }

    public static void main(String[] args) throws InvalidDeckNumberException {
        Deck d = new Deck(1);
        Card c;
        do {
            c = d.drawCard();
            System.out.println(c);
        } while (d.hasMoreCards());
    }

    public Card drawCard() {
        return cards.pop();
    }

    public boolean hasMoreCards() {
        return !cards.empty();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }
}
