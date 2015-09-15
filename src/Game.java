
// Teju Nareddy, 5/5/13
// TODO check for isWinner somewhere

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.LayerUI;

/*
 * Steps:
 * 1. Get Player Names in main
 * 2. Start Game
 * 3. Start Round
 * 4. Get Player Bets
 * 5. Distribute Cards
 * 6. Check for BlackJack
 * 7. Five choices until end of round
 * 8. Check cards
 * 9. Give winners money
 * 10. Repeat 2-9 until all except one player had $0
 * 11. End of Game
 */

/**
 * The driver class and the GUI. Used to play the game BlackJack.
 *
 * @author Teju
 *
 */
public class Game implements ActionListener, KeyListener {
    public class ValidationLayerUI extends LayerUI<JFormattedTextField> {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void paint(Graphics g, JComponent c) {
            if (c == null || g == null) {
                return;
            }

            super.paint(g, c);
            JLayer<?> jlayer = (JLayer<?>) c;
            JFormattedTextField ftf = (JFormattedTextField) jlayer.getView();

            if (ftf == null) {
                return;
            }

            boolean correct = false;
            ftf.setText(ftf.getText().replaceAll(",", ""));

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int w = c.getWidth();
            int h = c.getHeight();
            int s = 8;
            int pad = 4;
            int x = w - pad - s;
            int y = (h - s) / 2;

            if (!ftf.isEditValid() || ftf.getText().equals("")
                    || ftf.getText() == null) {
                if (ftf.getText().equals("") || ftf.getText() == null) {
                    if (ftf.getFormatter().toString()
                            .contains("NumberFormatter")) {
                        g2.setPaint(Color.red);
                    } else {
                        g2.setPaint(Color.orange);
                    }
                } else if (!ftf.isEditValid()) {
                    g2.setPaint(Color.red);
                } else {
                    throw new IllegalArgumentException(
                            "Illegal Condition -- Contant Teju Nareddy...");
                }
            } else if (ftf.getName().equals("Decks")) {
                try {
                    if (Integer.parseInt(ftf.getText()) < 1) {
                        g2.setPaint(Color.orange);
                    } else {
                        correct = true;
                    }
                } catch (NumberFormatException e) {
                    g2.setPaint(Color.red);
                }
            } else if (ftf.getName().equals("Players")) {
                try {
                    if (Integer.parseInt(ftf.getText()) < 2
                            || Integer.parseInt(ftf.getText()) > 8) {
                        g2.setPaint(Color.orange);
                    } else {
                        correct = true;
                    }
                } catch (NumberFormatException e) {
                    g2.setPaint(Color.red);
                }
            } else if (ftf.getName().equals("Bet")) {
                try {
                    if (Double.parseDouble(ftf.getText()) < 20 || Double
                            .parseDouble(ftf.getText()) > viewer.getMoney()) {
                        g2.setPaint(Color.orange);
                    } else {
                        correct = true;
                    }
                } catch (NumberFormatException e) {
                    g2.setPaint(Color.red);
                }
            } else {
                correct = true;
            }

            if (correct) {
                g2.setPaint(Color.green);
            }
            g2.fillRect(x, y, s + 1, s + 1);
            g2.setPaint(Color.white);

            if (!correct) {
                g2.drawLine(x, y, x + s, y + s);
                g2.drawLine(x, y + s, x + s, y);
            } else {
                g2.drawLine(x, y + s / 2, x + s / 2, y + s);
                g2.drawLine(x + s / 2, y + s, x + s, y);
            }

            g2.dispose();
        }
    }

    private static final long serialVersionUID = -6083000962624958450L;

