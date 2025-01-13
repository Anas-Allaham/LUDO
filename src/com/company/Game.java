package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static com.company.Statics.getStonePosition;

public class Game {
    Board board;
    int curPlayer = 0;
    Player[] players;
    int Tot = 4;
    String[][] boards = new String[15][15];
    JLabel[][] cells = new JLabel[15][15];

    Game() {
        players = new Player[4];
        board = new Board();
        init();
    }

    public Game(Game other) {
        // Deep copy the board
        this.board = new Board(other.board);
        this.curPlayer = other.curPlayer;
        this.Tot = other.Tot;

        // Deep copy the players array
        this.players = deepCopyPlayers(other.players);

        // Deep copy the boards and cells arrays
        this.boards = deepCopyBoards(other.boards);
        this.cells = deepCopyCells(other.cells);

        // Copy stones
        copyStonesFromOtherBoard(other);
    }

    private Player[] deepCopyPlayers(Player[] otherPlayers) {
        Player[] copiedPlayers = new Player[otherPlayers.length];
        for (int i = 0; i < otherPlayers.length; i++) {
            copiedPlayers[i] = new Player(otherPlayers[i]);
        }
        return copiedPlayers;
    }

    private String[][] deepCopyBoards(String[][] otherBoards) {
        String[][] copiedBoards = new String[15][15];
        for (int i = 0; i < 15; i++) {
            copiedBoards[i] = Arrays.copyOf(otherBoards[i], otherBoards[i].length);
        }
        return copiedBoards;
    }

