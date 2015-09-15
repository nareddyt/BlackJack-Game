
// Teju Nareddy, 5/5/13

import java.util.ArrayList;

/**
 * Contains the Player's cards.
 *
 * @author Teju
 *
 */
public class Hand {
    private ArrayList<Card> cards = new ArrayList<Card>();

    public ArrayList<Card> getCards() // Cards can be added, deleted, modified
                                      // without a setter (object reference is
                                      // the same)
    {
        return cards;
    }
}
