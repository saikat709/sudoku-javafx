package com.saikat.sudoku.userinterface;

import com.saikat.sudoku.constants.GameState;
import com.saikat.sudoku.problemdomain.Coordinates;
import com.saikat.sudoku.problemdomain.SudokuGame;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class UserInterface implements  IUserInterfaceContract.view, EventHandler<KeyEvent> {

    private final Stage stage;
    private final Group group;

    private final SudokuTextField[][] textFields = new SudokuTextField[9][9];

    private HashMap<Coordinates, SudokuTextField> textFieldMap = new HashMap<>();
    private IUserInterfaceContract.EventListener listener;

    private static final double WINDOW_X = 732;
    private static final double WINDOW_Y = 732;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 576;
    
    // colors
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "Sudoku";


    public UserInterface(Stage stage) {
        System.out.println("User interface Constructed  !");
        this.stage = stage;
        this.group = new Group();
        this.textFieldMap = new HashMap<>();

        this.stage.setMinWidth(WINDOW_X);
        this.stage.setMinHeight(WINDOW_Y);

        initializeUserInterface();
    }


    private void initializeUserInterface(){
        drawBackground(group);
        drawTitle(group);
        drawGridLines(group);
        drawBoard(group);
        drawTextFields(group);
        stage.show();
        System.out.println("User interface initialized!");
    }

    private void drawBackground(Group group){
        Scene scene = new Scene(group, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    private void drawGridLines(Group group){
        int xAndY = (int) 114;
        int index = 0;

        while( index < 9 ){
            int thickness = 5;
            if ( index == 2 || index == 5 ){
                thickness = 3;
            }  else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(xAndY + index * 64, BOARD_PADDING, BOARD_X_AND_Y,  thickness );
            Rectangle horizontalLine = getLine( BOARD_PADDING,  xAndY + index * 100,  BOARD_X_AND_Y, thickness );

            // group.getChildren().addAll(verticalLine, horizontalLine);
            index++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setFill(Color.ALICEBLUE);

        return rectangle;
    }

    private void drawTextFields(Group group){
        final int xOrigin = 50;
        final int yOrigin = 50;
        final int xAndYDelta = 64;

        for( int i = 0; i < 9; i++ ){
            for( int j = 0; j < 9; j++ ){
                int x = xOrigin + j * xAndYDelta;
                int y = yOrigin + i * xAndYDelta;
                SudokuTextField textField = new SudokuTextField(i, j);
                Font font = new Font(32);
                textField.setFont(font);
                textField.setLayoutX(x);
                textField.setLayoutY(y);
                textField.setPrefWidth(xAndYDelta);
                textField.setPrefHeight(xAndYDelta);
                textField.setOnKeyPressed(this);
                textFieldMap.put(new Coordinates(i, j), textField);
                group.getChildren().add(textField);
            }
        }
    }

    private void drawBoard(Group group){
        Rectangle board = new Rectangle();
        board.setX(BOARD_PADDING);
        board.setY(BOARD_PADDING);
        board.setWidth(BOARD_X_AND_Y);
        board.setHeight(BOARD_X_AND_Y);
        board.setFill(BOARD_BACKGROUND_COLOR);
        group.getChildren().add(board);
    }

    private void drawTitle(Group group){
        Text title = new Text(SUDOKU);
        title.setFont(new Font(43));
        title.setFill(Color.WHITE);
        title.setLayoutX(WINDOW_X / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setLayoutY(BOARD_PADDING - 50);
        group.getChildren().add(title);
    }



    @Override
    public void handle(KeyEvent keyEvent) {
        if ( keyEvent.getEventType() == KeyEvent.KEY_TYPED ){
            if ( keyEvent.getText().matches("[0-9]") ){
                int value = Integer.parseInt(keyEvent.getText());
                handleInput(value, keyEvent.getSource());
            } else if ( keyEvent.getCode() == KeyCode.BACK_SPACE ) {
                handleInput(0, keyEvent.getSource());
                ((TextField) keyEvent.getSource()).setText("");
            } else {
                ((TextField) keyEvent.getSource()).setText("");
            }
        }
    }

    private void handleInput(int value, Object source){
        SudokuTextField textField = (SudokuTextField) source;
        listener.onSudokuInput(textField.getX(), textField.getY(), value);
    }

    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {

    }

    @Override
    public void updateSquare(int x, int y, int value) {
        SudokuTextField textField = textFieldMap.get(new Coordinates(x, y));
        String text = value == 0 ? "" : Integer.toString(value);
        textField.textProperty().setValue(text);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = textFieldMap.get(new Coordinates(i, j));
                String value = Integer.toString(game.getCopyOfGridState()[i][j]);
                if ( "0".equals(value) ) value = "";
                textField.setText(value);
                if ( game.getGameState() == GameState.NEW ){
                    if (value.isEmpty()){
                        textField.setStyle("-fx-opacity: 1;");
                        textField.setDisable(false);
                    } else {
                        textField.setStyle("-fx-opacity: 0.5;");
                        textField.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.showAndWait();

        if ( alert.getResult() == ButtonType.OK ){
            listener.onDialogButtonClick();
        }
    }

    @Override
    public void showError(String message) {

    }
}