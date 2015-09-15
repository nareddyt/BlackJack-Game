
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
    public static final double SLIP_OF_HAND = 0.0525;

    private ArrayList<Hand> hands = new ArrayList<Hand>();

    private String name = "";

    private final Random rand = new Random();
    private double bet;
    private double money;
    private boolean isComputer;
    private Card dropped = null;

    public Player(String name, boolean isAi) {
        this.name = name;
        isComputer = isAi;
    }

    public Player(String name, int hands, boolean isAi) {
        this(name, isAi);
        for (int i = 0; i < hands; i++) {
            this.hands.add(new Hand());
        }
    }

    public boolean equals(Player p) {
        if (getName() == p.getName() && getMoney() == p.getMoney()
                && getHandList().equals(p.getHandList())) {
            return true;
        }
        return false;
    }

    public double getBet() {
        return bet;
    }

    public Card getDropped() {
        return dropped;
    }

    public ArrayList<Hand> getHandList() // Hands can be added, deleted,
                                         // modified without a setter (object
                                         // reference is the same)
    {
        return hands;
    }

    public double getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public boolean hasCards() {
        return BlackjackTools.getNumberOfCardsInPlayer(this) > 0;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void resetRound() {
        dropped = null;
        bet = 0;
        hands = new ArrayList<Hand>();
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public void setDropped() {
        if (hasCards() && dropped == null) {
            if (rand.nextDouble() < Player.SLIP_OF_HAND) {
                for (int i = 0; i < hands.size(); i++) {
                    ArrayList<Card> c = hands.get(0).getCards();
                    if (c.size() > 0) {
                        dropped = hands.get(0).getCards().get(c.size() - 1);
                    }
                }
            }
        }
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return getName();
    }
}
