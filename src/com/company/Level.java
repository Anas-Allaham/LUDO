package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static com.company.Statics.getStonePosition;

public class Level {
    Board board;
    int curPlyr = 0;
    Player[] players;
    int Tot = 4;
    String[][] grid = new String[15][15];
    JLabel[][] cells = new JLabel[15][15];

    Level() {
        players = new Player[4];
        board = new Board();
        initialize();
    }

    public Level(Level other) {
        // Deep copy the board
        this.board = new Board(other.board);
        this.curPlyr = other.curPlyr;
        this.Tot = other.Tot;

        // Deep copy the players array
        this.players = deepCopyPlayers(other.players);

        // Deep copy the grid and cells arrays
        this.grid = deepCopyBoards(other.grid);
        this.cells = deepCopyCells(other.cells);

        // Copy pawns
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

    private void copyStonesFromOtherBoard(Level other) {
        for (int i = 0; i < 100; i++) {
            if (board.Adj[i] == null) continue;
            for (Pawn pawn : other.board.Adj[i].pawns) {
                board.Adj[i].pawns.add(getStone(pawn.Idx_Player, pawn.name));
            }
        }
    }

    private Pawn getStone(int idx, String name) {
        for (int i = 0; i < 4; i++) {
            if (players[idx].pawns[i].name.equals(name)) return players[idx].pawns[i];
        }
        return null;
    }

    void initialize() {
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
            players[playerIdx].pawns[i].color = color;
        }
    }

