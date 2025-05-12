package com.saikat.sudoku.userinterface.logic;

import com.saikat.sudoku.computationallogic.GameLogic;
import com.saikat.sudoku.constants.GameState;
import com.saikat.sudoku.constants.Messages;
import com.saikat.sudoku.problemdomain.IStorage;
import com.saikat.sudoku.problemdomain.SudokuGame;
import com.saikat.sudoku.userinterface.IUserInterfaceContract;

import java.io.IOException;

public class ControlLogic implements IUserInterfaceContract.EventListener {
    private IStorage storage;
    private IUserInterfaceContract.view view;

    public ControlLogic(IStorage storage, IUserInterfaceContract.view view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onSudokuInput(int x, int y, int value) {
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGameCopy = gameData.getCopyOfGridState();
            newGameCopy[x][y] = value;

            gameData = new SudokuGame(
                    GameLogic.checkForComplete(newGameCopy),
                    newGameCopy
            );

            storage.updateGameData(gameData);
            view.updateSquare(x, y, value);
            if( gameData.getGameState() == GameState.COMPLETED){
                view.showDialog(Messages.GAME_COMPLETED);
            }
        } catch (IOException e){
            e.printStackTrace();
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onDialogButtonClick() {
        try{
            storage.updateGameData(GameLogic.getNewGame());
            view.updateBoard(storage.getGameData());
        } catch (IOException e){
            view.showError(e.getMessage());
        }
    }
}
