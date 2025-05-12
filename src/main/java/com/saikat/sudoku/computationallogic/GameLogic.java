package com.saikat.sudoku.computationallogic;

import com.saikat.sudoku.constants.GameState;
import com.saikat.sudoku.constants.Rows;
import com.saikat.sudoku.problemdomain.SudokuGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {
    public static void copySudokuArrayValues(int[][] oldArr, int[][] newArr){
        for (int i = 0; i < 9; i++) {
            System.arraycopy(oldArr[i], 0, newArr[i], 0, 9);
        }
    }

    public static int[][] copyToNewArray(int[][] oldArr){
        int[][] newArr = new int[9][9];
        copySudokuArrayValues(oldArr, newArr);
        return newArr;
    }

    public static SudokuGame getNewGame(){
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );
    }

    public static GameState checkForComplete(int[][] grid){
        if ( sudokuIsInvalid(grid) ) return GameState.ACTIVE;
        if ( titesAreNotFilled(grid) ) return GameState.ACTIVE;

        return GameState.COMPLETED;
    }

    public static boolean sudokuIsInvalid(int[][] grid){
        if ( rowsAreInvalid(grid) ) return true;
        if ( columnsAreInvalid(grid) ) return true;
        if ( squaresAreInvalid(grid) ) return true;
        return false;
    }

    private static boolean squaresAreInvalid(int[][] grid){
        if (rowOfSquaresIsInvalid(grid, Rows.TOP) ) return true;
        if( rowOfSquaresIsInvalid(grid, Rows.MIDDLE)) return true;
        return rowOfSquaresIsInvalid(grid, Rows.BOTTOM);
    }

    private static boolean rowOfSquaresIsInvalid(int[][] grid, Rows row){
        switch (row){
            case TOP:
                if ( squareIsInvalid(0, 0, grid) ) return false;
                if ( squareIsInvalid(0, 3, grid) ) return false;
                if ( squareIsInvalid(0, 6, grid) ) return false;
                return false;
            case MIDDLE:
                if ( squareIsInvalid(3, 0, grid) ) return false;
                if ( squareIsInvalid(3, 3, grid) ) return false;
                if ( squareIsInvalid(3, 6, grid) ) return false;
                return false;
            case BOTTOM:
                if ( squareIsInvalid(6, 0, grid) ) return false;
                if ( squareIsInvalid(6, 3, grid) ) return false;
                if ( squareIsInvalid(6, 6, grid) ) return false;
                return false;
            default:
                return false;
        }
    }

    private static boolean squareIsInvalid(int x, int y, int[][] grid){
        int yEnd = y + 3;
        int xEnd = x + 3;

        List<Integer> square = new ArrayList<>();

        while( y < yEnd ){
            while( x < xEnd ){
                square.add(grid[y][x]);
                x++;
            }
            y++;
            x = x - 3;
        }

        if ( collectionHasRepeats(square) ) return true;
        return false;
    }

    private static boolean collectionHasRepeats(List<Integer> collection){
        for (int i = 0; i < collection.size(); i++) {
            if (Collections.frequency(collection, collection.get(i)) > 1) return true;
        }
        return false;
    }

    private static boolean columnsAreInvalid(int[][] grid){
        for( int i = 0; i < 9; i++ ){
            List<Integer> column = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                column.add(grid[j][i]);
            }
            if ( collectionHasRepeats(column) ) return true;
        }
        return false;
    }

    private static boolean rowsAreInvalid(int[][] grid){
       for( int y = 0; y < 9; y++ ){
           List<Integer> row = new ArrayList<>();
           for( int x = 0; x < 9; x++ ){
               row.add(grid[x][y]);
           }
           if ( collectionHasRepeats(row)  ) return true;
       }
        return false;
    }

    public static boolean titesAreNotFilled(int[][] grid){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ( grid[i][j] == 0 ) return true;
            }
        }
        return false;
    }

}
