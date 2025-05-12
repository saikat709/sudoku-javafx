package com.saikat.sudoku.problemdomain;

import com.saikat.sudoku.constants.GameState;

import java.io.Serializable;

public class SudokuGame implements Serializable  {
    private final GameState gameState;
    private final int[][] gridState = new int[9][9];

    public static final int GRID_BOUNDARY = 9;

    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        for (int i = 0; i < 9; i++) {
            System.arraycopy(gridState[i], 0, this.gridState[i], 0, 9);
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getCopyOfGridState() {
        return gridState.clone();
    }
}
