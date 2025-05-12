package com.saikat.sudoku.buildlogic;

import com.saikat.sudoku.computationallogic.GameLogic;
import com.saikat.sudoku.persistance.LocalStorageImpl;
import com.saikat.sudoku.problemdomain.IStorage;
import com.saikat.sudoku.problemdomain.SudokuGame;
import com.saikat.sudoku.userinterface.IUserInterfaceContract;
import com.saikat.sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

public class BuildLogic {

    public static void build(IUserInterfaceContract.view userInterface ) throws IOException{
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {
            initialState = storage.getGameData();
        } catch (IOException e){
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }

        IUserInterfaceContract.EventListener listener = new ControlLogic(storage, userInterface);
        userInterface.setListener(listener);
        userInterface.updateBoard(initialState);
    }
}