    private JLabel[][] deepCopyCells(JLabel[][] otherCells) {
        JLabel[][] copiedCells = new JLabel[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                copiedCells[i][j] = new JLabel(otherCells[i][j].getText());
                copiedCells[i][j].setBackground(otherCells[i][j].getBackground());
            }
        }
        return copiedCells;
    }

    private void copyStonesFromOtherBoard(Game other) {
        for (int i = 0; i < 100; i++) {
            if (board.Adj[i] == null) continue;
            for (Stone stone : other.board.Adj[i].stones) {
                board.Adj[i].stones.add(getStone(stone.Idx_Player, stone.name));
            }
        }
    }

    private Stone getStone(int idx, String name) {
        for (int i = 0; i < 4; i++) {
            if (players[idx].stones[i].name.equals(name)) return players[idx].stones[i];
        }
        return null;
    }

    void init() {
        int pre = 1;
        for (int i = 0; i < 4; i++) {
            players[i] = new Player("Player" + (i + 1), (pre + 50) % 52, i);
            pre += 52/4;
        }

        assignStoneColors();
        assignStoneNames();
    }

    private void assignStoneColors() {
        setPlayerStoneColor(0, Statics.YELLOW);
        setPlayerStoneColor(1, Statics.BLUE);
        setPlayerStoneColor(2, Statics.RED);
        setPlayerStoneColor(3, Statics.GREEN);
    }

    private void setPlayerStoneColor(int playerIdx, String color) {
        for (int i = 0; i < 4; i++) {
            players[playerIdx].stones[i].color = color;
        }
    }

    private void assignStoneNames() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                players[i].stones[j].name = String.valueOf((char) ('A' + j) )+ (i + 1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return curPlayer == game.curPlayer &&
                Tot == game.Tot &&
                Objects.equals(board, game.board) &&
                Arrays.equals(players, game.players) &&
                Arrays.deepEquals(boards, game.boards) &&
                Arrays.deepEquals(cells, game.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(board, curPlayer, Tot, boards, cells);
        result = 31 * result + Arrays.hashCode(players);
        return result;
    }


    void refreshCell(Node node) {
        StringBuilder nw = createStoneString(node);

        // Update node's string
        node.string = nw.toString();

        // Update board and cells based on the presence of stones
        if (node.stones.size() > 0) {
            updateBoardAndCells(node, nw, node.stones.get(0).color);
        } else {
            updateBoardAndCells(node, nw, Statics.MAGENTA);
        }
    }

    private StringBuilder createStoneString(Node node) {
        StringBuilder nw = new StringBuilder("");

        // Append names of up to 2 stones
        for (int i = 0; i < Math.min(node.stones.size(), 2); i++) {
            nw.append(node.stones.get(i).name);
        }

        // Pad with spaces until the length is 4
        while (nw.length() < 4) {
            nw.append(" ");
        }

        // Replace the first character with 'S' if it's a space
        if (nw.charAt(0) == ' ') {
            nw.setCharAt(0, '#');
        }

        return nw;
    }

    private void updateBoardAndCells(Node node, StringBuilder nw, String color) {
        boards[node.x][node.y] = color + nw + Statics.RESET;
        cells[node.x][node.y].setBackground(trans(color));
        cells[node.x][node.y].setText(String.valueOf(nw));
    }


Color trans(String col){
    if(col.equals(Statics.WHITE))return new Color(182, 181, 181);
    if(col.equals(Statics.MAGENTA))return new Color(248, 236, 211);
    if(col.equals(Statics.BLUE))return new Color(129, 189, 229);
    if(col.equals(Statics.RED))return new Color(225, 161, 245);
    if(col.equals(Statics.GREEN))return new Color (183, 245, 161);
    if(col.equals(Statics.YELLOW))return new Color (231, 240, 144);
    return Color.CYAN;
}

    void add_stone(Stone stone) {
        int[] position = getStonePosition(stone.name);
        if (position == null) return;

        updateCell(position[0], position[1], Statics.WHITE, "", true);
    }

    void remove_stone(Stone stone) {
        int[] position = getStonePosition(stone.name);
        if (position == null) return;

        String color = (stone.color);
        updateCell(position[0], position[1], color, stone.name, false);
    }

    private void updateCell(int x, int y, String color, String text, boolean isEmpty) {
        boards[x][y] = color + (isEmpty ? ".   " : text + "  ") + Statics.RESET;
        cells[x][y].setBackground(trans(color));
        cells[x][y].setText(isEmpty ? "" : text);
    }


    boolean dfs_update(Node node, int cnt, Stone stone) {
        if (!stone.is_playing) {
            if (cnt == 6) {
                handleStoneEntry(stone);
                return true;
            }
            return false;
        }

        if (cnt == 0) {
            return handleStonePlacement(node, stone);
        }

        if (node.ch.isEmpty()) {
            return false;
        }

        if (isBlocked(node, stone)) {
            refreshCell(node);
            return false;
        }

        if (node.num == stone.EntryBlock) {
            return dfs_update(node.ch.get(1), cnt - 1, stone);
        } else {
            return dfs_update(node.ch.get(0), cnt - 1, stone);
        }
    }

    boolean dfs_checker(Node node, int cnt, Stone stone) {
        if (!stone.is_playing) {
            return cnt == 6;
        }

        if (cnt == 0) {
            return true;
        }

        if (node.ch.isEmpty()) {
            return false;
        }

        if (isBlocked(node, stone)) {
            return false;
        }

        if (node.num == stone.EntryBlock) {
            return dfs_checker(node.ch.get(1), cnt - 1, stone);
        } else {
            return dfs_checker(node.ch.get(0), cnt - 1, stone);
        }
    }

    boolean checker(int idx, int val) {
        Stone cur = players[curPlayer].stones[idx];
        if (cur.is_win) return false;
        return dfs_checker(board.Adj[cur.pos > 0 ? cur.pos : 1], val, cur);
    }

    boolean update(int idx, int val) {
        Stone cur = players[curPlayer].stones[idx];
        if (cur.is_win) return false;

        int previousPos = cur.pos;
        boolean done = dfs_update(board.Adj[cur.pos > 0 ? cur.pos : 1], val, cur);

        if (done && previousPos != -1) {
            board.Adj[previousPos].stones.remove(cur);
            refreshCell(board.Adj[previousPos]);
        }

        return done;
    }

    // Helper methods
    private void handleStoneEntry(Stone stone) {
        add_stone(stone);
        stone.is_playing = true;
        stone.pos = (stone.EntryBlock + 2) % 52;
        board.Adj[stone.pos].stones.add(stone);
        refreshCell(board.Adj[stone.pos]);
    }

    private boolean handleStonePlacement(Node node, Stone stone) {
        if (node.sheer) {
            node.stones.add(stone);
            stone.pos = node.num;
            refreshCell(node);
            return true;
        }

        if (!node.stones.isEmpty() && node.stones.get(0).Idx_Player != stone.Idx_Player && node.num != board.trg.num) {
            clearOpponentStones(node);
        }

        node.stones.add(stone);
        stone.pos = node.num;

        if (node.num == board.trg.num) {
            stone.is_win = true;
            stone.is_playing = false;
        }

        refreshCell(node);
        return true;
    }

    private boolean isBlocked(Node node, Stone stone) {
        return node.stones.size() >= 2 && node.stones.get(0).Idx_Player != stone.Idx_Player &&
                stone.pos != node.num && !node.sheer;
    }

    private void clearOpponentStones(Node node) {
        for (Stone tmp : node.stones) {
            tmp.is_playing = false;
            tmp.pos = -1;
            remove_stone(tmp);
        }
        node.stones.clear();
    }


    boolean EndGame(){
        boolean tmp =true;
        for(int i=0;i<4;i++) tmp &=players[curPlayer].stones[i].is_win;

        return tmp;
    }

    double Heuristic (int pl){
        double score=0;
        double priority=1e6;
        int cnt=0;
        for(int i=0;i<4;i++){
            cnt+=(players[pl].stones[i].pos>=board.Adj[players[pl].stones[i].EntryBlock].ch.get(1).num? 1:0);
        }
        score+=priority*cnt;
        priority/=10;
        cnt=12;
        for(int i=0;i<4;i++){
            if(i==pl)continue;
            for(int j=0;j<4;j++){
                cnt-=players[i].stones[j].is_playing||players[i].stones[j].is_win?1:0;//fix
            }
        }
        score+=cnt*priority;
        priority/=10;
        cnt=0;
        for(int i=0;i<4;i++){
            if(players[pl].stones[i].pos<=0 || players[pl].stones[i].pos==board.trg.num )continue;
//            System.out.println(players[pl].stones[i].pos);
            cnt+=(board.Adj[players[pl].stones[i].pos].sheer ? 1:0);// null
        }
        score+=priority*cnt;
        priority/=100;
        cnt=0;
        for(int i=0;i<4;i++){
            cnt+=players[pl].stones[i].is_playing?1:0;
        }
        score+=cnt*priority;
        for(int i=0;i<4;i++){
            if(players[pl].stones[i].pos==-1)continue;
            cnt+=Math.max(0l,players[pl].stones[i].pos-players[pl].stones[i].EntryBlock);
        }
        score-=priority*cnt;
        return score;
    }

//    double probability(List<Integer>ls){
//        double prob=Math.pow(6,ls.size());
//        return 1.0/prob;
//    }
//
//    Game AI(int depth,List<Integer>ls,int pl){
//        double ans=(curPlayer==pl)?-1e9:1e9;
//        Game fin=new Game(this);
//            List<Game>mul=apply(ls);
//            Set<Game> uniqueSet = new LinkedHashSet<>(mul);
//
//            for (Game cur : uniqueSet) {
//                Game tmp=new Game(cur);
//
//                tmp.curPlayer = (curPlayer + 4/Tot) % (Tot==3? 3 : 4);
//                double recResult = tmp.rec(depth - 1,-1e9,1e9,pl);
//                double tt = probability(ls) * recResult;
////                System.out.println("**"+tt);
//                if (ans < tt) {
//                    fin = new Game(tmp);
//                    ans = tt;
//                }
//            }
//
//
//        return new Game(fin);
//    }
//
//
//
//    double rec(int depth, double mx, double mn, int pl) {
//        if (depth == 0 || EndGame()) {
//            return Heuristic(pl);
//        }
//
//        double ans = (curPlayer == pl) ? -1e9 : 1e9;
//
//        for (List<Integer> ls : Statics.expectMini) {
//            List<Game> mul = apply(ls);
//            Set<Game> uniqueSet = new LinkedHashSet<>(mul);
//
//            for (Game cur : uniqueSet) {
//                Game tmp = new Game(cur);
//                tmp.curPlayer = (curPlayer + 4/Tot) % (Tot==3? 3 : 4);
//                double recResult = tmp.rec(depth - 1, mx, mn,pl);
//                double tt = probability(ls) * recResult;
//
//                if (curPlayer == pl) {
//                    ans = Math.max(ans, tt);
//                    mx = Math.max(mx, ans);
//                } else {
//                    ans = Math.min(ans, tt);
//                    mn = Math.min(mn, ans);
//                }
//
//                if (mn <= mx) {
//                    break; // Prune remaining branches
//                }
//            }
//        }
//        return ans;
//    }
//
//    List<Game> apply(List<Integer> ls) {
//        List<Game> lg = new ArrayList<>();
//        if (ls.isEmpty()) {
////            System.out.println(this.Heuristic());
//            Game cur = new Game(this);
//            lg.add(cur);
//            return lg;
//        }
//
//        List<Integer> lls = new ArrayList<>(ls); // Copy list to avoid mutation
//        for (int i = 0; i < 4; i++) {
//            // Check if the current stone is playable
////            System.out.println(ls);
//            if (lls.get(0)==6||players[curPlayer].stones[i].is_playing) {
////                for (int j = 0; j < lls.size(); j++) {
//                    Game nxt = new Game(this); // Create a deep copy of the game
//                    int val = lls.get(0);
//                    List<Integer> remaining = new ArrayList<>(lls);
//                    remaining.remove(0);
//
//                    // Apply the move
//                    if(!nxt.checker(i,val)){
//                        remaining.add(0,val);
//                        continue;
//                    }
//                    nxt.update(i, val);
//
//                    lg.addAll(nxt.apply(remaining)); // Recursively add further states
//                    remaining.add(0,val);
//                }
////            }
//
//        }
//
//        return lg;
//    }

    double probability(List<Integer> ls) {
        return 1.0 / Math.pow(6, ls.size());
    }

    Game AI(int depth, List<Integer> ls, int pl) {
        double bestScore = (curPlayer == pl) ? -1e9 : 1e9;
        Game bestGame = new Game(this);

        List<Game> possibleGames = apply(ls);
        Set<Game> uniqueGames = new LinkedHashSet<>(possibleGames);

        for (Game game : uniqueGames) {
            Game tempGame = new Game(game);
            tempGame.curPlayer = (curPlayer + 4 / Tot) % (Tot == 3 ? 3 : 4);

            double recResult = tempGame.rec(depth - 1, -1e9, 1e9, pl);
            double weightedScore = probability(ls) * recResult;

            if ((curPlayer == pl && bestScore < weightedScore) || (curPlayer != pl && bestScore > weightedScore)) {
                bestGame = new Game(tempGame);
                bestScore = weightedScore;
            }
        }

        return bestGame;
    }

    double rec(int depth, double mx, double mn, int pl) {
        if (depth == 0 || EndGame()) {
            return Heuristic(pl);
        }

        double bestScore = (curPlayer == pl) ? -1e9 : 1e9;

        for (List<Integer> ls : Statics.expectMini) {
            List<Game> possibleGames = apply(ls);
            Set<Game> uniqueGames = new LinkedHashSet<>(possibleGames);

            for (Game game : uniqueGames) {
                Game tempGame = new Game(game);
                tempGame.curPlayer = (curPlayer + 4 / Tot) % (Tot == 3 ? 3 : 4);

                double recResult = tempGame.rec(depth - 1, mx, mn, pl);
                double weightedScore = probability(ls) * recResult;

                if (curPlayer == pl) {
                    bestScore = Math.max(bestScore, weightedScore);
                    mx = Math.max(mx, bestScore);
                } else {
                    bestScore = Math.min(bestScore, weightedScore);
                    mn = Math.min(mn, bestScore);
                }

                if (mn <= mx) {
                    break; // Alpha-beta pruning
                }
            }
        }
        return bestScore;
    }

    List<Game> apply(List<Integer> ls) {
        List<Game> gameStates = new ArrayList<>();

        if (ls.isEmpty()) {
            gameStates.add(new Game(this));
            return gameStates;
        }

        List<Integer> remainingRolls = new ArrayList<>(ls);

        for (int i = 0; i < 4; i++) {
            if (remainingRolls.get(0) == 6 || players[curPlayer].stones[i].is_playing) {
                Game nextGame = new Game(this);
                int rollValue = remainingRolls.remove(0);

                if (!nextGame.checker(i, rollValue)) {
                    remainingRolls.add(0, rollValue);
                    continue;
                }

                nextGame.update(i, rollValue);
                gameStates.addAll(nextGame.apply(remainingRolls));

                remainingRolls.add(0, rollValue); // Restore the list after recursion
            }
        }

        return gameStates;
    }






}
