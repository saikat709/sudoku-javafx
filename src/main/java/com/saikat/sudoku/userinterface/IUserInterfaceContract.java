package com.saikat.sudoku.userinterface;

import com.saikat.sudoku.problemdomain.SudokuGame;

public interface IUserInterfaceContract {
    interface EventListener {
        void onSudokuInput(int x, int y, int value);
        void onDialogButtonClick();
    }

    interface view {
       void setListener(IUserInterfaceContract.EventListener listener);
       void updateSquare(int x, int y, int value);
       void updateBoard(SudokuGame game);
       void showDialog(String message);
       void showError(String message);
    }
}
