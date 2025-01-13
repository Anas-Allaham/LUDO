package com.company;

import java.util.Arrays;
import java.util.Objects;

public class Board {
    Node[] Adj = new Node[100];
    Node start;
    Node trg;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        Board board = (Board) o; // Cast the object
        return Arrays.equals(Adj, board.Adj) && // Compare `Adj` (array of Nodes)
                Objects.equals(start, board.start) && // Compare `start` Node
                Objects.equals(trg, board.trg); // Compare `trg` Node
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(start, trg); // Hash `start` and `trg`
        result = 31 * result + Arrays.hashCode(Adj); // Add hash of `Adj` array
        return result;
    }

    Board() {
        init();
    }
    public Board(Board other) {
        init();
        for(int i=0;i<100;i++){
            if(other.Adj[i]==null)continue;
            Adj[i].x=other.Adj[i].x;
            Adj[i].y=other.Adj[i].y;
            Adj[i].string=other.Adj[i].string;
        }
    }

    void createTree(int i){
        if(i==53)return;
        Node cur = new Node(i, (i - 1) % 13 == 0);
        if(i>1) {
            if (Adj[i-1] != null) {
                Adj[i-1].ch.add(cur);
            }
        }
        Adj[i] = cur;
        createTree(i+1);
    }

    void init() {
        createTree(1);
        Adj[52].ch.add(Adj[1]);
        start = Adj[1];
        int nw = 53;
        int prefix = -1;
        Node lst;
        for (int i = 0; i < 4; i++) {
            prefix += 13;
            lst = Adj[prefix];
            int st=nw;
            while(nw<st+5) {
                Node tmp = new Node(nw, false);
                lst.ch.add(tmp);
                Adj[nw++] = tmp;
                lst = tmp;
            }
        }
        trg = new Node(nw, false);
        Adj[62].ch.add(trg);
        Adj[57].ch.add(trg);
        Adj[67].ch.add(trg);
        Adj[72].ch.add(trg);

    }


}
