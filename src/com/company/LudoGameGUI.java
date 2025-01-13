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
    Game game;

    LudoGameGUI(){}
    public LudoGameGUI(Game game) {
        setTitle("Ludo Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(710, 800);
        setLayout(new BorderLayout());
        setLocation(670,0);
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        setVisible(true);

        this.game=game;

    }
    void init(){
        // Set up the main frame
        setTitle("Ludo Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(710, 800);
        setLayout(new BorderLayout());
        setLocation(670,0);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
//                System.out.println(i+" "+j);
                game.cells[i][j]=new JLabel("");
                game.cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                game.cells[i][j].setOpaque(true);
                game.cells[i][j].setBackground(Color.WHITE); // Default color
                game.cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
//                game.cells[i][j].setBackground(Color.red);
            }
        }
        markBaseZones();
        markSafeZones();
        for(int i=0;i<15;i++)for(int j=0;j<15;j++)                boardPanel.add(game.cells[i][j]);

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
        game.cells[2][2].setBackground(Color.BLUE);
        game.cells[2][3].setBackground(Color.BLUE);
        game.cells[3][2].setBackground(Color.BLUE);
        game.cells[3][3].setBackground(Color.BLUE);
        game.cells[2][2].setText("A2");
        game.cells[2][3].setText("B2");
        game.cells[3][2].setText("C2");
        game.cells[3][3].setText("D2");

        // RED Base
        game.cells[2][11].setBackground(Color.RED);
        game.cells[2][12].setBackground(Color.RED);
        game.cells[3][11].setBackground(Color.RED);
        game.cells[3][12].setBackground(Color.RED);
        game.cells[2][11].setText("A3");
        game.cells[2][12].setText("B3");
        game.cells[3][11].setText("C3");
        game.cells[3][12].setText("D3");

        // YELLOW Base
        game.cells[11][2].setBackground(Color.YELLOW);
        game.cells[11][3].setBackground(Color.YELLOW);
        game.cells[12][2].setBackground(Color.YELLOW);
        game.cells[12][3].setBackground(Color.YELLOW);
        game.cells[11][2].setText("A1");
        game.cells[11][3].setText("B1");
        game.cells[12][2].setText("C1");
        game.cells[12][3].setText("D1");

        // GREEN Base
        game.cells[11][11].setBackground(Color.GREEN);
        game.cells[11][12].setBackground(Color.GREEN);
        game.cells[12][11].setBackground(Color.GREEN);
        game.cells[12][12].setBackground(Color.GREEN);
        game.cells[11][11].setText("A4");
        game.cells[11][12].setText("B4");
        game.cells[12][11].setText("C4");
        game.cells[12][12].setText("D4");

        // Make sure labels are opaque to see the background color
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                game.cells[i][j].setOpaque(true);
            }
        }
    }

    public void drawBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
//                System.out.println(i+" "+j);
//                game.cells[i][j].setText("");
                game.cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                game.cells[i][j].setOpaque(true);
//                game.cells[i][j].setBackground(Color.WHITE); // Default color
                game.cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
//                game.cells[i][j].setBackground(Color.red);
                boardPanel.add(game.cells[i][j]);

            }
        }
