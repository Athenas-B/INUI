package minmax;

import java.util.Scanner;

public class GameMain {
    // Named-constants for the game board

    public static final int ROWS = 3;  // ROWS by COLS cells
    public static final int COLS = 3;

    private final Board board;            // the game board
    private GameState currentState; // the current state of the game (of enum GameState)
    private Seed currentPlayer;     // the current player (of enum Seed)
    private final AIPlayer minimaxpAIPlayer;

    /**
     * Constructor to setup the game
     */
    public GameMain(int choice) {
        board = new Board();  // allocate game-board
        minimaxpAIPlayer = new AIPlayerMiniMaxAlphaBetaPruning(board, 9);
        chooseGame(choice);
        do {

            computerMove(currentPlayer); // update the content, currentRow and currentCol

            board.paint();             // ask the board to paint itself
            updateGame(currentPlayer); // update currentState
            

            // Switch player
            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
        } while (currentState == GameState.PLAYING);  // repeat until game-over
        System.out.println(currentState);
    }

    public int chooseGame(int choice) {

        switch (choice) {
            case 1:
                initGame();
                break;
            case 2:
                initGame2();
                break;
            case 3:
                initGame3();
                break;
            case 4:
                initGame4();
                break;
            case 5:
                initGame5();
                break;
            default:
                initGame();
                break;
        }
        return choice;
    }

    /**
     * Initialize the game-board contents and the current states Empty gameboard
     */
    public void initGame() {
        board.init();  // clear the board contents
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }

    /**
     * Initialize the game-board contents and the current states Gameboard
     * initializate to state two
     */
    public void initGame2() {
        write(1, 1, Seed.CROSS);
        write(2, 3, Seed.CROSS);
        write(1, 2, Seed.NOUGHT);
        write(2, 2, Seed.NOUGHT);
        board.paint();             // ask the board to paint itself
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }

    /**
     * Initialize the game-board contents and the current states Gameboard
     * initializate to state three
     */
    public void initGame3() {
        write(1, 1, Seed.NOUGHT);
        write(2, 3, Seed.NOUGHT);
        write(1, 2, Seed.CROSS);
        write(2, 1, Seed.CROSS);
        write(3, 2, Seed.CROSS);
        write(2, 2, Seed.NOUGHT);
        board.paint();             // ask the board to paint itself
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }

    /**
     * Initialize the game-board contents and the current states Gameboard
     * initializate to state four
     */
    public void initGame4() {
        write(1, 1, Seed.CROSS);
        write(1, 2, Seed.NOUGHT);
        write(2, 2, Seed.CROSS);
        write(3, 3, Seed.NOUGHT);
        board.paint();             // ask the board to paint itself
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }

    /**
     * Initialize the game-board contents and the current states Gameboard
     * initializate to state four
     */
    public void initGame5() {
        write(1, 1, Seed.NOUGHT);
        write(1, 3, Seed.NOUGHT);
        write(2, 2, Seed.NOUGHT);
        write(2, 1, Seed.CROSS);
        write(2, 3, Seed.CROSS);
        write(3, 1, Seed.CROSS);
        board.paint();             // ask the board to paint itself
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }

    /**
     * The player with "theSeed" makes one move, with input validation. Update
     * Cell's content, Board's currentRow and currentCol.
     */
    /**
     * The player with "theSeed" makes one move, with input validation. Update
     * Cell's content, Board's currentRow and currentCol.
     */
    public void computerMove(Seed theSeed) {
        boolean validInput = false;  // for validating input
        do {
            System.out.println("\n");
            int[] move = minimaxpAIPlayer.move();
            int row = move[0];
            int col = move[1];
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                    && board.cells[row][col].content == Seed.EMPTY) {
                board.cells[row][col].content = theSeed;
                board.currentRow = row;
                board.currentCol = col;
                validInput = true; // input okay, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);   // repeat until input is valid
    }

    /**
     * Update the currentState after the player with "theSeed" has moved
     */
    public void updateGame(Seed theSeed) {
        if (board.hasWon(theSeed)) {  // check for win
            currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (board.isDraw()) {  // check for draw
            currentState = GameState.DRAW;
        }
        // Otherwise, no change to current state (still GameState.PLAYING).
    }

    private void write(int row, int col, Seed theSeed) {
        board.cells[row - 1][col - 1].content = theSeed;
    }

    /**
     * The entry main() method
     */
    public static void main(String[] args) {
            System.out.println("******************************************");
            //System.out.println("Hra cislo " + i + ":");
            new GameMain(1);
            System.out.println("******************************************");
        
    }
}
