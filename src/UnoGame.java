import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

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


        // Zentrum
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.GRAY);
        centerPanel.setLayout(new GridBagLayout());
        frame.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 50, 0, 50); // Increase space between the piles
        gbc.anchor = GridBagConstraints.CENTER; // Center the components

// Draw pile
        JButton drawPile = new JButton();
        drawPile.setPreferredSize(new Dimension(120, 200));
        drawPile.setIcon(scaledDeckCardBackImageIcon);
        drawPile.setEnabled(false);
        gbc.gridx = 0;
        centerPanel.add(drawPile, gbc);


// Discard pile
        JLabel discardPile = new JLabel();
        discardPile.setPreferredSize(new Dimension(120, 200));
        gbc.gridx = 1;
        centerPanel.add(discardPile, gbc);



        FourColorCircle colorCircle = new FourColorCircle();
        centerPanel.add(colorCircle, gbc);
        colorCircle.setVisible(false);


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

        Cards topCard = tableCards.get(0);

        updateTopCardImage(discardPile, topCard);
        updatePlayerCards(you, southCardPanel, topCard, game, discardPile, drawPile, colorCircle, centerPanel);

        for (int i = 0; i < 7; i++) {
            JLabel card = new JLabel(scaledCardBackImageIcon);
            card.setPreferredSize(new Dimension(60, 100));
            northCardPanel.add(card);
        }

        for (int i = 0; i < 7; i++) {
            JLabel card = new JLabel(scaledCardBackImageIcon);
            card.setPreferredSize(new Dimension(60, 100));
            westCardPanel.add(card);
        }

        for (int i = 0; i < 7; i++) {
            JLabel card = new JLabel(scaledCardBackImageIcon);
            card.setPreferredSize(new Dimension(60, 100));
            eastCardPanel.add(card);
        }

        drawPile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (game.drawCard(you)) {
                    updatePlayerCards(you, southCardPanel, tableCards.get(0), game, discardPile, drawPile, colorCircle, centerPanel);
                }
            }
        });


        frame.setVisible(true);
    }


    private static void updateTopCardImage(JLabel discardPile, Cards topCard) {
        ImageIcon topCardImageIcon = new ImageIcon(topCard.getCardImage());
        Image topCardImage = topCardImageIcon.getImage();
        Image scaledTopCardImage = topCardImage.getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        Icon scaledTopCardImageIcon = new ImageIcon(scaledTopCardImage);
        discardPile.setIcon(scaledTopCardImageIcon);
    }

    private void updateOtherPlayersDeck(JPanel panel, ArrayList<Player> players, int playerIndex, Icon scaledCardBackImageIcon) {
        panel.removeAll();
        for (Player player : players) {
            if (player != players.get(playerIndex)) {
                for (int j = player.getCardCount(); j > 0; j--) {
                    JLabel card = new JLabel(scaledCardBackImageIcon);
                    card.setPreferredSize(new Dimension(60, 100));
                    panel.add(card);

                }
            }
        }
        panel.repaint();
        panel.revalidate();
    }

    private static void updatePlayerCards(Player player, JPanel southCardPanel, Cards topCard, Game game, JLabel discardPile, JButton drawPile, FourColorCircle colorCircle, JPanel centerPanel) {
        southCardPanel.removeAll();
        ArrayList<Cards> playerCards = player.getPlayerCards();
        int totalCards = playerCards.size();
        int cardWidth = 90;
        int cardHeight = 150;
        int panelWidth = southCardPanel.getPreferredSize().width;
        int overlap = Math.max((panelWidth - cardWidth) / (totalCards - 1), 10);
        JButton[] cards = new JButton[totalCards];
        boolean disabledDrawPile = true;

        for (int i = 0; i < totalCards; i++) {
            Cards currentcard = playerCards.get(i);
            ImageIcon cardImageIcon = new ImageIcon(currentcard.getCardImage());
            Image cardImage = cardImageIcon.getImage();
            Image scaledCardImage = cardImage.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
            Icon scaledCardImageIcon = new ImageIcon(scaledCardImage);
            JButton card = new JButton(scaledCardImageIcon);
            card.setDisabledIcon(scaledCardImageIcon);
            int x = Math.min(i * overlap, panelWidth - cardWidth);
            card.setBounds(x, 50, cardWidth, cardHeight);

            if (disabledDrawPile) {
                if (Game.checkCardPlayable(currentcard, topCard)) {
                    drawPile.setEnabled(false);
                    disabledDrawPile = false;
                } else {
                    drawPile.setEnabled(true);
                }
            }

            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    for (JButton otherCard : cards) {
                        otherCard.setBounds(otherCard.getX(), otherCard.getY(), cardWidth, cardHeight);
                    }
                    card.setBounds(card.getX() - 20, card.getY() - 20, cardWidth + 40, cardHeight + 40);
                    if (southCardPanel.getComponentZOrder(card) != 0) {
                        southCardPanel.setComponentZOrder(card, southCardPanel.getComponentZOrder(card) - 1);
                    }
                    southCardPanel.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    card.setBounds(card.getX() + 20, card.getY() + 20, cardWidth, cardHeight);
                    southCardPanel.setComponentZOrder(card, southCardPanel.getComponentZOrder(card) + 1);
                    southCardPanel.repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    int i = game.playCard(player, currentcard);
                    if (i > 0) {
                        if (i == 2) {
                            JOptionPane.showMessageDialog(null, "You won!");
                        }
                        if (i == 5) {
                            colorCircle.setVisible(true);
                            discardPile.setVisible(false);
                            drawPile.setEnabled(false);
                            centerPanel.repaint();
                            centerPanel.revalidate();

                            new SwingWorker<Void, Void>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    while (colorCircle.getSegmentClicked() == -1) {
                                        Thread.sleep(100);
                                    }
                                    return null;
                                }

                                @Override
                                protected void done() {
                                    //get Topcard
                                    Cards topCard = game.getTableCards().get(game.getTableCards().size() - 1);
                                    switch (colorCircle.getSegmentClicked()) {
                                        case 0:
                                            topCard.setWildColour("blue");
                                            System.out.println("Blue");
                                            break;
                                        case 1:
                                            topCard.setWildColour("yellow");
                                            System.out.println("Yellow");
                                            break;
                                        case 2:
                                            topCard.setWildColour("red");
                                            System.out.println("Red");
                                            break;
                                        case 3:
                                            topCard.setWildColour("green");
                                            System.out.println("Green");
                                            break;
                                    }
                                    colorCircle.setVisible(false);
                                    colorCircle.setSegmentClicked(-1);
                                    discardPile.setVisible(true);
                                    updateTopCardImage(discardPile, topCard);
                                    updatePlayerCards(player, southCardPanel, topCard, game, discardPile, drawPile, colorCircle, centerPanel);
                                }
                            }.execute();
                        }
                        updateTopCardImage(discardPile, currentcard);
                        updatePlayerCards(player, southCardPanel, topCard, game, discardPile, drawPile , colorCircle, centerPanel);
                    }
                }
            });
            southCardPanel.add(card);
            cards[i] = card;
        }
        southCardPanel.repaint();
        southCardPanel.revalidate();
    }
}