package main.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {
    final private int WINNING_MOVES[][][] = {
            {{1, 1}, {0, 0}, {2, 2}}, // left diagonal
            {{1, 1}, {0, 2}, {2, 0}}, // right diagonal
            {{0, 0}, {0, 1}, {0, 2}}, // top horizontal
            {{1, 1}, {1, 0}, {1, 2}}, // mid horizontal
            {{2, 0}, {2, 1}, {2, 2}}, // bot horizontal
            {{0, 0}, {1, 0}, {2, 0}}, // left vertical
            {{1, 1}, {0, 1}, {2, 1}}, // mid vertical
            {{0, 2}, {1, 2}, {2, 2}}, // right vertical
    };
    private int[][] p1Moves, p2Moves;
    private int turn = 1;

    public TicTacToe() {
        p1Moves = new int[3][3];
        p2Moves = new int[3][3];
    }

    public void move(int row, int column) {
        if(turn % 2 == 1) p1Moves[row][column] = 1;
        else p2Moves[row][column] = 1;
        turn++;
    }

    /**
     * Determines whether there's a winner already or not
     * @param moves 2 dimensional 3x3 grid which determines the moves made by a player
     * @return whether or not the player with that moves has won or not
     */
    public boolean hasWon(int[][] moves) {
        // loops the winning moves
        for(int winningMove[][] : WINNING_MOVES) {
            int total = 0;
            // accumulate the moves made in that winning move
            for(int move[] : winningMove) total += moves[move[0]][move[1]];
            // if all move was made in that winning move, then the player has won
            if(total == 3) return true;
        }

        // if not, the player hasn't won yet
        return false;
    }

    /**
     * Determines whether or not the game is a draw or not
     * @return boolean value to determine if game is draw or not
     */
    public boolean isDraw() {
        if(!hasWon(p1Moves) && !hasWon(p2Moves) && turn >= 10) return true;
        return false;
    }

    public int[][] getP1Moves() {
        return p1Moves;
    }

    public int[][] getP2Moves() {
        return p2Moves;
    }

    public int getTurn() {
        return turn;
    }

    /**
     * Level 0:
     *     Lets AI choose a random move based on the available moves
     */
    public void randomMove() {
        Random randomizer = new Random();
        int randomMove = randomizer.nextInt(getAvailableMoves().size());
        move(getAvailableMoves().get(randomMove)[0], getAvailableMoves().get(randomMove)[1]);
    }

    /**
     * Level 1 Smart:
     *      the agent uses a hard-coded table that generates a move for every possible state/configuration;
     *      note that since there is a very large number (9!) of possible configurations, the “hard-coded table of moves”
     *      can make generalizations, take advantage of symmetries, perform some kind of clustering of configurations,
     *      and the like.
     *
     */
    public void smartOne() {
        /* Algorithm:
             1. check number of moves opponent needs to complete any single winning move
             2. check number of moves AI needs to complete any single winning move
             3. do the move which needs the lower number of moves to complete any single winning move
             4. for backup, if moveToStop and moveToWin is both equal to -1, just do a random move
        */

        /* Main Concept:
              is to check the number of moves to complete any single winning move and stop that
              move if its the opponent's, but choose that move if its the AI's
        */

        int moveToStopOpponent[] = completeMove(p1Moves, p2Moves);
        int moveToWin[] = completeMove(p2Moves, p1Moves);

        // NOTE: move to be done is determined by the number of moves
        //       left for each player has to make to win

        // System.out.println("MOVES TO STOP OPPONENT:" + moveToStopOpponent[0] + " | MOVES TO WIN:" + moveToWin[0]);

        System.out.println(moveToStopOpponent[0] + " " + moveToWin[0]);
        // if move wasn't made yet, do a random move
        if(moveToStopOpponent[0] == -1 && moveToWin[0] == -1) randomMove();
        // do move to stop opponent
        else if(moveToStopOpponent[0] > moveToWin[0]) move(moveToStopOpponent[1], moveToStopOpponent[2]);
        // do move to win
        else move(moveToWin[1], moveToWin[2]);
    }

    /**
     * Level 2 Smart: Minimax
     *     the agent uses a search strategy to find the “best” move given the current configuration,
     *     using some simple heuristics, for example.
     */
    public void smartTwo() {
        final int MAX_DEPTH = 12;
        int[] bestMove = new int[2];
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (isMoveAvailable(row, col)) {
                    p2Moves[row][col] = 1;
                    int currValue = minimax(MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    if (currValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = currValue;
                    }
                    p2Moves[row][col] = 0;
                }
            }
        }
        move(bestMove[0], bestMove[1]);
    }

    /**
     * Returns a list of all available moves
     * @return arraylist of all available moves
     */
    private ArrayList<int[]> getAvailableMoves() {
        ArrayList<int[]> availableMoves = new ArrayList<int[]>();
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                if(getP1Moves()[row][col] != 1 && getP2Moves()[row][col] != 1) {
                    int available[] = new int[2];
                    available[0] = row;
                    available[1] = col;
                    availableMoves.add(available);
                }
            }
        }

        return availableMoves;
    }

    /**
     * Checks whether a specific move is available or not
     * @param x row of move
     * @param y column of move
     * @return boolean value determining whether move is available or not
     */
    private boolean isMoveAvailable(int x, int y) {
        ArrayList<int[]> availableMoves = getAvailableMoves();
        for(int[] move : availableMoves) {
            if(x == move[0] && y == move[1]) return true;
        }
        return false;
    }

    /**
     * Returns the best move to do to complete a winning move
     * @param moves 3x3 grid representing the move done by a player
     * @return 3-element array where
     *      index 0: move left to do to complete that winning move
     *      index 1: row position for player to move
     *      index 2: column position for player to move
     */
    private int[] completeMove(int moves[][], int opponentMoves[][]) {
        // number of moves made for winning move, x and y move to make to complete winning move
        int moveToMake[] = new int[3];
        // per winning move, check if there is a move that a player can do to win
        for(int winningMove[][] : WINNING_MOVES) {
            int movesToComplete = 0, opponentMovesToComplete = 0;
            for(int move[] : winningMove) movesToComplete += moves[move[0]][move[1]];
            for(int move[] : winningMove) opponentMovesToComplete += opponentMoves[move[0]][move[1]];
//            System.out.print(movesToComplete + " ");
            if(movesToComplete > moveToMake[0] && opponentMovesToComplete == 0) {
                // get the move that wasn't done by the player
                int moveIndex = 0;
                while((moves[winningMove[moveIndex][0]][winningMove[moveIndex][1]] == 1 || !isMoveAvailable(winningMove[moveIndex][0], winningMove[moveIndex][1])) &&  moveIndex < 2) moveIndex++;
                // if any move to complete a move is available, set that to be the move to be done
                if(isMoveAvailable(winningMove[moveIndex][0], winningMove[moveIndex][1])) {
                    moveToMake[0] = movesToComplete;
                    moveToMake[1] = winningMove[moveIndex][0];
                    moveToMake[2] = winningMove[moveIndex][1];
                }
            }
        }
//        System.out.println();
        return moveToMake;
    }

    /**
     * Minimax with Alpha-Beta Pruning function
     * Max - P2 (AI)
     * Min - P1 (Player)
     * @param depth maximum depth for search tree to search
     * @param alpha alpha for alpha beta pruning
     * @param beta  beta for alpha beta pruning
     * @param isMaximum determines whether to get the maximum or minimum value
     * @return maximized heuristic value
     */
    public int minimax(int depth, int alpha, int beta, boolean isMaximum) {
        int value = heuristic(p1Moves, p2Moves);
        if(Math.abs(value) == 10 || depth == 0 || getAvailableMoves().size() == 0) return value;

        // Maximizing player, find the maximum attainable value.
        if(isMaximum) {
            int biggest = Integer.MIN_VALUE;
            for(int row = 0; row < 3; row++) {
                for(int col = 0; col < 3; col++) {
                    if(isMoveAvailable(row, col)) {
                        p2Moves[row][col] = 1;
                        biggest = Math.max(biggest, minimax(depth - 1, alpha, beta, false));
                        p2Moves[row][col] = 0;
                        alpha = Math.max(alpha, biggest);
                        if(alpha >= beta) return biggest;
                    }
                }
            }
            return biggest;
        } else {
            // Minimizing player, find the minimum attainable value
            int lowest = Integer.MAX_VALUE;
            for(int row = 0; row < 3; row++) {
                for(int col = 0; col < 3; col++) {
                    if(isMoveAvailable(row, col)) {
                        p1Moves[row][col] = 1;
                        lowest = Math.min(lowest, minimax(depth - 1, alpha, beta, true));
                        p1Moves[row][col] = 0;
                        beta = Math.min(beta, lowest);
                        if(beta <= alpha) return lowest;
                    }
                }
            }
            return lowest;
        }
    }

    /**
     * if p1 has a winning move, return -10, if p2 has a winning move return 10
     * if not, return the number of moves p1 can make to win - number of moves p2 can make to win
     * NOTE: you want p2 to win here since p2 is the AI
     * @return heuristic value
     */
    public int heuristic(int[][] p1Moves, int[][] p2Moves) {
        int p1NMoves = 0, p2NMoves = 0;

        // Get the number of winning moves each player can make
        for(int[][] winningMoves : WINNING_MOVES) {
            int p1Count = 0, p2Count = 0;
            // checks if p1 "can" win on current move
            for(int move[] : winningMoves) p1Count += p1Moves[move[0]][move[1]];

            // checks if p2 "can" win on current move
            for(int move[] : winningMoves) p2Count += p2Moves[move[0]][move[1]];

            // only count that certain move if the other player doesn't have a move on it
            // 3 - count means the number of moves left to make
            if(p1Count > 0 && p2Count == 0) {
                // return -10 if p1 has a winning move
                if(p1Count == 3) return -10;
                else p1NMoves += 3 - p1Count;
            }
            if(p2Count > 0 && p1Count == 0) {
                // returns 10 if p2 has a winning move
                if(p2Count == 3) return 10;
                else p2NMoves += 3 - p2Count;
            }
        }
        return p2NMoves - p1NMoves;
    }

    /**
     * Combines the p1 and p2 moves to 1 tic tac toe board
     * @return 3x3 board representing 0 for p1 moves and 1 for p2 moves
     */
    public int[][] getBoard() {
        int[][] board = new int[][]{{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                if(p1Moves[row][col] == 1) board[row][col] = 0;
            }
        }
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                if(p2Moves[row][col] == 1) board[row][col] = 1;
            }
        }
        return board;
    }
}
