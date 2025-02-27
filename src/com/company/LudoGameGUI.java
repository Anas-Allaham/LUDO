package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LudoGameGUI extends JFrame {
    private static final int BOARD_SIZE = 15;
    private final JPanel boardPanel = new JPanel();
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JLabel diceLabel = new JLabel("Dice: 1");
    private final JLabel currentPlayerLabel = new JLabel("Current Player: Red");
    private final String[] players = {"Red", "Blue", "Yellow", "Green"};
    private int currentPlayerIndex = 0;
    Level level;

    LudoGameGUI(){}
    public LudoGameGUI(Level level) {
        setTitle("Ludo Level");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(710, 800);
        setLayout(new BorderLayout());
        setLocation(670,0);
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        setVisible(true);

        this.level = level;

    }
    void init(){
        // Set up the main frame
        setTitle("Ludo Level");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(710, 800);
        setLayout(new BorderLayout());
        setLocation(670,0);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
//                System.out.println(i+" "+j);
                level.cells[i][j]=new JLabel("");
                level.cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                level.cells[i][j].setOpaque(true);
                level.cells[i][j].setBackground(new Color(182, 181, 181)); // Default color
                level.cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
//                level.cells[i][j].setBackground(new Color(225, 161, 245));
            }
        }
        markBaseZones();
        markSafeZones();
        for(int i=0;i<15;i++)for(int j=0;j<15;j++)                boardPanel.add(level.cells[i][j]);

        // Set up the board panel
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        add(boardPanel, BorderLayout.CENTER);

        // Set up the control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(currentPlayerLabel);
        controlPanel.add(diceLabel);
        controlPanel.add(rollDiceButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Add action listener to the dice button
        rollDiceButton.addActionListener(new RollDiceListener());


        setVisible(true);
    }
    private void markBaseZones() {
        // Mark the center cells of each corner with player identifiers

        // BLUE Base
        level.cells[2][2].setBackground(new Color(129, 189, 229));
        level.cells[2][3].setBackground(new Color(129, 189, 229));
        level.cells[3][2].setBackground(new Color(129, 189, 229));
        level.cells[3][3].setBackground(new Color(129, 189, 229));
        level.cells[2][2].setText("A2");
        level.cells[2][3].setText("B2");
        level.cells[3][2].setText("C2");
        level.cells[3][3].setText("D2");

        // RED Base
        level.cells[2][11].setBackground(new Color(225, 161, 245));
        level.cells[2][12].setBackground(new Color(225, 161, 245));
        level.cells[3][11].setBackground(new Color(225, 161, 245));
        level.cells[3][12].setBackground(new Color(225, 161, 245));
        level.cells[2][11].setText("A3");
        level.cells[2][12].setText("B3");
        level.cells[3][11].setText("C3");
        level.cells[3][12].setText("D3");

        // YELLOW Base
        level.cells[11][2].setBackground(new Color (231, 240, 144));
        level.cells[11][3].setBackground(new Color (231, 240, 144));
        level.cells[12][2].setBackground(new Color (231, 240, 144));
        level.cells[12][3].setBackground(new Color (231, 240, 144));
        level.cells[11][2].setText("A1");
        level.cells[11][3].setText("B1");
        level.cells[12][2].setText("C1");
        level.cells[12][3].setText("D1");

        // GREEN Base
        level.cells[11][11].setBackground(new Color (183, 245, 161));
        level.cells[11][12].setBackground(new Color (183, 245, 161));
        level.cells[12][11].setBackground(new Color (183, 245, 161));
        level.cells[12][12].setBackground(new Color (183, 245, 161));
        level.cells[11][11].setText("A4");
        level.cells[11][12].setText("B4");
        level.cells[12][11].setText("C4");
        level.cells[12][12].setText("D4");

        // Make sure labels are opaque to see the background color
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                level.cells[i][j].setOpaque(true);
            }
        }
    }

    public void drawBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
//                System.out.println(i+" "+j);
//                level.cells[i][j].setText("");
                level.cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                level.cells[i][j].setOpaque(true);
//                level.cells[i][j].setBackground(new Color(182, 181, 181)); // Default color
                level.cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
//                level.cells[i][j].setBackground(new Color(225, 161, 245));
                boardPanel.add(level.cells[i][j]);

            }
        }
//        System.out.println(level.grid.length);
        setTitle("Ludo Level");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(710, 800);
        setLayout(new BorderLayout());
        setLocation(670,0);
        level.cells[level.board.Adj[1].x][level.board.Adj[1].y].setBackground(new Color (231, 240, 144));
        level.cells[level.board.Adj[1].x][level.board.Adj[1].y].setText(level.board.Adj[1].string);
        level.cells[level.board.Adj[14].x][level.board.Adj[14].y].setBackground(new Color(129, 189, 229));
        level.cells[level.board.Adj[14].x][level.board.Adj[14].y].setText(level.board.Adj[14].string);
        level.cells[level.board.Adj[27].x][level.board.Adj[27].y].setBackground(new Color(225, 161, 245));
        level.cells[level.board.Adj[27].x][level.board.Adj[27].y].setText(level.board.Adj[27].string);
        level.cells[level.board.Adj[40].x][level.board.Adj[40].y].setBackground(new Color (183, 245, 161));
        level.cells[level.board.Adj[40].x][level.board.Adj[40].y].setText(level.board.Adj[40].string);