    private static PrintStream outputFile;

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        new Game(); // Just making a new object. Will just like a main method
                    // for the object.
    }

    private final Dimension userScreen = java.awt.Toolkit.getDefaultToolkit()
            .getScreenSize();
    private final double STARTING_MONEY = 1500.00;

    private final Random rand = new Random();
    private JFrame f;
    private JFrame playerCards;
    private JFrame otherCards;

    private LayerUI<JFormattedTextField> layerUI = new ValidationLayerUI();
    private PopupMenu popup = new PopupMenu();
    private TrayIcon trayIcon = new TrayIcon(
            Toolkit.getDefaultToolkit().getImage("Suits.png"));

    private SystemTray tray = SystemTray.getSystemTray();
    private JButton getText = new JButton("OK"); // Make sure to change
    private JFormattedTextField textField1;
    private JFormattedTextField textField2;
    private JFormattedTextField textField3;

    private JTextArea outputConsole = new JTextArea(20, 50);
    // 5 choices in the game
    private JButton hit = new JButton("Hit");
    private JButton stand = new JButton("Stand");
    private JButton doubleDown = new JButton("Double Down");
    private JButton split = new JButton("Split");
    private JButton surrender = new JButton("Surrender");

    private boolean paused = false;
    private boolean endOfRound = false;
    private boolean isWinner = false;
    private int seconds = 0;

    private int minutes = 0;
    private Stack stack;
    private ArrayList<Player> players;

    private Player viewer;

    public Game() // The "main" method
    {
        TextAreaOutputStream taos = new TextAreaOutputStream(outputConsole, 60);
        PrintStream ps = new PrintStream(taos);
        // TODO
        // 2 lines below set console in the JFrame
        // System.setErr(ps);
        // System.setOut(ps);

        f = new JFrame("Game Options");
        f.setLayout(new BorderLayout());
        f.setAlwaysOnTop(false);
        f.add(new JLabel(
                "Change the settings for the game here. Press bottom button when finished..."),
                BorderLayout.NORTH);

        getText = new JButton("Start Game");
        getText.addActionListener(this);
        getText.setToolTipText(
                "Fill out the above boxes and press this to start the game of BlackJack!");

        JPanel fields = new JPanel(new GridLayout(3, 2)); // Now fields are used
                                                          // for the following

        textField1 = new JFormattedTextField(NumberFormat.getIntegerInstance());
        fields.add(new JLabel("Number of Decks"));
        textField1
                .setToolTipText("Each deck contains 52 cards with no Jokers.");
        textField1.setName("Decks");
        fields.add(new JLayer<JFormattedTextField>(textField1, layerUI));

        textField2 = new JFormattedTextField(NumberFormat.getIntegerInstance());
        fields.add(new JLabel("Number of Players"));
        textField2.setToolTipText(
                "This number includes you, so if n is inputed, there are n-1 computers as players.");
        textField2.setName("Players");
        fields.add(new JLayer<JFormattedTextField>(textField2, layerUI));

        textField3 = new JFormattedTextField();
        fields.add(new JLabel("Your Name"));
        textField3.setToolTipText("Just for display purposes ;)");
        textField3.setName("Name");
        textField3.setValue("");
        fields.add(new JLayer<JFormattedTextField>(textField3, layerUI));

        JPanel options = new JPanel(new BorderLayout());
        options.add(fields, BorderLayout.CENTER);
        options.add(getText, BorderLayout.SOUTH);

        f.add(options, BorderLayout.CENTER);
        f.add(new JScrollPane(outputConsole), BorderLayout.SOUTH); // don't
                                                                   // forget
                                                                   // JScrollPane()
        f.pack();
        f.setVisible(true);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        f.toFront();

        if (SystemTray.isSupported()) {
            // Create a pop-up menu components
            MenuItem aboutItem = new MenuItem("About");
            MenuItem infoItem = new MenuItem("Info");
            MenuItem exitItem = new MenuItem("Exit");
            MenuItem helpItem = new MenuItem("Help");
            MenuItem size = new MenuItem("Reset Size");

            /*
             * CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip"); Menu
             * displayMenu = new Menu("Display"); MenuItem errorItem = new
             * MenuItem("Error"); MenuItem warningItem = new
             * MenuItem("Warning");
             *
             * MenuItem noneItem = new MenuItem("None");
             */
            // Add components to pop-up menu
            popup.setName("BlackJack -- Teju Nareddy");
            popup.setLabel("BlackJack -- Teju Nareddy");

            exitItem.addActionListener(this);
            aboutItem.addActionListener(this);
            infoItem.addActionListener(this);
            helpItem.addActionListener(this);
            size.addActionListener(this);

            popup.add(aboutItem);
            popup.add(infoItem);
            popup.add(helpItem);
            popup.addSeparator();
            popup.add(size);
            popup.addSeparator();
            popup.add(exitItem);
            /*
             * popup.add(cb2); popup.addSeparator(); popup.add(displayMenu);
             * displayMenu.add(errorItem); displayMenu.add(warningItem);
             *
             * displayMenu.add(noneItem);
             */

            trayIcon.setPopupMenu(popup);
            trayIcon.setToolTip("BlackJack -- Teju Nareddy");
            trayIcon.setImageAutoSize(true);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("TrayIcon could not be added.");
            }
        }

        // Go to action performed method now --> "Start Game"
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Finish
        if (arg0.toString().contains("Exit")) {
            System.exit(1);
        } else if (arg0.toString().contains("About")) {
            showMessageDialog(null,
                    "BlackJack: Created by Teju Nareddy\nStarted in March, 2013\n"
                            + "This is not the real version of BlackJack: See Info");
        } else if (arg0.toString().contains("Info")) {
            showMessageDialog(null,
                    "This is a custom game of BlackJack geared toward one human and multiple computer players..."
                            + "\nNo Dealer, so player is against everyone in the game"
                            + "\nComputers make logical decisions based on their cards"
                            + "\nSlight chance of showing your cards to others or seeing other's cards"
                            + "\nEveryone has money and the computers bet around the same amount of money as you do");
        } else if (arg0.toString().contains("Help")) {
            showMessageDialog(null,
                    "When filling out text and number fields, three different images will be shown:"
                            + "\nA red cross if the input is not in the correct format"
                            + "\nA orange cross if the input is in the correct format but is probably not right (typing in nothing into a text field)"
                            + "\nA green check if the input is correct");
        } else if (arg0.toString().contains("Reset Size")) {
            f.pack();
            f.toFront();
        } else if (arg0.toString().contains("Start Game")) {
            f.dispose();
            System.out.println("You will now begin a game of BlackJack!");
            int numOfDecks = Integer.parseInt(textField1.getText());
            stack = new Stack(numOfDecks);

            ArrayList<Player> plays = new ArrayList<Player>();
            int num = Integer.parseInt(textField2.getText());
            String name = textField3.getText();

            for (int i = 0; i < num - 1; i++) {
                plays.add(new Player("Computer " + (i + 1), true));
            }
            plays.add(new Player(name, false));
            for (Player p : plays) {
                p.setMoney(STARTING_MONEY);
            }

            players = plays;
            viewer = players.get(players.size() - 1);

            stack.shuffle(5);

            getPlayerBet(); // Go to this method now
        } else if (arg0.toString().contains("Make Bet")) {
            f.dispose();
            double bet = Double.parseDouble(textField1.getText());
            if (bet < 20 && 20 < viewer.getMoney()) {
                showMessageDialog(null,
                        "The min bet is $20, but you typed in $" + bet);
                getPlayerBet();
            } else if (bet > viewer.getMoney()) {
                showMessageDialog(null,
                        "The most money you have is $" + viewer.getMoney()
                                + ", but you typed in $" + bet
                                + " as you bet.");
                getPlayerBet();
            } else {
                viewer.setBet(bet);
                getComputerBets();

                distributeCards();
                displayCards();
                getChoice(); // TODO
            }
        }
    }

    // TODO CHECK for BUGS
    public Player checkForWinner() // Change isWinner
    {
        int[][] sum = new int[players.size()][2];
        for (int a = 0; a < players.size(); a++) {
            for (int b = 0; b < BlackjackTools
                    .getNumberOfHandsInPlayer(players.get(a)); b++) {
                sum[a][b] = BlackjackTools.getSumOfCardsInHand(
                        players.get(a).getHandList().get(b));
            }
        }

        int bestSum = 0; // Greatest that is less than or equal to 21
        Player bestPlayer = players.get(0);

        for (int a = 0; a < players.size(); a++) {
            for (int b = 0; b < BlackjackTools
                    .getNumberOfHandsInPlayer(players.get(a)); b++) {
                int temp = BlackjackTools.getSumOfCardsInHand(
                        players.get(a).getHandList().get(b));
                if (temp > bestSum) {
                    bestSum = temp;
                    bestPlayer = players.get(a);
                }
            }
        }

        return bestPlayer;
    }

    public void displayCards() // Works?
    {
        playerCards = new JFrame("Cards you know about");
        playerCards.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();

        for (int j = 0; j < viewer.getHandList().size(); j++) {
            for (int i = 0; i < viewer.getHandList().get(j).getCards()
                    .size(); i++) {
                ImageIcon image = new ImageIcon(viewer.getHandList().get(j)
                        .getCards().get(i).getImage());
                JLabel l = new JLabel(image);
                l.setToolTipText("Card #" + (i + 1) + " in hand #" + (j + 1)
                        + " --> " + viewer.getName());
                panels.add(new JPanel());
                if (viewer.getDropped() != null && viewer.getDropped().equals(
                        viewer.getHandList().get(j).getCards().get(i))) {
                    // If dropped, adds notification and image to the panel
                    // System.out.println("A");
                    panels.get(panels.size() - 1).setLayout(new BorderLayout());
                    panels.get(panels.size() - 1)
                            .add(new JLabel(
                                    "You accidentally dropped the card above. Other players know about it..."),
                            BorderLayout.SOUTH);
                    panels.get(panels.size() - 1).add(l, BorderLayout.CENTER);
                } else {
                    // If not dropped, just adds card image to the panel
                    panels.get(panels.size() - 1).add(l);
                }

            }
        }

        playerCards.setLayout(new GridLayout(userScreen.height / 250,
                userScreen.width / 200));
        for (int i = 0; i < panels.size(); i++) {
            playerCards.add(panels.get(i));
        }
        playerCards.pack();
        playerCards.setVisible(true);

        panels = new ArrayList<JPanel>();
        otherCards = new JFrame(
                "Cards accidentally dropped by the computers...");
        otherCards.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (Player p : players) {
            if (p.getDropped() != null && !p.equals(viewer)) {
                ImageIcon image = new ImageIcon(p.getDropped().getImage());
                JLabel l = new JLabel(image);
                l.setToolTipText("Card dropped by " + p.getName());
                panels.add(new JPanel());
                panels.get(panels.size() - 1).add(l);
            }
        }

        otherCards.setLayout(new GridLayout(userScreen.height / 250,
                userScreen.width / 200));
        for (int i = 0; i < panels.size(); i++) {
            otherCards.add(panels.get(i));
        }
        if (panels.size() == 0) {
            otherCards.add(
                    new JLabel("No players have dropped any cards yet..."));
        }
        otherCards.pack();
        otherCards.setVisible(true);
    }

    public void distributeCards() {

        for (Player p : players) {
            p.getHandList().add(new Hand());
            p.getHandList().get(0).getCards().add(stack.getCard());
            p.getHandList().get(0).getCards().add(stack.getCard());
            p.setDropped();
        }

    }

    // TODO
    public void getChoice() {
        f = new JFrame("Make your choice...");
        f.add(new JLabel("TO DO"));
        f.pack();
        f.setVisible(true);
    }

    public void getComputerBets() {
        double bet;
        for (Player p : players) {
            bet = viewer.getBet();
            if (p.equals(viewer)) {
                // do nothing
            } else {
                // AI LOGIC for betting
                int r = rand.nextInt(rand.nextInt((int) p.getMoney())) / 5;
                boolean positive = rand.nextBoolean();
                if (positive) {
                    bet += r;
                } else {
                    bet -= r;
                }

                positive = rand.nextBoolean();
                double ra = rand.nextInt(100) / 100.0;
                if (positive) {
                    bet += ra;
                } else {
                    bet -= ra;
                }

                if (bet < 20) {
                    bet = 20;
                }
                if (bet > p.getMoney()) {
                    bet = p.getMoney();
                }

                p.setBet(bet);
            }
        }
    }

    public void getPlayerBet() {
        f = new JFrame("Make Your Bet " + viewer.getName());
        f.setLayout(new BorderLayout());
        f.setAlwaysOnTop(false);
        f.add(new JLabel(
                "Change the settings for the game here. Press bottom button when finished..."),
                BorderLayout.NORTH);

        getText = new JButton("Make Bet");
        getText.addActionListener(this);
        getText.setToolTipText(
                "Press this to make your bet. Bet should be in a valid $ format.");

        f.add(new JLabel("The minumium bet is $20, the max bet is $"
                + viewer.getMoney() + " for you."), BorderLayout.NORTH);
        f.add(new JLabel("Your bet (in dollars) "), BorderLayout.WEST);

        NumberFormat format = NumberFormat.getNumberInstance();
        format.setRoundingMode(RoundingMode.HALF_UP);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        textField1 = new JFormattedTextField(format);
        textField1.setName("Bet");
        f.add(new JLayer<JFormattedTextField>(textField1, layerUI),
                BorderLayout.CENTER);

        f.add(getText, BorderLayout.EAST);
        f.add(new JScrollPane(outputConsole), BorderLayout.SOUTH);

        f.pack();
        f.setVisible(true);

        // go to action performed method now --> "Get Other Bets, Start round of
        // cards"
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        if (arg0.getKeyChar() == 'p' || arg0.getKeyChar() == 'P') {
            System.out.println("Toggle-Pause");
            if (paused) {
                paused = false;
            } else {
                paused = true;
            }
        }
    }
}
