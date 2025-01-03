package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    int num;
    boolean is_safe = false;
    List<Node> ch = new ArrayList<>();
    List<Stone> stones = new ArrayList<>();
    int x, y;
    String string = "";

    // Constructor
    Node(int num, boolean is_safe) {
        this.num = num;
        this.is_safe = is_safe;
    }

    // Deep copy constructor
    Node(Node other) {
        this.num = other.num;
        this.is_safe = other.is_safe;
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
                is_safe == node.is_safe && // Compare `is_safe`
                x == node.x && // Compare `x`
                y == node.y && // Compare `y`
                Objects.equals(string, node.string) && // Compare `string`
                Objects.equals(stones, node.stones); // Compare `stones` list
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, is_safe, x, y, string, stones); // Exclude `ch` to avoid circular reference
    }


}
