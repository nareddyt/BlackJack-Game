
// Teju Nareddy, 5/5/13

import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * Tests the Deck class, the Card class, and all of its subclasses. NOTE: THIS
 * CLASS IS ONLY FOR TESTING THE DECK. IT IS NOT PART OF THIS PROJECT.
 *
 * @author Teju
 *
 */
@SuppressWarnings("serial")
public class DeckTester extends JFrame {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Deck d = new Deck();
        for (int i = 0; i < 52; i++) {
            System.out.println(d.getCard().toString() + "s");
        }
        d.shuffle(5);
        System.out.println("\n---\n");

        boolean first = true;
        while (d.getNumberOfCardsLeft() > 0) {
            String s = d.getCard().toString();
            System.out.println(s + "s");
            if (s.contains("Queen") && first) {
                System.out.println("\n<<--->>\n");
                d.shuffle(635);
                first = false;
            }
        }

        new DeckTester();

        System.out.println("\nIT WORKS");
    }

    Deck deck = new Deck();
    int count = 52;

    public DeckTester() {
        super("Testing program -- Teju Nareddy");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);

        while (true) {
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (count >= 51) {
            deck.shuffle(15);
            count = 0;
            g.drawString("Shuffled", 600, 600);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        g.drawImage(deck.getCard().getImage(), 50, 50, 200, 250, this);
        count++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
