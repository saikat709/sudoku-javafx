module com.saikat.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.saikat.sudoku to javafx.fxml;
    exports com.saikat.sudoku;
}