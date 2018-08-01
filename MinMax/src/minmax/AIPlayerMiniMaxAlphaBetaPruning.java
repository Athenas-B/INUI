package minmax;

import java.util.*;

/**
 * AIPlayer using Minimax algorithm
 */
public class AIPlayerMiniMaxAlphaBetaPruning extends AIPlayer {

    /**
     * Constructor with the given game board
     *
     * @param board
     */
    public AIPlayerMiniMaxAlphaBetaPruning(Board board) {
        super(board);
    }

    /**
     * Constructor with the given game board
     *
     * @param board
     * @param depth
     */
    public AIPlayerMiniMaxAlphaBetaPruning(Board board, int depth) {
        super(board);
        this.depth = depth;
    }

    /**
     * Get next best move for computer. Return int[2] of {row, col}
     */
    @Override
    int[] move() {
        int[] result = minimax(depth, mySeed, Integer.MIN_VALUE, Integer.MAX_VALUE);
        // depth, max-turn, alpha, beta
        return new int[]{result[1], result[2]};   // row, col
    }

    /**
     * Minimax (recursive) at level of depth for maximizing or minimizing player
     * with alpha-beta cut-off. Return int[3] of {score, row, col}
     */
    private int[] minimax(int depth, Seed player, int alpha, int beta) {
        // Generate possible next moves in a list of int[2] of {row, col}.
        List<int[]> nextMoves = generateMoves();

        // mySeed is maximizing; while oppSeed is minimizing
        int score;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            score = evaluate();
            return new int[]{score, bestRow, bestCol};
        } else {
            for (int[] move : nextMoves) {
                // try this move for the current "player"
                cells[move[0]][move[1]].content = player;
                if (player == mySeed) {  // mySeed (computer) is maximizing player
                    score = minimax(depth - 1, oppSeed, alpha, beta)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // oppSeed is minimizing player
                    score = minimax(depth - 1, mySeed, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // undo move
                cells[move[0]][move[1]].content = Seed.EMPTY;
                // cut-off
                if (alpha >= beta) {
                    break;
                }
            }
            return new int[]{(player == mySeed) ? alpha : beta, bestRow, bestCol};
        }
    }

    /**
     * Find all valid next moves. Return List of moves in int[2] of {row, col}
     * or empty list if gameover
     */
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List

        // If gameover, i.e., no next move
        if (hasWon(mySeed) || hasWon(oppSeed)) {
            return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.EMPTY) {
                    nextMoves.add(new int[]{row, col});
                }
            }
        }
        return nextMoves;
    }

    /**
     * The heuristic evaluation function for the current board
     *
     * @Return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer. -100,
     * -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent. 0 otherwise
     */
    private int evaluate() {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    /**
     * The heuristic evaluation function for the given line of 3 cells
     *
     * @Return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer. -100, -10, -1
     * for 3-, 2-, 1-in-a-line for opponent. 0 otherwise
     */
    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        Seed[] pole = {cells[row1][col1].content, cells[row2][col2].content, cells[row3][col3].content};
        int my = 0;
        int opp = 0;
        for (Seed seed : pole) {
            if (seed == mySeed) {
                my++;

            }
            if (seed == oppSeed) {
                opp++;
            }
        }
//        if (my > 0 && opp > 0) {
//            return 0;
//        } else if (my > 0) {
//            return my;
//        } else if (opp > 0) {
//            return -opp;
//        }
//        if (my > opp) {
//            return my;
//        } else {
//            return -opp;
//        }

        if (my > 0) {
            if (opp > 0) {
                return 0;
            }
        }
        if (my > opp) {
            return (int) Math.pow(10, my);
        } else {
            return (int) Math.pow(10, opp) * -1;
        }

        // return 0;
    }

    private int[] winningPatterns = {
        0b111000000, 0b000111000, 0b000000111, // rows
        0b100100100, 0b010010010, 0b001001001, // cols
        0b100010001, 0b001010100 // diagonals
    };

    /**
     * Returns true if thePlayer wins
     */
    private boolean hasWon(Seed thePlayer) {
        int pattern = 0b000000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == thePlayer) {
                    pattern |= (1 << (row * COLS + col));
                }
            }
        }
        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) {
                return true;
            }
        }
        return false;
    }
}
