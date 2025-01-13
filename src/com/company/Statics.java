package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Statics {
     static final String RESET = "\u001B[0m";
      static final String RED = "\u001B[31m";
      static final String YELLOW = "\u001B[33m";
      static final String GREEN = "\u001B[32m";
      static final String BLUE = "\u001B[34m";
      static final String CYAN = "\u001B[36m";
      static final String MAGENTA = "\u001B[35m"; // New color for safe zones
      static final String WHITE = "\u001B[37m";
      static final String NEW = "\u001B[40m";

    static List<List<Integer>> expectMini=new ArrayList<List<Integer>>();
    static List<Integer>throw_dice(){
        Random rand = new Random();
        Scanner sc=new Scanner(System.in);
        List<Integer> ls=new ArrayList<>();
        int lst=6;
        while(ls.size()<3 && lst==6){
            lst=rand.nextInt(6)+1;
            ls.add(lst);
        }
//        int n=sc.nextInt();
//        ls.add(n);
        return ls;
    }

    static void gen(int sz, List<Integer>ls, int lst){
        if(lst>6) { lst = 1;sz++; ls.add(6);}
        if(sz ==3)return;
        List<Integer>tmp=new ArrayList<>();
        for(int i : ls)tmp.add(i);
        tmp.add(lst);
        expectMini.add(tmp);
        gen(sz,ls,lst+1);
    }

    static int[] getStonePosition(String stoneName) {
        switch (stoneName) {
            case "A1": return new int[]{11, 2};
            case "B1": return new int[]{11, 3};
            case "C1": return new int[]{12, 2};
            case "D1": return new int[]{12, 3};
            case "A2": return new int[]{2, 2};
            case "B2": return new int[]{2, 3};
            case "C2": return new int[]{3, 2};
            case "D2": return new int[]{3, 3};
            case "A3": return new int[]{2, 11};
            case "B3": return new int[]{2, 12};
            case "C3": return new int[]{3, 11};
            case "D3": return new int[]{3, 12};
            case "A4": return new int[]{11, 11};
            case "B4": return new int[]{11, 12};
            case "C4": return new int[]{12, 11};
            case "D4": return new int[]{12, 12};
            default: return null;
        }
    }
    static String getStoneColor(String stoneName) {
        if (stoneName.startsWith("A")) return Statics.YELLOW;
        if (stoneName.startsWith("B")) return Statics.BLUE;
        if (stoneName.startsWith("C")) return Statics.RED;
        if (stoneName.startsWith("D")) return Statics.GREEN;
        return Statics.WHITE;
    }

}
