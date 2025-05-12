package com.saikat.sudoku.computationallogic;

import com.saikat.sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGenerator {
    public static int[][] getNewGameGrid(){
        return unsolvedGame(getSolvedGame());
    }

    private static int[][] getSolvedGame(){
        Random random = new Random();
        int[][] solvedGame = new int[9][9];
        for (int i = 0; i < 9; i++) {
           int allocations = 0;
           int interrupt = 0;

           List<Coordinates> allocTracker = new ArrayList<>();
           int attempts = 0;

           while( allocations < 9 ){
               if( interrupt > 200 ){
                   allocTracker.forEach(coord -> {
                       solvedGame[coord.getX()][coord.getY()] = 0;
                   });
                   interrupt = 0;
                   allocations = 0;
                   allocTracker.clear();
                   attempts++;
                   if ( attempts > 500 ) {
                       clearArray(solvedGame);
                       attempts = 0;
                       i = 1;
                   }
               }

               int xCoor = random.nextInt(9);
               int yCoor = random.nextInt(9);

               if ( solvedGame[xCoor][yCoor] == 0 ){
                   solvedGame[xCoor][yCoor] = i;

                   if ( GameLogic.sudokuIsInvalid(solvedGame) ){
                       solvedGame[xCoor][yCoor] = 0;
                       interrupt++;
                   } else {
                       allocTracker.add(new Coordinates(xCoor, yCoor));
                       allocations++;
                   }
               }
           }
        }
        return solvedGame;
    }


    private static int[][] unsolvedGame(int[][] solvedGame){
        Random random = new Random(System.currentTimeMillis());
        boolean solvable = true;

        int[][] solvableGame = GameLogic.copyToNewArray(solvedGame);

        while( solvable == false ){
            copySudokuArrayValues(solvedGame, solvableGame);

            int ind = 0;

            while( ind < 40 ){
                int xCo = random.nextInt(9);
                int yCo = random.nextInt(9);

                if ( solvedGame[xCo][yCo] != 0 ){
                    solvableGame[xCo][yCo] = 0;
                    ind++;
                }
            }

            int[][] toBeSolved = new int[9][9];
            copySudokuArrayValues(solvableGame, toBeSolved);

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }

        return solvableGame;
    }

    private static void copySudokuArrayValues(int[][] oldArr, int[][] newArr){
        for (int i = 0; i < 9; i++) {
            System.arraycopy(oldArr[i], 0, newArr[i], 0, 9);
        }
    }

    private static void clearArray(int[][] arr){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                arr[i][j] = 0;
            }
        }
    }

}
