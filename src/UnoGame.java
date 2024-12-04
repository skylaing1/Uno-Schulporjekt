import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UnoGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BorderLayout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1000);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        Game game = new Game();
        game.newGame();

        ArrayList<Player> players = game.getPlayers();
        ArrayList<Cards> tableCards = game.getTableCards();
        Player you = players.get(0);


        // Kartenrückseite für andere Spieler
        ImageIcon cardBackImageIcon = new ImageIcon("src/unocards/uno_back.png");
        Image cardBackImage = cardBackImageIcon.getImage();
        Image scaledCardBackImage = cardBackImage.getScaledInstance(60, 100, Image.SCALE_SMOOTH);
        Icon scaledCardBackImageIcon = new ImageIcon(scaledCardBackImage);

        // Kartenrückseite Ziehstapel

        Image scaledDeckCardBackImage = cardBackImage.getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        Icon scaledDeckCardBackImageIcon = new ImageIcon(scaledDeckCardBackImage);

        // Ablagestapel karte
        Cards topCard = tableCards.get(0);
        ImageIcon topCardImageIcon = new ImageIcon(topCard.getCardImage());
        Image topCardImage = topCardImageIcon.getImage();
        Image scaledTopCardImage = topCardImage.getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        Icon scaledTopCardImageIcon = new ImageIcon(scaledTopCardImage);


        // Norden
        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.RED);
        northPanel.setPreferredSize(new Dimension(0, 250));
        frame.add(northPanel, BorderLayout.NORTH);

        JPanel northCardPanel = new JPanel();
        northCardPanel.setBackground(Color.YELLOW);
        northCardPanel.setPreferredSize(new Dimension(500, 240));
        northPanel.add(northCardPanel);

        // Westen
        JPanel westPanel = new JPanel();
        westPanel.setBackground(Color.GREEN);
        westPanel.setPreferredSize(new Dimension(250, 0));
        frame.add(westPanel, BorderLayout.WEST);

        JPanel westCardPanel = new JPanel();
        westCardPanel.setBackground(Color.YELLOW);
        westCardPanel.setPreferredSize(new Dimension(240, 500));
        westPanel.add(westCardPanel);

        // Osten
        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.ORANGE);
        eastPanel.setPreferredSize(new Dimension(250, 0));
        frame.add(eastPanel, BorderLayout.EAST);

        JPanel eastCardPanel = new JPanel();
        eastCardPanel.setBackground(Color.YELLOW);
        eastCardPanel.setPreferredSize(new Dimension(240, 500));
        eastPanel.add(eastCardPanel);

        // Süden
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setPreferredSize(new Dimension(0, 250));
        frame.add(southPanel, BorderLayout.SOUTH);

        JPanel southCardPanel = new JPanel();
        southCardPanel.setLayout(null);
        southCardPanel.setBackground(Color.YELLOW);
        southCardPanel.setPreferredSize(new Dimension(800, 240));
        southPanel.add(southCardPanel);

        ArrayList<Cards> playerCards = you.getPlayerCards();
        // Parameter für die Karten
        int totalCards = playerCards.size(); // Anzahl der Karten
        int cardWidth = 90;
        int cardHeight = 150;

        // Verfügbarer Platz für die Karten
        int panelWidth = southCardPanel.getPreferredSize().width;
        int overlap = Math.max((panelWidth - cardWidth) / (totalCards - 1), 10); // Dynamisches Overlap

        // Referenz für die hervorgehobene Karte
        JButton[] cards = new JButton[totalCards];

        // Positionierung der Karten
        for (int i = 0; i < totalCards; i++) {
            Cards currentcard = playerCards.get(i);
            ImageIcon cardImageIcon = new ImageIcon(currentcard.getCardImage());
            Image cardImage = cardImageIcon.getImage();
            Image scaledCardImage = cardImage.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
            Icon scaledCardImageIcon = new ImageIcon(scaledCardImage);
            JButton card = new JButton(scaledCardImageIcon);
            int x = Math.min(i * overlap, panelWidth - cardWidth); // Begrenzung für letzte Karte
            card.setBounds(x, 50, cardWidth, cardHeight);


            // MouseListener für Hover-Effekt
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Alle anderen Karten bleiben in ihrer ursprünglichen Position
                    for (JButton otherCard : cards) {
                        otherCard.setBounds(otherCard.getX(), otherCard.getY(), cardWidth, cardHeight); // Zurücksetzen auf Standardgröße// Zurücksetzen der Z-Order
                    }

                    // Die aktuelle Karte wird größer und bleibt sichtbar
                    card.setBounds(card.getX() - 20, card.getY() - 20, cardWidth + 40, cardHeight + 40); // Vergrößern der Karte
                    if (southCardPanel.getComponentZOrder(card) != 0) {
                        southCardPanel.setComponentZOrder(card, southCardPanel.getComponentZOrder(card) - 1); // Bringt die Karte in den Vordergrund
                    }
                    southCardPanel.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {

                    // Wenn die Maus die Karte verlässt, wird die Karte wieder kleiner
                    card.setBounds(card.getX() + 20, card.getY() + 20, cardWidth, cardHeight); // Zurücksetzen der Größe
                    southCardPanel.setComponentZOrder(card, southCardPanel.getComponentZOrder(card) + 1); // Zurücksetzen der Z-Order
                    southCardPanel.repaint();
                }
            });

            southCardPanel.add(card);
            cards[i] = card; // Karte in das Array speichern
        }


        for (int i = 0; i < 7; i++) {
            JButton card = new JButton(scaledCardBackImageIcon);
            card.setPreferredSize(new Dimension(60, 100));
            northCardPanel.add(card);
        }

        for (int i = 0; i < 7; i++) {
            JButton card = new JButton(scaledCardBackImageIcon);
            card.setPreferredSize(new Dimension(60, 100));
            westCardPanel.add(card);
        }

        for (int i = 0; i < 7; i++) {
            JButton card = new JButton(scaledCardBackImageIcon);
            card.setPreferredSize(new Dimension(60, 100));
            eastCardPanel.add(card);
        }
// Zentrum
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.GRAY);
        centerPanel.setLayout(new GridBagLayout());
        frame.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 50, 0, 50); // Increase space between the piles
        gbc.anchor = GridBagConstraints.CENTER; // Center the components

// Draw pile
        JLabel drawPile = new JLabel();
        drawPile.setPreferredSize(new Dimension(120, 200));
        drawPile.setIcon(scaledDeckCardBackImageIcon);
        gbc.gridx = 0;
        centerPanel.add(drawPile, gbc);

// Discard pile
        JLabel discardPile = new JLabel(scaledTopCardImageIcon);
        discardPile.setPreferredSize(new Dimension(120, 200));
        discardPile.setBackground(Color.WHITE);
        gbc.gridx = 1;
        centerPanel.add(discardPile, gbc);



        frame.setVisible(true);
    }
}
