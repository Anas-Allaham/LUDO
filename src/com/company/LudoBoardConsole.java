package com.company;


public class LudoBoardConsole {
     Game game;
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
    private static final String NEW = "\u001B[40m";

    void init(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                game.boards[i][j] = WHITE+".   "+RESET;
            }
        }
        markSafeZones();
        markBaseZones();
        for (int i = 1; i <=5; i++) {
            game.boards[i][7]=RED+"#   "+RESET;
            game.board.Adj[58+i-1].x=i;
            game.board.Adj[58+i-1].y=7;
            game.board.Adj[58+i-1].string="#   ";

        }
        game.boards[1][8]=RED+"#   "+RESET;

        for (int i = 1; i <=5; i++) {
            game.boards[7][i]=BLUE+"#   "+RESET;
            game.board.Adj[53+i-1].x=7;
            game.board.Adj[53+i-1].y=i;
            game.board.Adj[53+i-1].string="#   ";

        }
        game.boards[6][1]=BLUE+"#   "+RESET;

        for (int i = 9; i <=13; i++) {
            game.boards[i][7]=YELLOW+"#   "+RESET;
            game.board.Adj[68-i+13].x=i;
            game.board.Adj[68-i+13].y=7;
            game.board.Adj[68-i+13].string="#   ";

        }
        game.boards[13][6]=YELLOW+"#   "+RESET;

        for (int i = 9; i <=13; i++) {
            game.boards[7][i]=GREEN+"#   "+RESET;
            game.board.Adj[63-i+13].x=7;
            game.board.Adj[63-i+13].y=i;
            game.board.Adj[63-i+13].string="#   ";
        }
        game.boards[8][13]=GREEN+"#   "+RESET;

        for (int i=6;i<=8;i++){
            for(int j=6;j<=8;j++){
                game.boards[i][j]=NEW+"#   "+RESET;
            }
        }
        // Draw the boards on the console
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(game.boards[i][j]);
            }
            System.out.println();
        }
        thhhh();

    }

    public LudoBoardConsole(Game game) {
        this.game = game;
    }

    public void drawBoard() {
        System.out.println(game.boards.length);

        game.boards[game.board.Adj[1].x][game.board.Adj[1].y]=YELLOW+game.board.Adj[1].string+RESET;
        game.boards[game.board.Adj[14].x][game.board.Adj[14].y]=BLUE+game.board.Adj[14].string+RESET;
        game.boards[game.board.Adj[27].x][game.board.Adj[27].y]=RED+game.board.Adj[27].string+RESET;
        game.boards[game.board.Adj[40].x][game.board.Adj[40].y]=GREEN+game.board.Adj[40].string+RESET;
        for (int i = 1; i <=5; i++) {
            game.boards[i][7]=RED+game.board.Adj[58+i-1].string+RESET;
        }

        for (int i = 1; i <=5; i++) {
            game.boards[7][i]=BLUE+game.board.Adj[53+i-1].string+RESET;
        }

        for (int i = 9; i <=13; i++) {
            game.boards[i][7]=YELLOW+game.board.Adj[68-i+13].string+RESET;
        }

        for (int i = 9; i <=13; i++) {
            game.boards[7][i]=GREEN+game.board.Adj[63-i+13].string+RESET;
        }
        game.boards[0][0]=Statics.WHITE+".   "+RESET;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(game.boards[i][j]);

            }
            System.out.println();
        }


    }

    private void markSafeZones() {
        // Mark safe zones with magenta 'S'
        for (int i = 6; i <= 8; i++) {
            for (int j = 0; j <= 5*2; j++) {

                game.boards[i][j] = MAGENTA + "#   " + RESET;
            } // Vertical safe zone
            for (int j = 9; j <= 14; j++) game.boards[i][j] = MAGENTA + "#   " + RESET; // Vertical safe zone
        }
        for (int j = 6; j <= 8; j++) {
            for (int i = 0; i <= 5; i++) game.boards[i][j] = MAGENTA + "#   " + RESET; // Horizontal safe zone
            for (int i = 9; i <= 14; i++) game.boards[i][j] = MAGENTA + "#   " + RESET; // Horizontal safe zone
        }
    }

    private void markBaseZones() {
        // Mark player bases with corners as 'T' and center cells as 'P'
//

        // Mark the center cells of each corner with 'P' for pawns
        game.boards[2][2] = BLUE + "A2  " + RESET;
        game.boards[2][3] = BLUE + "B2  " + RESET;
        game.boards[3][2] = BLUE + "C2  " + RESET;
        game.boards[3][3] = BLUE + "D2  " + RESET;
        game.boards[2][11] = RED + "A3  " + RESET;
        game.boards[2][12] = RED + "B3  " + RESET;
        game.boards[3][11] = RED + "C3  " + RESET;
        game.boards[3][12] = RED + "D3  " + RESET;
        game.boards[11][2] = YELLOW + "A1  " + RESET;
        game.boards[11][3] = YELLOW + "B1  " + RESET;
        game.boards[12][2] = YELLOW + "C1  " + RESET;
        game.boards[12][3] = YELLOW + "D1  " + RESET;
        game.boards[11][11] = GREEN + "A4  " + RESET;
        game.boards[11][12] = GREEN + "B4  " + RESET;
        game.boards[12][11] = GREEN + "C4  " + RESET;
        game.boards[12][12] = GREEN + "D4  " + RESET;
    }

    public static void main(String[] args) {
        Game game = new Game(); // Initialize your game with players and stones
        LudoBoardConsole consoleBoard = new LudoBoardConsole(game);

        System.out.println("Ludo Board:");
        consoleBoard.drawBoard();
    }
    void thhhh(){
        int lst=25;
        for(int i=0;i<=5;i++){
            lst--;
            game.board.Adj[lst].y=6;
            game.board.Adj[lst].x=i;
            game.board.Adj[lst].string="#   ";

        }
        for(int i=5;i>=0;i--){
            lst--;
            game.board.Adj[lst].y=i;
            game.board.Adj[lst].x=6;
            game.board.Adj[lst].string="#   ";

        }
        lst--;
        game.board.Adj[lst].y=0;
        game.board.Adj[lst].x=7;
        game.board.Adj[lst].string="#   ";
        for(int i=0;i<=5;i++){
            lst--;
            game.board.Adj[lst].y=i;
            game.board.Adj[lst].x=8;
            game.board.Adj[lst].string="#   ";

        }
        for(int i=9;i<=13;i++){
            lst--;
            game.board.Adj[lst].y=6;
            game.board.Adj[lst].x=i;
            game.board.Adj[lst].string="#   ";

        }
        lst=52;
        game.board.Adj[lst].y=6;
        game.board.Adj[lst].x=14;
        game.board.Adj[lst].string="#   ";

        lst--;
        game.board.Adj[lst].y=7;
        game.board.Adj[lst].x=14;
        game.board.Adj[lst].string="#   ";

        for(int i=14;i>=9;i--){
            lst--;
            game.board.Adj[lst].y=8;
            game.board.Adj[lst].x=i;
            game.board.Adj[lst].string="#   ";

        }

        for(int i=9;i<=14;i++){
            lst--;
            game.board.Adj[lst].y=i;
            game.board.Adj[lst].x=8;
            game.board.Adj[lst].string="#   ";

        }
        lst--;
        game.board.Adj[lst].y=14;
        game.board.Adj[lst].x=7;
        game.board.Adj[lst].string="#   ";

        for(int i=14;i>=9;i--){
            lst--;
            game.board.Adj[lst].y=i;
            game.board.Adj[lst].x=6;
            game.board.Adj[lst].string="#   ";

        }
        for(int i=5;i>=0;i--){
            lst--;
            game.board.Adj[lst].y=8;
            game.board.Adj[lst].x=i;
            game.board.Adj[lst].string="#   ";

        }
        lst--;
        game.board.Adj[lst].y=7;
        game.board.Adj[lst].x=0;
        game.board.Adj[lst].string="#   ";

        System.out.println(lst);
    }
}
