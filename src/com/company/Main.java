package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        LudoBoardConsole consoleBoard = new LudoBoardConsole(game);
        LudoGameGUI guiBoard = new LudoGameGUI(game);

        // Initialize boards
        consoleBoard.init();
        guiBoard.init();

        Statics.gen(0, new ArrayList<>(), 1);
        Scanner in = new Scanner(System.in);

        int totalPlayers, humanPlayers;
        do {
            System.out.println("How many players do you want to play?");
            totalPlayers = in.nextInt();
            System.out.println("How many humans do you want in the game?");
            humanPlayers = in.nextInt();
        } while (totalPlayers - humanPlayers < 0);

        game.Tot = totalPlayers;
        int turnCounter = 0;
        boolean gameOver = false;

        int cur=0;

        while (!gameOver) {
            System.out.println("It's " + game.players[cur].name + "'s turn.");

            List<Integer> diceRoll = Statics.throw_dice();

            // Count active stones before the turn
            int activeStonesBefore = 0;
            for (int i = 0; i < totalPlayers; i++) {
                if (i != turnCounter) {
                    for (int j = 0; j < 4; j++) {
                        if (game.players[i].stones[j].is_playing) {
                            activeStonesBefore++;
                        }
                    }
                }
            }

            // Handle the current player's turn
            if (cur < humanPlayers) {
                // Human player turn
                boolean check=false;
                int cntWin=0;
                for(int i=0;i<4;i++){
                    cntWin+=game.players[game.curPlayer].stones[i].is_win?1:0;
                    check|=game.checker(i,diceRoll.get(0));
                }
                check|=diceRoll.size()>1 && cntWin<4;
                if(!check){
                    System.out.println("You haven't any move :(");
                    TimeUnit.SECONDS.sleep(1);
                    cur++;
                    cur%=totalPlayers;
                    turnCounter += 4 / totalPlayers;
                    turnCounter %= totalPlayers==3? 3 : 4;
                    game.curPlayer = turnCounter;
                    continue;
                }

                while (!diceRoll.isEmpty()) {
                    System.out.println("Dice rolled: " + diceRoll);
                    int ListIdx=0,stoneIdx;
                    boolean w=false;
                    do {
                        System.out.println("Enter the index of the roll you want to play");
                        ListIdx = in.nextInt();
                        w=ListIdx>=diceRoll.size();
                        if(w){
                            System.out.println("The idx you insert is invalid");
                        }
                    }while(w);
                    int diceValue = diceRoll.remove(ListIdx);
                    do {
                        System.out.println("Enter stone index to move (0-3): ");
                        stoneIdx = in.nextInt();
                        w=stoneIdx>=4 || stoneIdx<0;
                        if(w){
                            System.out.println("The idx you insert is invalid");
                        }
                    }while(w);

                    if (game.update(stoneIdx, diceValue)) {
                        System.out.println("Player " + game.players[game.curPlayer].name + " moved stone " + stoneIdx + " with a roll of " + diceValue);
                        guiBoard.drawBoard();

                    } else {
                        System.out.println("Invalid move. Try again.");
                        diceRoll.add(ListIdx,diceValue);
                    }
                }
            } else {
                // AI player turn
                System.out.println("* AI is making a move...");
                game = new Game(game.AI(1, diceRoll, game.curPlayer));
            }

            // Count active stones after the turn
            int activeStonesAfter = 0;
            for (int i = 0; i < totalPlayers; i++) {
                if (i != turnCounter) {
                    for (int j = 0; j < 4; j++) {
                        if (game.players[i].stones[j].is_playing) {
                            activeStonesAfter++;
                        }
                    }
                }
            }

            // Update boards
            consoleBoard = new LudoBoardConsole(game);
            guiBoard.dispose();
            guiBoard = new LudoGameGUI(game);

            consoleBoard.drawBoard();
            guiBoard.drawBoard();

            // Check for game over
            gameOver = game.IsGameOver();
            if (gameOver) {
                System.out.println("Player " + game.players[game.curPlayer].name + " wins!");
                break;
            }

            // Update turn logic
            if (activeStonesBefore - activeStonesAfter == 0) {

                cur++;
                cur%=totalPlayers;
                turnCounter += 4 / totalPlayers;
                turnCounter %= totalPlayers==3? 3 : 4;
                game.curPlayer = turnCounter;
            }

            TimeUnit.SECONDS.sleep(1);
        }
    }
}
