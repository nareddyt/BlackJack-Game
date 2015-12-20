package JSwing;

// Teju Nareddy, 5/5/13

import java.util.ArrayList;
import java.util.Random;

/**
 * The Player (dealer or player). Contains multiple Hands.
 *
 * @author Teju
 *
 */
public class Player {
    // FIXME test value
    public static final double SLIP_OF_HAND = 1;

    private double bet;

    private Card dropped = null;

    private ArrayList<Hand> hands = new ArrayList<Hand>();
    private boolean isComputer;
    private double money;
    private String name = "";
    private final Random rand = new Random();

    /**
     * Creates a new player with an empty Hand
     *
     * @param name
     *            is the player name
     * @param isAi
     *            specifying whether the player is an AI
     */
    public Player(String name, boolean isAi) {
        this.name = name;
        isComputer = isAi;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }

        Player p = (Player) obj;
        if (getName() == p.getName() && getMoney() == p.getMoney() && getHandList().equals(p.getHandList())) {
            return true;
        }
        return false;
    }

    /**
     * Get the player's current bet
     *
     * @return double
     */
    public double getBet() {
        return bet;
    }

    /**
     * Get the current dropped card. Call setDropped() before to calculate
     *
     * @return Card that is dropped. Will be null if player has dropped no card!
     */
    public Card getDropped() {
        return dropped;
    }

    /**
     * Get the Arraylist of Hands
     *
     * @return ArrayList<Hand>
     */
    public ArrayList<Hand> getHandList() {
        return hands;
    }

    /**
     * Get the total amount of money the player has
     *
     * @return double
     */
    public double getMoney() {
        return money;
    }

    /**
     * Get the inputed name of the player
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    // TODO: Is this needed?
    /**
     * Returns whether the player has cards
     *
     * @return boolean
     */
    public boolean hasCards() {
        return BlackJackTools.getNumberOfCardsInPlayer(this) > 0;
    }

    @Override
    public int hashCode() {
        return (int) (bet + dropped.hashCode() + hands.hashCode() + money + name.hashCode());
    }

    /**
     * Is this player an AI?
     *
     * @return boolean
     */
    public boolean isComputer() {
        return isComputer;
    }

    /**
     * Resets the player's variables at the end of a round
     */
    public void resetRound() {
        dropped = null;
        bet = 0;
        hands = new ArrayList<Hand>();
    }

    // TODO check for bet > money
    /**
     * Set the player's bet
     *
     * @param bet
     */
    public void setBet(double bet) {
        this.bet = bet;
    }

    /**
     * Recalculates the player's dropped Card
     */
    public void setDropped() {
        if (hasCards() && dropped == null) {
            if (rand.nextDouble() < Player.SLIP_OF_HAND) {
                for (int i = 0; i < hands.size(); i++) {
                    ArrayList<Card> c = hands.get(i).getCards();
                    if (c.size() > 0) {
                        dropped = hands.get(0).getCards().get(c.size() - 1);
                        break;
                    }
                }
            } else {
                dropped = null;
            }
        }
    }

    /**
     * Set money
     *
     * @param money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return getName();
    }
}
