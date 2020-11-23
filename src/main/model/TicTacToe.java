package main.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {
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
        // horizontals
        if(moves[0][0] == 1 && moves[0][1] == 1 && moves[0][2] == 1) return true;
        if(moves[1][0] == 1 && moves[1][1] == 1 && moves[1][2] == 1) return true;
        if(moves[2][0] == 1 && moves[2][1] == 1 && moves[2][2] == 1) return true;

        // verticals
        if(moves[0][0] == 1 && moves[1][0] == 1 && moves[2][0] == 1) return true;
        if(moves[0][1] == 1 && moves[1][1] == 1 && moves[2][1] == 1) return true;
        if(moves[0][2] == 1 && moves[1][2] == 1 && moves[2][2] == 1) return true;

        // diagonals
        if(moves[0][0] == 1 && moves[1][1] == 1 && moves[2][2] == 1) return true;
        if(moves[2][0] == 1 && moves[1][1] == 1 && moves[0][2] == 1) return true;

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
     * Lets AI choose a random move based on the available moves
     */
    public void randomMove() {
        Random randomizer = new Random();
        int randomMove = randomizer.nextInt(getAvailableMoves().size());
        move(getAvailableMoves().get(randomMove)[0], getAvailableMoves().get(randomMove)[1]);
    }

    public void smartMove() {

    }

    private ArrayList<int[]> getAvailableMoves() {
        ArrayList<int[]> availableMoves = new ArrayList<int[]>();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(getP1Moves()[i][j] != 1 && getP2Moves()[i][j] != 1) {
                    int available[] = new int[2];
                    available[0] = i;
                    available[1] = j;
                    availableMoves.add(available);
                }
            }
        }

        return availableMoves;
    }
}
