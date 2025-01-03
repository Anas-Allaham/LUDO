package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        LudoBoardConsole consoleBoard = new LudoBoardConsole(game);
        consoleBoard.init();

        Statics.gen(0,new ArrayList<>(),1);
//        System.out.println(Statics.expectMini);
        System.out.println("insert 1 for( 4 players with AI) 2 for (2 players with AI) 3 for(2 players with your friend)");
        boolean AI=false;
        Scanner in=new Scanner(System.in);
        int x=in.nextInt();
        AI=x<=2;
        int mode=4;

        if(x==1)mode=4;
        else mode=2;
        game.mode=mode;
        // Loop for alternating turns
        int turnCounter = 0;
        boolean gameOver = false;

        game.board.print(game.board.arr[1],game.board.arr[52],-1);

        while (true) {
            game.curPlayer=turnCounter%mode;
            System.out.println("It's " + game.players[game.curPlayer].name + "'s turn.");
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
                    int diceValue = diceRoll.get(0);
                    int stoneIdx = in.nextInt();

                    boolean moveSuccess = game.update(stoneIdx, diceValue );

                    if (moveSuccess) {
                        System.out.println("Player " + game.players[game.curPlayer].name + " moved stone " + stoneIdx + " with a roll of " + diceValue);
                    } else {
                        System.out.println("Player " + game.players[game.curPlayer].name + " cannot move.");
                    }
                    diceRoll.remove(0);
                }
//                game.curPlayer=1;


            }else{
                System.out.println("*");
//                game.curPlayer=1;

                game =new Game(game.AI(1,diceRoll, game.curPlayer));
//                game.curPlayer
//                game.curPlayer++;
//                game.curPlayer%=mode;

            }
            for(int i=0;i<4;i++){
                if(i!= game.curPlayer)
                    for(int j=0;j<4;j++){
                        pre-=game.players[i].stones[j].is_playing?1:0;
                    }
            }

            consoleBoard=new LudoBoardConsole(game);

            consoleBoard.drawBoard();

            gameOver = game.IsGameOver();

            if (gameOver) {
                System.out.println("Player " + game.players[game.curPlayer].name + " wins!");
                break;
            }
            if(pre<=0) {
//                game.curPlayer = (game.curPlayer + 1) % mode;
                turnCounter--;
            }
            turnCounter++;


//            Timer timer = new Timer(2000, e -> {
//                System.out.println("PRE : ");
//
//            });
//            timer.setRepeats(false);
//            timer.start();
            TimeUnit.SECONDS.sleep(1);


        }
    }
}
