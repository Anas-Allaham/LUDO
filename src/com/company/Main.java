package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(); // Initialize the game with players and stones
        LudoBoardConsole consoleBoard = new LudoBoardConsole(game);
        consoleBoard.init();

        Statics.gen(0,new ArrayList<>(),1);
        System.out.println(Statics.expectMini);
        boolean AI=false;
        Scanner in=new Scanner(System.in);
        AI=in.nextBoolean();
        // Loop for alternating turns
        int turnCounter = 0;
        boolean gameOver = false;

        int mode=2;
        game.board.print(game.board.arr[1],game.board.arr[52],-1);

        while (true) {
            Player currentPlayer = game.players[turnCounter % mode]; // Get the current player (alternates between 4 players)
            System.out.println("It's " + currentPlayer.name + "'s turn.");
            List<Integer> diceRoll = Statics.throw_dice();
            System.out.println("Dice rolled: " + diceRoll);
            int pre=0;

            for(int i=0;i<4;i++){
                if(i!= game.curPlayer)
                    for(int j=0;j<4;j++){
                        pre+=game.players[i].stones[j].is_playing?1:0;
                    }
            }

            if(!AI||game.curPlayer==0){

                while (diceRoll.size()>0) {
                    int diceValue = diceRoll.get(0); // Example: choose the first dice value
                    int stoneIdx = in.nextInt(); // Example: choose the first stone (you can make this dynamic)

                    boolean moveSuccess = game.update(stoneIdx, diceValue );

                    if (moveSuccess) {
                        System.out.println("Player " + currentPlayer.name + " moved stone " + stoneIdx + " with a roll of " + diceValue);
                    } else {
                        System.out.println("Player " + currentPlayer.name + " cannot move.");
                    }
                    diceRoll.remove(0);
                }
                game.curPlayer=1;


            }else{
                System.out.println("*");
                game.curPlayer=1;

                game =new Game(game.AI(1,diceRoll));
//                game.curPlayer
                game.curPlayer=0;

            }
            for(int i=0;i<4;i++){
                if(i!= game.curPlayer)
                    for(int j=0;j<4;j++){
                        pre-=game.players[i].stones[j].is_playing?1:0;
                    }
            }

            consoleBoard=new LudoBoardConsole(game);

            // Draw the board after each turn
            consoleBoard.drawBoard();

            // Check if the game is over
            gameOver = game.IsGameOver();

            if (gameOver) {
                System.out.println("Player " + currentPlayer.name + " wins!");
                break;
            }
            System.out.println("PRE : "+pre);
            if(pre<=0) {
//                game.curPlayer = (game.curPlayer + 1) % mode;
                turnCounter++;
            }

        }
    }
}
