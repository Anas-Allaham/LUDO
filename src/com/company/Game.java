package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Game {
    Board board;
    int curPlayer=0;
    Player []players;
    int Tot =4;
    String[][] boards = new String[15][15];
    JLabel[][] cells = new JLabel[15][15];



    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        Game game = (Game) o; // Cast the object
        return curPlayer == game.curPlayer && // Compare primitive field `curPlayer`
                Tot == game.Tot && // Compare primitive field `Tot`
                Objects.equals(board, game.board) && // Compare `board` (nullable object)
                Arrays.equals(players, game.players)&&
                Arrays.equals(boards,game.boards)&& // Compare `players` array
                Arrays.equals(cells,game.cells); // Compare `players` array

    }

    @Override
    public int hashCode() {
        int result = Objects.hash(board, curPlayer, Tot,boards,cells); // Hash `board`, `curPlayer`, and `Tot`
        result = 31 * result + Arrays.hashCode(players); // Add hash of `players` array
        return result;
    }

    Game(){
        players=new Player[4];
        board=new Board();
        init();
    }
    public Game(Game other){
        // Deep copy the board
        this.board = new Board(other.board);

        // Copy primitive fields
        this.curPlayer = other.curPlayer;
        this.Tot = other.Tot;

        // Deep copy the players array
        this.players = new Player[other.players.length];
        for (int i = 0; i < other.players.length; i++) {
            this.players[i] = new Player(other.players[i]);
        }
        for(int i=0;i<100;i++){
            if(board.Adj[i]==null)continue;
            for(Stone j : other.board.Adj[i].stones)
                board.Adj[i].stones.add(get_stone(j.Idx_Player,j.name));

        }
        this.boards = new String[15][15];
        this.cells = new JLabel[15][15];
        for (int i = 0; i < 15; i++) {
            this.boards[i] = Arrays.copyOf(other.boards[i], other.boards[i].length);
//            this.cells[i] = Arrays.copyOf(other.cells[i], other.cells[i].length);
        }
        for (int i = 0; i < 15; i++) {
            for(int j=0;j<15;j++) {
                this.cells[i][j]=new JLabel(other.cells[i][j].getText());
                this.cells[i][j].setBackground(other.cells[i][j].getBackground());

            }
        }

    }
    Stone get_stone(int idx,String name){
        Stone t=null;
        for(int i=0;i<4;i++){
            if(players[idx].stones[i].name.equals(name))t=players[idx].stones[i];
        }

        return t;
    }

    void init(){
        int pre=1;
        for (int i=0;i<4;i++){
            players[i]=new Player("Player"+(i+1),(pre-2+52)%52,i);
            System.out.println(i+ " " +(pre-2+52)%52);
            pre+=13;
        }
        for (int i=0;i<4;i++){
            players[0].stones[i].color=Statics.YELLOW;
        } for (int i=0;i<4;i++){
            players[1].stones[i].color=Statics.BLUE;
        } for (int i=0;i<4;i++){
            players[2].stones[i].color=Statics.RED;
        } for (int i=0;i<4;i++){
            players[3].stones[i].color=Statics.GREEN;
        }
        for(int i=0;i<4;i++){
            players[i].stones[0].name="A"+(i+1);
        }
        for(int i=0;i<4;i++){
            players[i].stones[1].name="B"+(i+1);
        }
        for(int i=0;i<4;i++){
            players[i].stones[2].name="C"+(i+1);
        }
        for(int i=0;i<4;i++){
            players[i].stones[3].name="D"+(i+1);
        }
    }

void refreshCell(Node node) {
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
        nw.setCharAt(0, 'S');
    }

    // Update node's string
    node.string = nw.toString();

    if (node.stones.size() > 0) {

        boards[node.x][node.y] = node.stones.get(0).color + nw + Statics.RESET;//-1 is out of bounds
        cells[node.x][node.y].setBackground(trans(node.stones.get(0).color));
        cells[node.x][node.y].setText(String.valueOf(nw));//-1 is out of bounds
    } else {
        boards[node.x][node.y] = (Statics.MAGENTA) + nw + Statics.RESET;
        cells[node.x][node.y].setBackground(trans(Statics.MAGENTA));
        cells[node.x][node.y].setText(String.valueOf(nw));//-1 is out of bounds
    }
}

