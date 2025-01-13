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

}
