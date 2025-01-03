package com.company;

import java.util.Arrays;
import java.util.Objects;

public class Board {
    Node[] arr = new Node[100];
    Node start;
    Node target;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        Board board = (Board) o; // Cast the object
        return Arrays.equals(arr, board.arr) && // Compare `arr` (array of Nodes)
                Objects.equals(start, board.start) && // Compare `start` Node
                Objects.equals(target, board.target); // Compare `target` Node
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(start, target); // Hash `start` and `target`
        result = 31 * result + Arrays.hashCode(arr); // Add hash of `arr` array
        return result;
    }

    Board() {
        init();
    }
    public Board(Board other) {
        init();
        for(int i=0;i<100;i++){
            if(other.arr[i]==null)continue;
            arr[i].x=other.arr[i].x;
            arr[i].y=other.arr[i].y;
            arr[i].string=other.arr[i].string;
        }
    }

    void init() {
        Node lst = null;
        for (int i = 1; i <= 52; i++) {
            Node cur = new Node(i, (i - 1) % 13 == 0);
            if (lst != null) {
                lst.ch.add(cur);
            }
            lst = cur;
            arr[i] = cur;
        }
        lst.ch.add(arr[1]);
        start = arr[1];
        int nw = 53;
        int pre = -1;
        for (int i = 0; i < 4; i++) {
            pre += 13;
            lst = arr[pre];
            for (int j = 0; j < 5; j++) {
                Node child = new Node(nw, false);
                lst.ch.add(child);
                arr[nw++] = child;
                lst = child;
            }
        }
        target = new Node(nw, false);
        for (int i = 1; i < nw; i++) {
            if (arr[i].ch.size() == 0) arr[i].ch.add(target);
        }
    }

    void print(Node node, Node st, int p) {
        if (node == st) return;
        System.out.print(node.num + "->");
        for (Node i : node.ch) {
            System.out.print(i.num + ",");
        }
        System.out.println();
        for (Node child : node.ch) {
            print(child, st, node.num);
        }
    }


}
