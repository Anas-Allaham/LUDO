package com.company;

public class Pair{
    double x;
    Game g;
    Pair(double x, Game g){
        this.g = new Game(g);
        this.x= x;
    }

}
