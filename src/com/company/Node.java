package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    int num;
    boolean sheer = false;
    List<Node> children = new ArrayList<>();
    List<Pawn> pawns = new ArrayList<>();
    int x, y;
    String string = "";

    // Constructor
    Node(int num, boolean sheer) {
        this.num = num;
        this.sheer = sheer;
    }

    // Deep copy constructor
    Node(Node other) {
        this.num = other.num;
        this.sheer = other.sheer;
        this.x = other.x;
        this.y = other.y;
        this.string = other.string;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        Node node = (Node) o; // Cast the object
        return num == node.num && // Compare `num`
                sheer == node.sheer && // Compare `sheer`
                x == node.x && // Compare `x`
                y == node.y && // Compare `y`
                Objects.equals(string, node.string) && // Compare `string`
                Objects.equals(pawns, node.pawns); // Compare `pawns` list
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, sheer, x, y, string, pawns); // Exclude `children` to avoid circular reference
    }


}
