package com.company;


public class LudoBoardConsole {
     Level level;
    private final int BOARD_SIZE = 15;
//    String[][] board;

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String MAGENTA = "\u001B[35m"; 
    private static final String WHITE = "\u001B[37m";
    private static final String NEW = "\u001B[0m";

    void initialize(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                level.grid[i][j] = WHITE+".   "+RESET;
            }
        }
        markSafeZones();
        markBaseZones();
        int st=58;
        for (int i = 0; i <=4; i++) {
            level.grid[i+1][7]=RED+"#   "+RESET;
            level.board.Adj[st+i].x=i+1;
            level.board.Adj[st+i].y=7;
            level.board.Adj[st+i].string="#   ";
        }
        level.grid[1][8]=RED+"#   "+RESET;
        st=53;
        for (int i = 0; i <=4; i++) {
            level.grid[7][i+1]=BLUE+"#   "+RESET;
            level.board.Adj[st+i].x=7;
            level.board.Adj[st+i].y=i+1;
            level.board.Adj[st+i].string="#   ";
        }
        level.grid[6][1]=BLUE+"#   "+RESET;
        st=68+13;
        for (int i = 9; i <14; i++) {
            level.grid[i][7]=YELLOW+"#   "+RESET;
            level.board.Adj[st-i].x=i;
            level.board.Adj[st-i].y=7;
            level.board.Adj[st-i].string="#   ";
        }
        level.grid[13][6]=YELLOW+"#   "+RESET;
        st=63+13;
        for (int i = 9; i <=13; i++) {
            level.grid[7][i]=GREEN+"#   "+RESET;
            level.board.Adj[st-i].x=7;
            level.board.Adj[st-i].y=i;
            level.board.Adj[st-i].string="#   ";
        }
        level.grid[8][13]=GREEN+"#   "+RESET;

        for (int i=6;i<=8;i++){
            for(int j=6;j<=8;j++){
                level.grid[i][j]=NEW+"#   "+RESET;
            }
        }
        // Draw the grid on the console
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(level.grid[i][j]);
            }
            System.out.println();
        }
        func();

    }

    public LudoBoardConsole(Level level) {
        this.level = level;
    }

    public void drawBoard() {
        System.out.println(level.grid.length);

        level.grid[level.board.Adj[1].x][level.board.Adj[1].y]=YELLOW+ level.board.Adj[1].string+RESET;
        level.grid[level.board.Adj[14].x][level.board.Adj[14].y]=BLUE+ level.board.Adj[14].string+RESET;
        level.grid[level.board.Adj[27].x][level.board.Adj[27].y]=RED+ level.board.Adj[27].string+RESET;
        level.grid[level.board.Adj[40].x][level.board.Adj[40].y]=GREEN+ level.board.Adj[40].string+RESET;
        for (int i = 1; i <=5; i++) {
            level.grid[i][7]=RED+ level.board.Adj[58+i-1].string+RESET;
        }

        for (int i = 1; i <=5; i++) {
            level.grid[7][i]=BLUE+ level.board.Adj[53+i-1].string+RESET;
        }

        for (int i = 9; i <=13; i++) {
            level.grid[i][7]=YELLOW+ level.board.Adj[68-i+13].string+RESET;
        }

        for (int i = 9; i <=13; i++) {
            level.grid[7][i]=GREEN+ level.board.Adj[63-i+13].string+RESET;
        }
        level.grid[0][0]=Statics.WHITE+".   "+RESET;

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                Pawn tmp= level.players[i].pawns[j];
                if(tmp.is_win){
                    int position[]=Statics.getStonePosition(tmp.name);
                    level.grid[position[0]][position[1]]="X   ";
                    level.cells[position[0]][position[1]].setText("X");
                }
            }
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(level.grid[i][j]);
            }
            System.out.println();
        }
    }

    private void markSafeZones() {
        // Mark safe zones with magenta 'S'
        for (int i = 6; i <= 8; i++) {
            for (int j = 0; j <= 5*2; j++) {

                level.grid[i][j] = MAGENTA + "#   " + RESET;
            } // Vertical safe zone
            for (int j = 9; j <= 14; j++) level.grid[i][j] = MAGENTA + "#   " + RESET; // Vertical safe zone
        }
        for (int j = 6; j <= 8; j++) {
            for (int i = 0; i <= 5; i++) level.grid[i][j] = MAGENTA + "#   " + RESET; // Horizontal safe zone
            for (int i = 9; i <= 14; i++) level.grid[i][j] = MAGENTA + "#   " + RESET; // Horizontal safe zone
        }
    }

    private void markBaseZones() {
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                Pawn tmp= level.players[i].pawns[j];
                int []pos=Statics.getStonePosition(tmp.name);
                level.grid[pos[0]][pos[1]]=tmp.color+tmp.name+"  "+RESET;
            }
        }
    }
    void func(){
        int lst=25;
        for(int i=0;i<=5;i++){
            lst=updateAdjacency(lst,i,6,"#   ");
        }
        for(int i=5;i>=0;i--){
            lst=updateAdjacency(lst,6,i,"#   ");
        }
        lst=updateAdjacency(lst,7,0,"#   ");
        for(int i=0;i<=5;i++){
            lst=updateAdjacency(lst,8,i,"#   ");
        }
        for(int i=9;i<=13;i++){
            lst=updateAdjacency(lst,i,6,"#   ");
        }
        lst=53;
        lst=updateAdjacency(lst,14,6,"#   ");
        lst=updateAdjacency(lst,14,7,"#   ");
        for(int i=14;i>=9;i--){
            lst=updateAdjacency(lst,i,8,"#   ");
        }
        for(int i=9;i<=14;i++){
            lst=updateAdjacency(lst,8,i,"#   ");
        }
        lst=updateAdjacency(lst,7,14,"#   ");
        for(int i=14;i>=9;i--){
            lst=updateAdjacency(lst,6,i,"#   ");
        }
        for(int i=5;i>=0;i--){
            lst=updateAdjacency(lst,i,8,"#   ");
        }
        lst=updateAdjacency(lst,0,6,"#   ");
        System.out.println(lst);
    }

    int updateAdjacency(int lst, int x, int y, String value) {
        lst--;
        level.board.Adj[lst].x = x;
        level.board.Adj[lst].y = y;
        level.board.Adj[lst].string = value;
        return lst;
    }
}
