package com.saikat.sudoku.userinterface;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class SudokuTextField  extends TextField {

    private final int x;
    private final int y;
    private boolean isFocused;
    private Character currentChar;

    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
        this.currentChar = ' ';

        // TODO: Hide the text input indicating bar

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            isFocused = newValue;
            toggleBg();
        });
        toggleBg();
    }

    private void toggleBg(){
        Background bg;
        Insets  insets = new Insets(2, 2, 2, 2);
        if(isFocused){
            bg = new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, insets));
            this.setCursor(Cursor.CLOSED_HAND);
        } else {
            bg = new Background(new BackgroundFill(Color.WHITE, null, insets));
            this.setCursor(Cursor.DEFAULT);
        }
        this.setBackground(bg);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void replaceText(int i, int i1, String s) {
        super.replaceText(i, i1, s);
        System.out.println("replaceText method called: " + s);
        if ( s.matches("[1-9]")){
            this.currentChar = s.charAt(0);
        } else {
            System.err.println("You typed: " + s + ", but it is not a number");
        }
        this.setText(String.valueOf(this.currentChar));
    }

    @Override
    public void extendSelection(int i) {
        System.out.println("extendSelection method called: " + i);
    }

    @Override
    public void replaceSelection(String s) {
        System.out.println("replaceSelection method called: " + s);
        this.deselect();
    }

}
