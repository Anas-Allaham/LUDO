package com.company;

import org.w3c.dom.ls.LSException;

import java.util.*;

public class Game {
    Board board;
    int curPlayer=0;
    Player []players;
    int mode=4;
    String[][] boards = new String[15][15];


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        Game game = (Game) o; // Cast the object
        return curPlayer == game.curPlayer && // Compare primitive field `curPlayer`
                mode == game.mode && // Compare primitive field `mode`
                Objects.equals(board, game.board) && // Compare `board` (nullable object)
                Arrays.equals(players, game.players)&&
                Arrays.equals(boards,game.boards); // Compare `players` array

    }

    @Override
    public int hashCode() {
        int result = Objects.hash(board, curPlayer, mode,boards); // Hash `board`, `curPlayer`, and `mode`
        result = 31 * result + Arrays.hashCode(players); // Add hash of `players` array
        return result;
    }

    Game(){
        players=new Player[4];
        board=new Board();
        init();
    }
    public Game(Game other) {
        // Deep copy the board
        this.board = new Board(other.board);

        // Copy primitive fields
        this.curPlayer = other.curPlayer;
        this.mode = other.mode;

        // Deep copy the players array
        this.players = new Player[other.players.length];
        for (int i = 0; i < other.players.length; i++) {
            this.players[i] = new Player(other.players[i]);
        }
        for(int i=0;i<100;i++){
            if(board.arr[i]==null)continue;
            for(Stone j : other.board.arr[i].stones)
                board.arr[i].stones.add(get_stone(j.Idx_Player,j.name));

        }
        this.boards = new String[15][15];
        for (int i = 0; i < 15; i++) {
            this.boards[i] = Arrays.copyOf(other.boards[i], other.boards[i].length);
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

    // Update boards with colors and formatting
//    System.out.println(node.x+" "+node.y);
    if (node.stones.size() > 0) {
        boards[node.x][node.y] = node.stones.get(0).color + nw + Statics.RESET;
    } else {
        boards[node.x][node.y] = (Statics.MAGENTA) + nw + Statics.RESET;
    }
}

    void add_pawn(Stone stone){
        if(stone.name.equals("A1")){
            boards[11][2]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("B1")){
            boards[11][3]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("C1")){
            boards[12][2]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("D1")){
            boards[12][3]=Statics.WHITE+".   "+Statics.RESET;
        }
        if(stone.name.equals("A2")){
            boards[2][2]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("B2")){
            boards[2][3]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("C2")){
            boards[3][2]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("D2")){
            boards[3][3]=Statics.WHITE+".   "+Statics.RESET;
        }
        if(stone.name.equals("A3")){
            boards[2][11]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("B3")){
            boards[2][12]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("C3")){
            boards[3][11]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("D3")){
            boards[3][12]=Statics.WHITE+".   "+Statics.RESET;
        }  if(stone.name.equals("A4")){
            boards[11][11]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("B4")){
            boards[11][12]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("C4")){
            boards[12][11]=Statics.WHITE+".   "+Statics.RESET;
        }else if(stone.name.equals("D4")){
            boards[12][12]=Statics.WHITE+".   "+Statics.RESET;
        }


    }

    void remove_pawn(Stone stone) {
        if (stone.name.equals("A1")) {
            boards[11][2] = Statics.YELLOW + "A1  " + Statics.RESET;
        } else if (stone.name.equals("B1")) {
            boards[11][3] = Statics.YELLOW + "B1  " + Statics.RESET;
        } else if (stone.name.equals("C1")) {
            boards[12][2] = Statics.YELLOW + "C1  " + Statics.RESET;
        } else if (stone.name.equals("D1")) {
            boards[12][3] = Statics.YELLOW + "D1  " + Statics.RESET;
        }
        if (stone.name.equals("A2")) {
            boards[2][2] = Statics.BLUE + "A2  " + Statics.RESET;
        } else if (stone.name.equals("B2")) {
            boards[2][3] = Statics.BLUE + "B2  " + Statics.RESET;
        } else if (stone.name.equals("C2")) {
            boards[3][2] = Statics.BLUE + "C2  " + Statics.RESET;
        } else if (stone.name.equals("D2")) {
            boards[3][3] = Statics.BLUE + "D2  " + Statics.RESET;
        }
        if (stone.name.equals("A3")) {
            boards[2][11] = Statics.RED + "A3  " + Statics.RESET;
        } else if (stone.name.equals("B3")) {
            boards[2][12] = Statics.RED + "B3  " + Statics.RESET;
        } else if (stone.name.equals("C3")) {
            boards[3][11] = Statics.RED + "C3  " + Statics.RESET;
        } else if (stone.name.equals("D3")) {
            boards[3][12] = Statics.RED + "D3  " + Statics.RESET;
        }
        if (stone.name.equals("A4")) {
            boards[11][11] = Statics.GREEN + "A4  " + Statics.RESET;
        } else if (stone.name.equals("B4")) {
            boards[11][12] = Statics.GREEN + "B4  " + Statics.RESET;
        } else if (stone.name.equals("C4")) {
            boards[12][11] = Statics.GREEN + "C4  " + Statics.RESET;
        } else if (stone.name.equals("D4")) {
            boards[12][12] = Statics.GREEN + "D4  " + Statics.RESET;
        }
    }

    boolean dfs_update(Node node,int cnt,Stone stone){
        //System.out.println(node.x+ " , "+node.y);
        if(stone.is_playing==false){
            if(cnt==6){
                add_pawn(stone);
                stone.is_playing=true;
                stone.pos=(stone.EntryBlock+2)%52;
                board.arr[(stone.EntryBlock+2)%52].stones.add(stone);

                refreshCell(board.arr[(stone.EntryBlock+2)%52]);
//                System.out.println(node.x+" + "+ node.y+ " , "+(stone.EntryBlock+2)%52);

                return true;
            }else return false;
        }
        if(cnt==0){
            if(node.is_safe){
                node.stones.add(stone);
                stone.pos=node.num;
                refreshCell(node);

                return true;
            }
            if(!node.stones.isEmpty() && node.stones.get(0).Idx_Player!= stone.Idx_Player && node.num!=board.target.num) {
                for (Stone tmp : node.stones) {
                    tmp.is_playing=false;
                    tmp.pos=-1;
                    remove_pawn(tmp);
                }
                node.stones.clear();
            }
            node.stones.add(stone);
            stone.pos=node.num;
            if(node.num==board.target.num){
                stone.is_win=true;
                stone.is_playing=false;
            }
            refreshCell(node);
            return true;
        }
        if(node.ch.isEmpty()){
            return false;
        }
        if(  node.stones.size()>=2&&node.stones.get(0).Idx_Player!=stone.Idx_Player && stone.pos!=node.num && !node.is_safe){
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
            if(node.is_safe){
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
        boolean done=dfs_checker(board.arr[cur.pos>0?cur.pos:1],val,cur);
        return done;
    }

    boolean update(int idx,int val){
        Stone cur=players[curPlayer].stones[idx];
        if(cur.is_win)return false;
        int x=cur.pos;
        boolean done=dfs_update(board.arr[cur.pos>0?cur.pos:1],val,cur);
        if(done && x!=-1){
//            System.out.println(x);
            board.arr[x].stones.remove(cur);
            refreshCell(board.arr[x]);
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
        double priority=1e5;
        int cnt=0;
        for(int i=0;i<4;i++){
            cnt+=(players[pl].stones[i].pos>=board.arr[players[pl].stones[i].EntryBlock].ch.get(1).num? 1:0);
        }
        score+=priority*cnt;
        priority/=10;
        cnt=12;
        for(int i=0;i<4;i++){
            if(i==pl)continue;
            for(int j=0;j<4;j++){
                cnt-=players[i].stones[j].is_playing||players[i].stones[j].is_win?1:0;
            }
        }
        score+=cnt*priority;
        priority/=10;
        cnt=0;
        for(int i=0;i<4;i++){
            if(players[pl].stones[i].pos<=0)continue;
            cnt+=(board.arr[players[pl].stones[i].pos].is_safe? 1:0);// null
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
//        System.out.println(ls);
            List<Game>mul=apply(ls);
            Set<Game> uniqueSet = new LinkedHashSet<>(mul);

//            System.out.println("sz"+mul.size());
            for (Game cur : uniqueSet) {
//                System.out.println(cur.players[0].stones[0].pos+" "+cur.players[1].stones[0].pos+" "+Heuristic());
                Game tmp=new Game(cur);
//                LudoBoardConsole lu=new LudoBoardConsole(tmp);
//                lu.drawBoard();
                tmp.curPlayer = (curPlayer + 1) % mode;
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

//    double rec(int depth){
//        if(depth==0 || IsGameOver()){
//
//            return Heuristic();
//        }
//        double ans=(curPlayer==1)?-1e9:1e9;
//
//        for(List<Integer>ls : Statics.expectMini){
//            List<Game>mul=apply(ls);
//            Set<Game> uniqueSet = new LinkedHashSet<>(mul);
//
//            System.out.println(ls.size());
//            for (Game cur : uniqueSet) {
//                Game tmp=new Game(cur);
//                tmp.curPlayer = (curPlayer + 1) % mode;
//                double recResult = tmp.rec(depth - 1);
//                double tt = get(ls) * recResult;
//                System.out.println("**"+tt);
//                if (curPlayer == 1 && ans < tt) {
//                    ans = tt;
//                }
//                if (curPlayer == 0 && ans > tt) {
//                    ans = tt;
//                }
//            }
//
//        }
//        return ans;
//    }

    double rec(int depth, double alpha, double beta,int pl) {
        if (depth == 0 || IsGameOver()) {
            return Heuristic(pl);
        }

        double ans = (curPlayer == pl) ? -1e9 : 1e9;

        for (List<Integer> ls : Statics.expectMini) {
            List<Game> mul = apply(ls);
            Set<Game> uniqueSet = new LinkedHashSet<>(mul);

            for (Game cur : uniqueSet) {
                Game tmp = new Game(cur);
                tmp.curPlayer = (curPlayer + 1) % mode;
                double recResult = tmp.rec(depth - 1, alpha, beta,pl);
                double tt = get(ls) * recResult;

                if (curPlayer == pl) {
                    ans = Math.max(ans, tt);
                    alpha = Math.max(alpha, ans);
                } else {
                    ans = Math.min(ans, tt);
                    beta = Math.min(beta, ans);
                }

                if (beta <= alpha) {
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
//                    nxt.curPlayer = (nxt.curPlayer + 1) % mode;

//                    System.out.println("Adding new game state for stone " + i + " with value " + val+"size :"+ls.size());

//                    lg.add(new Game(nxt)); // Add the updated game state
                    lg.addAll(nxt.apply(remaining)); // Recursively add further states
                    remaining.add(0,val);
                }
//            }

        }

//        System.out.println("Total states generated: " + lg.size());
        return lg;
    }






}