//        markBaseZones();

        for (int i = 1; i <=5; i++) {
            level.cells[i][7].setBackground(new Color(225, 161, 245));
            level.cells[i][7].setText(level.board.Adj[58+i-1].string);
        }

        for (int i = 1; i <=5; i++) {
            level.cells[7][i].setBackground(new Color(129, 189, 229));
            level.cells[7][i].setText(level.board.Adj[53+i-1].string);
        }

        for (int i = 9; i <=13; i++) {
            level.cells[i][7].setBackground(new Color (231, 240, 144));
            level.cells[i][7].setText(level.board.Adj[68-i+13].string);
        }

        for (int i = 9; i <=13; i++) {
            level.cells[7][i].setBackground(new Color (183, 245, 161));
            level.cells[7][i].setText(level.board.Adj[63-i+13].string);
        }
        level.cells[0][0].setBackground(new Color(182, 181, 181));
        level.cells[0][0].setText(".   ");

        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                boardPanel.add(level.cells[i][j]);

        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        add(boardPanel, BorderLayout.CENTER);

        // Set up the control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(currentPlayerLabel);
        controlPanel.add(diceLabel);
        controlPanel.add(rollDiceButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Add action listener to the dice button
        rollDiceButton.addActionListener(new RollDiceListener());


        setVisible(true);
    }


    private void initializeBoard() {
        // Initialize each cell in the grid
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                JLabel cell = new JLabel();
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setBackground(new Color(182, 181, 181)); // Default color
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Set safe zones and bases
                if (isSafeZone(i, j)) {
                    cell.setBackground(new Color(248, 236, 211)); // Safe zones
                } else if (isBaseZone(i, j, "Red")) {
                    cell.setBackground(new Color(225, 161, 245)); // Red base
                } else if (isBaseZone(i, j, "Blue")) {
                    cell.setBackground(new Color(129, 189, 229)); // Blue base
                } else if (isBaseZone(i, j, "Yellow")) {
                    cell.setBackground(new Color (231, 240, 144)); // Yellow base
                } else if (isBaseZone(i, j, "Green")) {
                    cell.setBackground(new Color (183, 245, 161)); // Green base
                }

                level.cells[i][j] = cell;
                boardPanel.add(cell);
            }
        }

    }

    private void markSafeZones() {
        // Mark safe zones with magenta 'S'
        for (int i = 6; i <= 8; i++) {
            for (int j = 0; j <= 5; j++) {
                level.cells[i][j].setBackground(new Color(248, 236, 211));
                level.cells[i][j].setText("#");
            }
            for (int j = 9; j <= 14; j++) {
                level.cells[i][j].setBackground(new Color(248, 236, 211));
                level.cells[i][j].setText("#");
            }
        }
        for (int j = 6; j <= 8; j++) {
            for (int i = 0; i <= 5; i++) {
                level.cells[i][j].setBackground(new Color(248, 236, 211));
                level.cells[i][j].setText("#");
            }
            for (int i = 9; i <= 14; i++) {
                level.cells[i][j].setBackground(new Color(248, 236, 211));
                level.cells[i][j].setText("#");
            }
        }

        // Make sure labels are opaque to see the background color
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                level.cells[i][j].setOpaque(true);
            }
        }
        for (int i = 1; i <= 5; i++) {
            level.cells[i][7].setBackground(new Color(225, 161, 245));
            level.cells[i][7].setText("#");
        }
        level.cells[1][8].setBackground(new Color(225, 161, 245));
        level.cells[1][8].setText("#");

        // Mark the second set of blue safe zones
        for (int i = 1; i <= 5; i++) {
            level.cells[7][i].setBackground(new Color(129, 189, 229));
            level.cells[7][i].setText("#");
        }
        level.cells[6][1].setBackground(new Color(129, 189, 229));
        level.cells[6][1].setText("#");

        // Mark the third set of yellow safe zones
        for (int i = 9; i <= 13; i++) {
            level.cells[i][7].setBackground(new Color (231, 240, 144));
            level.cells[i][7].setText("#");
        }
        level.cells[13][6].setBackground(new Color (231, 240, 144));
        level.cells[13][6].setText("#");

        // Mark the fourth set of green safe zones
        for (int i = 9; i <= 13; i++) {
            level.cells[7][i].setBackground(new Color (183, 245, 161));
            level.cells[7][i].setText("#");
        }
        level.cells[8][13].setBackground(new Color (183, 245, 161));
        level.cells[8][13].setText("#");

        // Mark the new set of safe zones (NEW color)
        for (int i = 6; i <= 8; i++) {
            for (int j = 6; j <= 8; j++) {
                level.cells[i][j].setBackground(new Color (250, 130, 141));  // NEW safe zone color
                level.cells[i][j].setText("#");
            }
        }

        // Make sure labels are opaque to see the background color
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                level.cells[i][j].setOpaque(true);
            }
        }
    }
    private boolean isSafeZone(int row, int col) {
        // Safe zones logic
        return (row == 6 || row == 8) && (col >= 0 && col < 6 || col >= 9 && col < BOARD_SIZE) ||
                (col == 6 || col == 8) && (row >= 0 && row < 6 || row >= 9 && row < BOARD_SIZE);
    }

    private boolean isBaseZone(int row, int col, String player) {
        // Base zones for each player
        switch (player) {
            case "Red":
                return (row == 2 || row == 3) && (col == 11 || col == 12);
            case "Blue":
                return (row == 2 || row == 3) && (col == 2 || col == 3);
            case "Yellow":
                return (row == 11 || row == 12) && (col == 2 || col == 3);
            case "Green":
                return (row == 11 || row == 12) && (col == 11 || col == 12);
            default:
                return false;
        }
    }

    private class RollDiceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Simulate dice roll
            int diceValue = new Random().nextInt(6) + 1;
            diceLabel.setText("Dice: " + diceValue);

            // Update the current player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
            currentPlayerLabel.setText("Current Player: " + players[currentPlayerIndex]);
        }
    }

}