//        System.out.println(game.boards.length);
        setTitle("Ludo Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(710, 800);
        setLayout(new BorderLayout());
        setLocation(670,0);
        game.cells[game.board.Adj[1].x][game.board.Adj[1].y].setBackground(Color.YELLOW);
        game.cells[game.board.Adj[1].x][game.board.Adj[1].y].setText(game.board.Adj[1].string);
        game.cells[game.board.Adj[14].x][game.board.Adj[14].y].setBackground(Color.blue);
        game.cells[game.board.Adj[14].x][game.board.Adj[14].y].setText(game.board.Adj[14].string);
        game.cells[game.board.Adj[27].x][game.board.Adj[27].y].setBackground(Color.red);
        game.cells[game.board.Adj[27].x][game.board.Adj[27].y].setText(game.board.Adj[27].string);
        game.cells[game.board.Adj[40].x][game.board.Adj[40].y].setBackground(Color.green);
        game.cells[game.board.Adj[40].x][game.board.Adj[40].y].setText(game.board.Adj[40].string);
//        markBaseZones();

        for (int i = 1; i <=5; i++) {
            game.cells[i][7].setBackground(Color.red);
            game.cells[i][7].setText(game.board.Adj[58+i-1].string);
        }

        for (int i = 1; i <=5; i++) {
            game.cells[7][i].setBackground(Color.blue);
            game.cells[7][i].setText(game.board.Adj[53+i-1].string);
        }

        for (int i = 9; i <=13; i++) {
            game.cells[i][7].setBackground(Color.yellow);
            game.cells[i][7].setText(game.board.Adj[68-i+13].string);
        }

        for (int i = 9; i <=13; i++) {
            game.cells[7][i].setBackground(Color.green);
            game.cells[7][i].setText(game.board.Adj[63-i+13].string);
        }
        game.cells[0][0].setBackground(Color.white);
        game.cells[0][0].setText(".   ");

        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                boardPanel.add(game.cells[i][j]);

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
                cell.setBackground(Color.WHITE); // Default color
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Set safe zones and bases
                if (isSafeZone(i, j)) {
                    cell.setBackground(Color.MAGENTA); // Safe zones
                } else if (isBaseZone(i, j, "Red")) {
                    cell.setBackground(Color.RED); // Red base
                } else if (isBaseZone(i, j, "Blue")) {
                    cell.setBackground(Color.BLUE); // Blue base
                } else if (isBaseZone(i, j, "Yellow")) {
                    cell.setBackground(Color.YELLOW); // Yellow base
                } else if (isBaseZone(i, j, "Green")) {
                    cell.setBackground(Color.GREEN); // Green base
                }

                game.cells[i][j] = cell;
                boardPanel.add(cell);
            }
        }

    }

    private void markSafeZones() {
        // Mark safe zones with magenta 'S'
        for (int i = 6; i <= 8; i++) {
            for (int j = 0; j <= 5; j++) {
                game.cells[i][j].setBackground(Color.MAGENTA);
                game.cells[i][j].setText("S");
            }
            for (int j = 9; j <= 14; j++) {
                game.cells[i][j].setBackground(Color.MAGENTA);
                game.cells[i][j].setText("S");
            }
        }
        for (int j = 6; j <= 8; j++) {
            for (int i = 0; i <= 5; i++) {
                game.cells[i][j].setBackground(Color.MAGENTA);
                game.cells[i][j].setText("S");
            }
            for (int i = 9; i <= 14; i++) {
                game.cells[i][j].setBackground(Color.MAGENTA);
                game.cells[i][j].setText("S");
            }
        }

        // Make sure labels are opaque to see the background color
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                game.cells[i][j].setOpaque(true);
            }
        }
        for (int i = 1; i <= 5; i++) {
            game.cells[i][7].setBackground(Color.RED);
            game.cells[i][7].setText("S");
        }
        game.cells[1][8].setBackground(Color.RED);
        game.cells[1][8].setText("S");

        // Mark the second set of blue safe zones
        for (int i = 1; i <= 5; i++) {
            game.cells[7][i].setBackground(Color.BLUE);
            game.cells[7][i].setText("S");
        }
        game.cells[6][1].setBackground(Color.BLUE);
        game.cells[6][1].setText("S");

        // Mark the third set of yellow safe zones
        for (int i = 9; i <= 13; i++) {
            game.cells[i][7].setBackground(Color.YELLOW);
            game.cells[i][7].setText("S");
        }
        game.cells[13][6].setBackground(Color.YELLOW);
        game.cells[13][6].setText("S");

        // Mark the fourth set of green safe zones
        for (int i = 9; i <= 13; i++) {
            game.cells[7][i].setBackground(Color.GREEN);
            game.cells[7][i].setText("S");
        }
        game.cells[8][13].setBackground(Color.GREEN);
        game.cells[8][13].setText("S");

        // Mark the new set of safe zones (NEW color)
        for (int i = 6; i <= 8; i++) {
            for (int j = 6; j <= 8; j++) {
                game.cells[i][j].setBackground(Color.CYAN);  // NEW safe zone color
                game.cells[i][j].setText("S");
            }
        }

        // Make sure labels are opaque to see the background color
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                game.cells[i][j].setOpaque(true);
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
