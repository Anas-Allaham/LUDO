package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LudoGUI {
    private JFrame frame;
    private JPanel boardPanel;
    private JLabel diceResult;
    private JButton rollDiceButton;
    private JLabel currentPlayerLabel;
    private Game game;
    private JButton[][] cells;
    private int currentPlayer;
    private int diceValue;

    public LudoGUI(Game game) {
        this.game = game;
        this.currentPlayer = 0; // Start with Player 1
        this.diceValue = 0;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Ludo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(15, 15));
        boardPanel.setPreferredSize(new Dimension(600, 600));

        cells = new JButton[15][15];

        // Create board cells
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                cells[i][j] = new JButton();
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[i][j].setOpaque(true);
                cells[i][j].setEnabled(false);

                if (isSafeZone(i, j)) {
                    cells[i][j].setBackground(Color.WHITE);
                } else if (isBaseZone(i, j, "red")) {
                    cells[i][j].setBackground(Color.RED);
                } else if (isBaseZone(i, j, "yellow")) {
                    cells[i][j].setBackground(Color.YELLOW);
                } else if (isBaseZone(i, j, "green")) {
                    cells[i][j].setBackground(Color.GREEN);
                } else if (isBaseZone(i, j, "blue")) {
                    cells[i][j].setBackground(Color.BLUE);
                } else {
                    cells[i][j].setBackground(Color.LIGHT_GRAY);
                }

                boardPanel.add(cells[i][j]);
            }
        }

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));

        currentPlayerLabel = new JLabel("Current Player: Player 1", SwingConstants.CENTER);
        diceResult = new JLabel("Dice: ", SwingConstants.CENTER);
        rollDiceButton = new JButton("Roll Dice");

        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diceValue = rollDice();
                diceResult.setText("Dice: " + diceValue);
                enablePlayerPawns();
            }
        });

        controlPanel.add(currentPlayerLabel);
        controlPanel.add(diceResult);
        controlPanel.add(rollDiceButton);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Place initial stones on the board
        updateStones();

        frame.setVisible(true);
    }

    private void updateStones() {
        for (int playerIdx = 0; playerIdx < game.players.length; playerIdx++) {
            Player player = game.players[playerIdx];
            for (Stone stone : player.stones) {
                if (stone.is_playing) {
                    int[] position = getPosition(stone.pos);
                    if (position != null) {
                        JButton cell = cells[position[0]][position[1]];
                        cell.setBackground(getPlayerColor(playerIdx));
                        cell.setText("P" + (playerIdx + 1));
                        cell.setEnabled(true);
                        cell.addActionListener(new PawnActionListener(stone));
                    }
                }
            }
        }
    }

    private void enablePlayerPawns() {
        Player current = game.players[currentPlayer];
        for (Stone stone : current.stones) {
            if (stone.is_playing) {
                int[] position = getPosition(stone.pos);
                if (position != null) {
                    JButton cell = cells[position[0]][position[1]];
                    cell.setEnabled(true);
                }
            }
        }
    }

    private boolean isSafeZone(int i, int j) {
        // Define safe zone cells
        return (i == 6 && j >= 1 && j <= 5) || (j == 6 && i >= 1 && i <= 5) ||
                (i == 8 && j >= 9 && j <= 13) || (j == 8 && i >= 9 && i <= 13);
    }

    private boolean isBaseZone(int i, int j, String color) {
        // Define base zones for each color
        switch (color) {
            case "red":
                return (i >= 0 && i <= 5 && j >= 0 && j <= 5);
            case "yellow":
                return (i >= 0 && i <= 5 && j >= 9 && j <= 14);
            case "green":
                return (i >= 9 && i <= 14 && j >= 9 && j <= 14);
            case "blue":
                return (i >= 9 && i <= 14 && j >= 0 && j <= 5);
            default:
                return false;
        }
    }

    private int rollDice() {
        return Statics.throw_dice().get(0);
    }

    private int[] getPosition(int num) {
        // Translate board node numbers to 15x15 grid positions
        // This method needs to be implemented according to your board node mapping
        return new int[] {0, 0}; // Example placeholder
    }

    private Color getPlayerColor(int playerIdx) {
        switch (playerIdx) {
            case 0: return Color.RED;
            case 1: return Color.YELLOW;
            case 2: return Color.GREEN;
            case 3: return Color.BLUE;
            default: return Color.BLACK;
        }
    }

    private class PawnActionListener implements ActionListener {
        private Stone stone;

        public PawnActionListener(Stone stone) {
            this.stone = stone;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Move the pawn based on the dice value
            boolean moved = game.update(stone.Idx_Player, diceValue);
            if (moved) {
                updateStones();
                diceResult.setText("Dice: ");
                rollDiceButton.setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        SwingUtilities.invokeLater(() -> new LudoGUI(game));
    }
}
