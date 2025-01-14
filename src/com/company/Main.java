package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Level level = new Level();
        LudoBoardConsole consoleBoard = new LudoBoardConsole(level);
        LudoGameGUI guiBoard = new LudoGameGUI(level);
        int Timer=1000;
        // Initialize grid
        consoleBoard.initialize();
        guiBoard.init();

        Statics.gen(0, new ArrayList<>(), 1);
        Scanner in = new Scanner(System.in);

        int totalPlayers, humanPlayers;
        do {
            System.out.println("How many players do you want to play?");
            totalPlayers = in.nextInt();
            System.out.println("How many humans do you want in the level?");
            humanPlayers = in.nextInt();
        } while (totalPlayers - humanPlayers < 0);

        level.Tot = totalPlayers;
        int turnCounter = 0;
        boolean gameOver = false;

        int cur=0;

        while (!gameOver) {
//            cur=Math.min(turnCounter,totalPlayers);
            System.out.println("It's " + level.players[turnCounter].name + "'s turn.");

            List<Integer> diceRoll = Statics.throw_dice();

            // Count active pawns before the turn
            int activeStonesBefore = 0;
            for (int i = 0; i < totalPlayers; i++) {
                if (i != turnCounter) {
                    for (int j = 0; j < 4; j++) {
                        if (level.players[i].pawns[j].is_playing) {
                            activeStonesBefore++;
                        }
                    }
                }
            }

            boolean check=false;
            int cntWin=0;
            for(int i=0;i<4;i++){
                cntWin+= level.players[level.curPlyr].pawns[i].is_playing?1:0;
                check|= level.checker(i,diceRoll.get(0));
            }
            check|=diceRoll.size()>1 && cntWin<4;
            if(!check){
                System.out.println("Dice rolled: " + diceRoll);

                System.out.println(" haven't any move :( DR:"+diceRoll.size()+" cntWin:"+ cntWin);
                TimeUnit.MILLISECONDS.sleep(Timer);
                turnCounter += 4 / totalPlayers;
                turnCounter %= totalPlayers==3? 3 : 4;
                level.curPlyr = turnCounter;
                continue;
            }
            if (Math.min(turnCounter,totalPlayers) < humanPlayers) {
                // Human player turn

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

                    if (level.update(stoneIdx, diceValue)) {
                        System.out.println("Player " + level.players[level.curPlyr].name + " moved stone " + stoneIdx + " with a roll of " + diceValue);
                        guiBoard.dispose();
                        guiBoard=new LudoGameGUI(level);
                        guiBoard.drawBoard();

                    } else {
                        System.out.println("Invalid move. Try again.");
                        diceRoll.add(ListIdx,diceValue);
                    }
                }
            } else {
                System.out.println("Dice rolled: " + diceRoll);

                // AI player turn
                System.out.println("* AI is making a move...");
                level = new Level(level.AI(1, diceRoll, level.curPlyr));
                guiBoard.dispose();
                guiBoard = new LudoGameGUI(level);
                guiBoard.drawBoard();

            }

            // Count active pawns after the turn
            int activeStonesAfter = 0;
            for (int i = 0; i < totalPlayers; i++) {
                if (i != turnCounter) {
                    for (int j = 0; j < 4; j++) {
                        if (level.players[i].pawns[j].is_playing) {
                            activeStonesAfter++;
                        }
                    }
                }
            }

            // Update grid
            consoleBoard = new LudoBoardConsole(level);

            consoleBoard.drawBoard();

            // Check for level over
            gameOver = level.EndGame();
            if (gameOver) {
                System.out.println("Player " + level.players[level.curPlyr].name + " wins!");
                break;
            }

            // Update turn logic
            if (activeStonesBefore - activeStonesAfter == 0) {

                turnCounter += 4 / totalPlayers;
                turnCounter %= totalPlayers==3? 3 : 4;
            }
            level.curPlyr = turnCounter;

            TimeUnit.MILLISECONDS.sleep(Timer);
        }
    }
}
