package com.company;

import java.util.Arrays;
import java.util.Objects;

public class Player {
    String name="";
    Stone stones[];
    int idx;
    Player(String name,int Entry,int idx){
        this.name=name;
        this.idx=idx;
        stones=new Stone[4];
        for(int i=0;i<4;i++){
            stones[i]=new Stone(-1,Entry,idx);
        }
    }
    public Player(Player other) {
        this.name = other.name;
        this.idx = other.idx;
        this.stones = new Stone[4];
        for (int i = 0; i < 4; i++) {
            this.stones[i] = new Stone(other.stones[i]); // Deep copy each stone
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        Player player = (Player) o; // Cast the object
        return idx == player.idx && // Compare `idx`
                Objects.equals(name, player.name) && // Compare `name`
                Arrays.equals(stones, player.stones); // Compare `stones` array
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, idx); // Hash `name` and `idx`
        result = 31 * result + Arrays.hashCode(stones); // Hash `stones` array
        return result;
    }



}