    private void assignStoneNames() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                players[i].pawns[j].name = String.valueOf((char) ('A' + j) )+ (i + 1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return curPlyr == level.curPlyr &&
                Tot == level.Tot &&
                Objects.equals(board, level.board) &&
                Arrays.equals(players, level.players) &&
                Arrays.deepEquals(grid, level.grid) &&
                Arrays.deepEquals(cells, level.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(board, curPlyr, Tot, grid, cells);
        result = 31 * result + Arrays.hashCode(players);
        return result;
    }


    void refreshCell(Node node) {
        StringBuilder nw = createStoneString(node);

        // Update node's string
        node.string = nw.toString();

        // Update board and cells based on the presence of pawns
        if (node.pawns.size() > 0) {
            updateBoardAndCells(node, nw, node.pawns.get(0).color);
        } else {
            updateBoardAndCells(node, nw, Statics.MAGENTA);
        }
    }

    private StringBuilder createStoneString(Node node) {
        StringBuilder nw = new StringBuilder("");

        // Append names of up to 2 pawns
        for (int i = 0; i < Math.min(node.pawns.size(), 2); i++) {
            nw.append(node.pawns.get(i).name);
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
        grid[node.x][node.y] = color + nw + Statics.RESET;
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

    void add_stone(Pawn pawn) {
        int[] position = getStonePosition(pawn.name);
        if (position == null) return;
        updateCell(position[0], position[1], Statics.WHITE, "", true);
    }

    void remove_stone(Pawn pawn) {
        int[] position = getStonePosition(pawn.name);
        if (position == null) return;
        String color = (pawn.color);
        updateCell(position[0], position[1], color, pawn.name, false);
    }

    private void updateCell(int x, int y, String color, String text, boolean isEmpty) {
        grid[x][y] = color + (isEmpty ? ".   " : text + "  ") + Statics.RESET;
        cells[x][y].setBackground(trans(color));
        cells[x][y].setText(isEmpty ? "" : text);
    }


    boolean dfs_update(Node node, int cnt, Pawn pawn) {
        if (!pawn.is_playing) {
            if (cnt == 6) {
                handleStoneEntry(pawn);
                return true;
            }
            return false;
        }

        if (cnt == 0) {
            return handleStonePlacement(node, pawn);
        }

        if (node.children.isEmpty()) {
            return false;
        }

        if (isBlocked(node, pawn)) {
            refreshCell(node);
            return false;
        }

        if (node.num == pawn.EntryBlock) {
            return dfs_update(node.children.get(1), cnt - 1, pawn);
        } else {
            return dfs_update(node.children.get(0), cnt - 1, pawn);
        }
    }

    boolean dfs_checker(Node node, int cnt, Pawn pawn) {
        if (!pawn.is_playing) {
            return cnt == 6;
        }

        if (cnt == 0) {
            return true;
        }

        if (node.children.isEmpty()) {
            return false;
        }

        if (isBlocked(node, pawn)) {
            return false;
        }

        if (node.num == pawn.EntryBlock) {
            return dfs_checker(node.children.get(1), cnt - 1, pawn);
        } else {
            return dfs_checker(node.children.get(0), cnt - 1, pawn);
        }
    }

    boolean checker(int idx, int val) {
        Pawn cur = players[curPlyr].pawns[idx];
        if (cur.is_win) return false;
        return dfs_checker(board.Adj[cur.pos > 0 ? cur.pos : 1], val, cur);
    }

    boolean update(int idx, int val) {
        Pawn cur = players[curPlyr].pawns[idx];
        if (cur.is_win) return false;

        int previousPos = cur.pos;
        boolean done = dfs_update(board.Adj[cur.pos > 0 ? cur.pos : 1], val, cur);

        if (done && previousPos != -1) {
            board.Adj[previousPos].pawns.remove(cur);
            refreshCell(board.Adj[previousPos]);
        }

        return done;
    }

    // Helper methods
    private void handleStoneEntry(Pawn pawn) {
        add_stone(pawn);
        pawn.is_playing = true;
        pawn.pos = (pawn.EntryBlock + 2) % 52;
        board.Adj[pawn.pos].pawns.add(pawn);
        refreshCell(board.Adj[pawn.pos]);
    }

    private boolean handleStonePlacement(Node node, Pawn pawn) {
        if (node.sheer) {
            node.pawns.add(pawn);
            pawn.pos = node.num;
            refreshCell(node);
            return true;
        }

        if (!node.pawns.isEmpty() && node.pawns.get(0).Idx_Player != pawn.Idx_Player && node.num != board.trg.num) {
            clearOpponentStones(node);
        }

        node.pawns.add(pawn);
        pawn.pos = node.num;

        if (node.num == board.trg.num) {
            pawn.is_win = true;
            pawn.is_playing = false;
        }

        refreshCell(node);
        return true;
    }

    private boolean isBlocked(Node node, Pawn pawn) {
        return node.pawns.size() >= 2 && node.pawns.get(0).Idx_Player != pawn.Idx_Player &&
                pawn.pos != node.num && !node.sheer;
    }

    private void clearOpponentStones(Node node) {
        for (Pawn tmp : node.pawns) {
            tmp.is_playing = false;
            tmp.pos = -1;
            remove_stone(tmp);
        }
        node.pawns.clear();
    }


    boolean EndGame(){
        boolean tmp =true;
        for(int i=0;i<4;i++) tmp &=players[curPlyr].pawns[i].is_win;

        return tmp;
    }

    double evaluation(int pl){
        double res =0;
        double priority=1e7;
        int cnt=0;
        for(int i=0;i<4;i++){
            cnt+=(players[pl].pawns[i].pos>=board.Adj[players[pl].pawns[i].EntryBlock].children.get(1).num? 1:0);
        }
        res +=priority*cnt;
        priority/=10;
        cnt=12;
        for(int i=0;i<4;i++){
            if(i==pl)continue;
            for(int j=0;j<4;j++){
                cnt-=players[i].pawns[j].is_playing||players[i].pawns[j].is_win?1:0;//fix
            }
        }
        res +=cnt*priority;
        priority/=10;
        cnt=0;
        for(int i=0;i<4;i++){
            if(players[pl].pawns[i].pos<=0 || players[pl].pawns[i].pos==board.trg.num )continue;
//            System.out.println(players[pl].pawns[i].pos);
            cnt+=(board.Adj[players[pl].pawns[i].pos].sheer ? 1:0);// null
        }
        res +=priority*cnt;
        priority/=10;
        cnt=0;
        for(int i=0;i<4;i++){
            cnt+=players[pl].pawns[i].is_playing?1:0;
        }
        res +=cnt*priority;
        priority/=10;
        for(int i=0;i <4;i++){
            if(players[pl].pawns[i].pos==-1)continue;
            cnt+=Math.max(0l,players[pl].pawns[i].pos-players[pl].pawns[i].EntryBlock);
        }
        res -=priority*cnt;
        return res;
    }

    double probability(List<Integer> ls) {
        return 1.0 / Math.pow(6, ls.size());
    }

    Level AI(int depth, List<Integer> ls, int pl) {
        double bestScore = (curPlyr == pl) ? -1e9 : 1e9;
        Level bestLevel = new Level(this);

        List<Level> possibleLevels = GetFinalBoards(ls);
        Set<Level> uniqueLevels = new LinkedHashSet<>(possibleLevels);

        for (Level level : uniqueLevels) {
            Level tempLevel = new Level(level);
            tempLevel.curPlyr = (curPlyr + 4 / Tot) % (Tot == 3 ? 3 : 4);

            double recResult = tempLevel.recursion(depth - 1, -1e9, 1e9, pl);
            double weightedScore = probability(ls) * recResult;

            if ((curPlyr == pl && bestScore < weightedScore) || (curPlyr != pl && bestScore > weightedScore)) {
                bestLevel = new Level(tempLevel);
                bestScore = weightedScore;
            }
        }

        return bestLevel;
    }

    double recursion(int depth, double mx, double mn, int pl) {
        if (depth == 0 || EndGame()) {
            return evaluation(pl);
        }

        double bestScore = (curPlyr == pl) ? -1e9 : 1e9;
        int activeStonesBefore = 0;
        for (int i = 0; i < Tot; i++) {
            if (i != curPlyr) {
                for (int j = 0; j < 4; j++) {
                    if (players[i].pawns[j].is_playing) {
                        activeStonesBefore++;
                    }
                }
            }
        }
        for (List<Integer> ls : Statics.expectMini) {
            List<Level> possibleLevels = GetFinalBoards(ls);
            Set<Level> uniqueLevels = new LinkedHashSet<>(possibleLevels);

            for (Level level : uniqueLevels) {
                int activeStonesAfter = 0;
                for (int i = 0; i < level.Tot; i++) {
                    if (i != level.curPlyr) {
                        for (int j = 0; j < 4; j++) {
                            if (level.players[i].pawns[j].is_playing) {
                                activeStonesAfter++;
                            }
                        }
                    }
                }
                boolean W=activeStonesAfter==activeStonesBefore;
                Level nextLevel = new Level(level);
                if(W)  nextLevel.curPlyr = (curPlyr + 4 / Tot) % (Tot == 3 ? 3 : 4);
                double recResult = nextLevel.recursion(depth - (W?1:0), mx, mn, pl);
                double weightedScore = probability(ls) * recResult;

                if (curPlyr == pl) {
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

    List<Level> GetFinalBoards(List<Integer> ls) {
        List<Level> levelStates = new ArrayList<>();

        if (ls.isEmpty()) {
            levelStates.add(new Level(this));
            return levelStates;
        }

        List<Integer> remainingRolls = new ArrayList<>(ls);

        for (int i = 0; i < 4; i++) {
            if (remainingRolls.get(0) == 6 || players[curPlyr].pawns[i].is_playing) {
                Level nextLevel = new Level(this);
                int rollValue = remainingRolls.remove(0);

                if (!nextLevel.checker(i, rollValue)) {
                    remainingRolls.add(0, rollValue);
                    continue;
                }

                nextLevel.update(i, rollValue);
                levelStates.addAll(nextLevel.GetFinalBoards(remainingRolls));

                remainingRolls.add(0, rollValue); // Restore the list after recursion
            }
        }

        return levelStates;
    }
}
