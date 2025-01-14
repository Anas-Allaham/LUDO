package com.company;

import java.util.Objects;

public class Pawn {
    int pos;
    String color;
    int EntryBlock;
    int Idx_Player;
    boolean is_playing=false;
    boolean is_win=false;
    String name;

    Pawn(int pos, int Entry, int Idx){
        this.pos=pos;
        this.EntryBlock=Entry;
        Idx_Player=Idx;
    }
    public Pawn(Pawn other) {
        this.pos = other.pos;
        this.color = other.color;
        this.EntryBlock = other.EntryBlock;
        this.Idx_Player = other.Idx_Player;
        this.is_playing = other.is_playing;
        this.is_win = other.is_win;
        this.name = other.name; // Strings are immutable, so this is fine
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        Pawn pawn = (Pawn) o; // Cast the object
        return pos == pawn.pos && // Compare `pos`
                EntryBlock == pawn.EntryBlock && // Compare `EntryBlock`
                Idx_Player == pawn.Idx_Player && // Compare `Idx_Player`
                is_playing == pawn.is_playing && // Compare `is_playing`
                is_win == pawn.is_win && // Compare `is_win`
                Objects.equals(color, pawn.color) && // Compare `color`
                Objects.equals(name, pawn.name); // Compare `name`
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, color, EntryBlock, Idx_Player, is_playing, is_win, name);
    }

}