Color trans(String col){
    if(col.equals(Statics.WHITE))return Color.WHITE;
    if(col.equals(Statics.MAGENTA))return Color.MAGENTA;
    if(col.equals(Statics.BLUE))return Color.BLUE;
    if(col.equals(Statics.RED))return Color.RED;
    if(col.equals(Statics.GREEN))return Color.GREEN;
    if(col.equals(Statics.YELLOW))return Color.YELLOW;
    return Color.CYAN;
}



    void add_stone(Stone stone){
        if(stone.name.equals("A1")){
            boards[11][2]=Statics.WHITE+".   "+Statics.RESET;
            cells[11][2].setBackground(trans(Statics.WHITE));
            cells[11][2].setText("");
        }else if(stone.name.equals("B1")){
            boards[11][3]=Statics.WHITE+".   "+Statics.RESET;
            cells[11][3].setBackground(trans(Statics.WHITE));
            cells[11][3].setText("");
        }else if(stone.name.equals("C1")){
            boards[12][2]=Statics.WHITE+".   "+Statics.RESET;
            cells[12][2].setBackground(trans(Statics.WHITE));
            cells[12][2].setText("");
        }else if(stone.name.equals("D1")){
            boards[12][3]=Statics.WHITE+".   "+Statics.RESET;
            cells[12][3].setBackground(trans(Statics.WHITE));
            cells[12][3].setText("");
        }
        if(stone.name.equals("A2")){
            boards[2][2]=Statics.WHITE+".   "+Statics.RESET;
            cells[2][2].setBackground(trans(Statics.WHITE));
            cells[2][2].setText("");
        }else if(stone.name.equals("B2")){
            boards[2][3]=Statics.WHITE+".   "+Statics.RESET;
            cells[2][3].setBackground(trans(Statics.WHITE));
            cells[2][3].setText("");
        }else if(stone.name.equals("C2")){
            boards[3][2]=Statics.WHITE+".   "+Statics.RESET;
            cells[3][2].setBackground(trans(Statics.WHITE));
            cells[3][2].setText("");
        }else if(stone.name.equals("D2")){
            boards[3][3]=Statics.WHITE+".   "+Statics.RESET;
            cells[3][3].setBackground(trans(Statics.WHITE));
            cells[3][3].setText("");
        }
        if(stone.name.equals("A3")){
            boards[2][11]=Statics.WHITE+".   "+Statics.RESET;
            cells[2][11].setBackground(trans(Statics.WHITE));
            cells[2][11].setText("");
        }else if(stone.name.equals("B3")){
            boards[2][12]=Statics.WHITE+".   "+Statics.RESET;
            cells[2][12].setBackground(trans(Statics.WHITE));
            cells[2][12].setText("");
        }else if(stone.name.equals("C3")){
            boards[3][11]=Statics.WHITE+".   "+Statics.RESET;
            cells[3][11].setBackground(trans(Statics.WHITE));
            cells[3][11].setText("");
        }else if(stone.name.equals("D3")){
            boards[3][12]=Statics.WHITE+".   "+Statics.RESET;
            cells[3][12].setBackground(trans(Statics.WHITE));
            cells[3][12].setText("");
        }  if(stone.name.equals("A4")){
            boards[11][11]=Statics.WHITE+".   "+Statics.RESET;
            cells[11][11].setBackground(trans(Statics.WHITE));
            cells[11][11].setText("");
        }else if(stone.name.equals("B4")){
            boards[11][12]=Statics.WHITE+".   "+Statics.RESET;
            cells[11][12].setBackground(trans(Statics.WHITE));
            cells[11][12].setText("");
        }else if(stone.name.equals("C4")){
            boards[12][11]=Statics.WHITE+".   "+Statics.RESET;
            cells[12][11].setBackground(trans(Statics.WHITE));
            cells[12][11].setText("");
        }else if(stone.name.equals("D4")){
            boards[12][12]=Statics.WHITE+".   "+Statics.RESET;
            cells[12][12].setBackground(trans(Statics.WHITE));
            cells[12][12].setText("");
        }


    }

    void remove_stone(Stone stone) {
        if (stone.name.equals("A1")) {
            boards[11][2] = Statics.YELLOW + "A1  " + Statics.RESET;
            cells[11][2].setBackground(trans(Statics.YELLOW));
            cells[11][2].setText("A1");
        } else if (stone.name.equals("B1")) {
            boards[11][3] = Statics.YELLOW + "B1  " + Statics.RESET;
            cells[11][3].setBackground(trans(Statics.YELLOW));
            cells[11][3].setText("B1");
        } else if (stone.name.equals("C1")) {
            boards[12][2] = Statics.YELLOW + "C1  " + Statics.RESET;
            cells[12][2].setBackground(trans(Statics.YELLOW));
            cells[12][2].setText("C1");
        } else if (stone.name.equals("D1")) {
            boards[12][3] = Statics.YELLOW + "D1  " + Statics.RESET;
            cells[12][3].setBackground(trans(Statics.YELLOW));
            cells[12][3].setText("D1");
        }
        if (stone.name.equals("A2")) {
            boards[2][2] = Statics.BLUE + "A2  " + Statics.RESET;
            cells[2][2].setBackground(trans(Statics.BLUE));
            cells[2][2].setText("A2");
        } else if (stone.name.equals("B2")) {
            boards[2][3] = Statics.BLUE + "B2  " + Statics.RESET;
            cells[2][3].setBackground(trans(Statics.BLUE));
            cells[2][3].setText("B2");
        } else if (stone.name.equals("C2")) {
            boards[3][2] = Statics.BLUE + "C2  " + Statics.RESET;
            cells[3][2].setBackground(trans(Statics.BLUE));
            cells[3][2].setText("C2");
        } else if (stone.name.equals("D2")) {
            boards[3][3] = Statics.BLUE + "D2  " + Statics.RESET;
            cells[3][3].setBackground(trans(Statics.BLUE));
            cells[3][3].setText("D2");
        }
        if (stone.name.equals("A3")) {
            boards[2][11] = Statics.RED + "A3  " + Statics.RESET;
            cells[2][11].setBackground(trans(Statics.RED));
            cells[2][11].setText("A3");
        } else if (stone.name.equals("B3")) {
            boards[2][12] = Statics.RED + "B3  " + Statics.RESET;
            cells[2][12].setBackground(trans(Statics.RED));
            cells[2][12].setText("B3");

        } else if (stone.name.equals("C3")) {
            boards[3][11] = Statics.RED + "C3  " + Statics.RESET;
            cells[3][11].setBackground(trans(Statics.RED));
            cells[3][11].setText("C3");
        } else if (stone.name.equals("D3")) {
            boards[3][12] = Statics.RED + "D3  " + Statics.RESET;
            cells[3][12].setBackground(trans(Statics.RED));
            cells[3][12].setText("D3");
        }
        if (stone.name.equals("A4")) {
            boards[11][11] = Statics.GREEN + "A4  " + Statics.RESET;
            cells[11][11].setBackground(trans(Statics.GREEN));
            cells[11][11].setText("A4");
        } else if (stone.name.equals("B4")) {
            boards[11][12] = Statics.GREEN + "B4  " + Statics.RESET;
            cells[11][12].setBackground(trans(Statics.GREEN));
            cells[11][12].setText("B4");
        } else if (stone.name.equals("C4")) {
            boards[12][11] = Statics.GREEN + "C4  " + Statics.RESET;
            cells[12][11].setBackground(trans(Statics.GREEN));
            cells[12][11].setText("C4");
        } else if (stone.name.equals("D4")) {
            boards[12][12] = Statics.GREEN + "D4  " + Statics.RESET;
            cells[12][12].setBackground(trans(Statics.GREEN));
            cells[12][12].setText("D4");
        }
    }

    boolean dfs_update(Node node,int cnt,Stone stone){
        //System.out.println(node.x+ " , "+node.y);
        if(stone.is_playing==false){
            if(cnt==6){
                add_stone(stone);
                stone.is_playing=true;
                stone.pos=(stone.EntryBlock+2)%52;
                board.Adj[(stone.EntryBlock+2)%52].stones.add(stone);

                refreshCell(board.Adj[(stone.EntryBlock+2)%52]);
//                System.out.println(node.x+" + "+ node.y+ " , "+(stone.EntryBlock+2)%52);

                return true;
            }else return false;
        }
        if(cnt==0){
            if(node.sheer){
                node.stones.add(stone);
                stone.pos=node.num;
                refreshCell(node);

                return true;
            }
            if(!node.stones.isEmpty() && node.stones.get(0).Idx_Player!= stone.Idx_Player && node.num!=board.trg.num) {
                for (Stone tmp : node.stones) {
                    tmp.is_playing=false;
                    tmp.pos=-1;
                    remove_stone(tmp);
                }
                node.stones.clear();
            }
            node.stones.add(stone);
            stone.pos=node.num;
            if(node.num==board.trg.num){
                stone.is_win=true;
                stone.is_playing=false;
            }
            refreshCell(node);
            return true;
        }
        if(node.ch.isEmpty()){
            return false;
        }
        if(  node.stones.size()>=2&&node.stones.get(0).Idx_Player!=stone.Idx_Player && stone.pos!=node.num && !node.sheer){
            refreshCell(node);

            return false;
        }
        if(node.num==stone.EntryBlock){
            return dfs_update(node.ch.get(1),cnt-1,stone);
        }else {
            return dfs_update(node.ch.get(0),cnt-1,stone);
        }
    }
    boolean dfs_checker(Node node,int cnt,Stone stone){
        if(stone.is_playing==false){
            if(cnt==6){
                return true;
            }else return false;
        }
        if(cnt==0){
            if(node.sheer){
                return true;
            }
            return true;
        }
        if(node.ch.isEmpty()){
            return false;
        }
        if(  node.stones.size()>=2&&node.stones.get(0).Idx_Player!=stone.Idx_Player && stone.pos!=node.num){

            return false;
        }
        if(node.num==stone.EntryBlock){
            return dfs_checker(node.ch.get(1),cnt-1,stone);
        }else {
            return dfs_checker(node.ch.get(0),cnt-1,stone);
        }
    }
    boolean checker(int idx,int val){
        Stone cur=players[curPlayer].stones[idx];
        if(cur.is_win)return false;
        boolean done=dfs_checker(board.Adj[cur.pos>0?cur.pos:1],val,cur);
        return done;
    }

    boolean update(int idx,int val){
        Stone cur=players[curPlayer].stones[idx];
        if(cur.is_win)return false;
        int x=cur.pos;
        boolean done=dfs_update(board.Adj[cur.pos>0?cur.pos:1],val,cur);
        if(done && x!=-1){
//            System.out.println(x);
            board.Adj[x].stones.remove(cur);
            refreshCell(board.Adj[x]);
        }
         return done;
    }

    boolean IsGameOver(){
        boolean w=true;
        for(int i=0;i<4;i++){
            w&=players[curPlayer].stones[i].is_win;
        }
        return w;
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

    double get(List<Integer>ls){
        double prob=1;
        for(int i=0;i<ls.size();i++){
            prob*=1.0/6.0;
        }
        return prob;
    }

    Game AI(int depth,List<Integer>ls,int pl){
        double ans=(curPlayer==pl)?-1e9:1e9;
        Game fin=new Game(this);
            List<Game>mul=apply(ls);
            Set<Game> uniqueSet = new LinkedHashSet<>(mul);

            for (Game cur : uniqueSet) {
                Game tmp=new Game(cur);

                tmp.curPlayer = (curPlayer + 4/Tot) % (Tot==3? 3 : 4);
                double recResult = tmp.rec(depth - 1,-1e9,1e9,pl);
                double tt = get(ls) * recResult;
//                System.out.println("**"+tt);
                if (ans < tt) {
                    fin = new Game(tmp);
                    ans = tt;
                }
            }


        return new Game(fin);
    }



    double rec(int depth, double mx, double mn, int pl) {
        if (depth == 0 || IsGameOver()) {
            return Heuristic(pl);
        }

        double ans = (curPlayer == pl) ? -1e9 : 1e9;

        for (List<Integer> ls : Statics.expectMini) {
            List<Game> mul = apply(ls);
            Set<Game> uniqueSet = new LinkedHashSet<>(mul);

            for (Game cur : uniqueSet) {
                Game tmp = new Game(cur);
                tmp.curPlayer = (curPlayer + 4/Tot) % (Tot==3? 3 : 4);
                double recResult = tmp.rec(depth - 1, mx, mn,pl);
                double tt = get(ls) * recResult;

                if (curPlayer == pl) {
                    ans = Math.max(ans, tt);
                    mx = Math.max(mx, ans);
                } else {
                    ans = Math.min(ans, tt);
                    mn = Math.min(mn, ans);
                }

                if (mn <= mx) {
                    break; // Prune remaining branches
                }
            }
        }
        return ans;
    }

    List<Game> apply(List<Integer> ls) {
        List<Game> lg = new ArrayList<>();
        if (ls.isEmpty()) {
//            System.out.println(this.Heuristic());
            Game cur = new Game(this);
            lg.add(cur);
            return lg;
        }

        List<Integer> lls = new ArrayList<>(ls); // Copy list to avoid mutation
        for (int i = 0; i < 4; i++) {
            // Check if the current stone is playable
//            System.out.println(ls);
            if (lls.get(0)==6||players[curPlayer].stones[i].is_playing) {
//                for (int j = 0; j < lls.size(); j++) {
                    Game nxt = new Game(this); // Create a deep copy of the game
                    int val = lls.get(0);
                    List<Integer> remaining = new ArrayList<>(lls);
                    remaining.remove(0);

                    // Apply the move
                    if(!nxt.checker(i,val)){
                        remaining.add(0,val);
                        continue;
                    }
                    nxt.update(i, val);

                    lg.addAll(nxt.apply(remaining)); // Recursively add further states
                    remaining.add(0,val);
                }
//            }

        }

        return lg;
    }






}
