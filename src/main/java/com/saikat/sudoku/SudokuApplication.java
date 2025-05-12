package com.saikat.sudoku;

import com.saikat.sudoku.userinterface.IUserInterfaceContract;
import com.saikat.sudoku.userinterface.UserInterface;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        IUserInterfaceContract.view uiImpl = new UserInterface(stage);
        // BuildLogic.build(uiImpl);
    }

    public static void main(String[] args) {
        launch();
    }
}