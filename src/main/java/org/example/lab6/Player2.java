package org.example.lab6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Player2 {

    @FXML
    private GridPane grid2;
    @FXML
    private Label instruction;
    @FXML
    private Button proceed;

    int battleShips = 1;
    int destroyers = 2;
    int submarines = 3;

    int sRow, sColumn, dRow, dColumn;
    boolean placingSource = true;
    private static Boolean[][] boolGrid2 = new Boolean[9][9];

    @FXML
    void initialize() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Button button = new Button();
                button.setPrefSize(60, 60);
                button.setText("");

                int row = r;
                int column = c;
                button.setOnAction(event -> buttonClicked(button, row, column));

                grid2.add(button, c, r);
                proceed.setVisible(false);

                boolGrid2[r][c] = false;
            }
        }
    }

    public static Boolean[][] getBoolGrid2() {
        return boolGrid2;
    }

    void setInstruction() {
        if (battleShips > 0)
            instruction.setText("Place a Battleship");
        else if (destroyers > 0)
            instruction.setText("Place a Destroyer");
        else if (submarines > 0)
            instruction.setText("Place a Submarine");
        else {
            instruction.setText("Ships are ready for war!");
            proceed.setVisible(true);
        }
    }

    boolean sourceValidity() {
        return !boolGrid2[sRow][sColumn];
    }

    boolean destinationValidity() {
        if (sRow != dRow && sColumn != dColumn)
            return false;
        if (sRow == dRow) {
            int columnStarts = Math.min(sColumn, dColumn);
            int columnEnds = Math.max(sColumn, dColumn);
            for (int i = columnStarts; i <= columnEnds; i++) {
                if (boolGrid2[sRow][i])
                    return false;
            }
        } else {
            int rowStarts = Math.min(sRow, dRow);
            int rowEnds = Math.max(sRow, dRow);
            for (int i = rowStarts; i <= rowEnds; i++) {
                if (boolGrid2[i][sColumn])
                    return false;
            }
        }
        int horizontal = Math.abs(dRow - sRow);
        int vertical = Math.abs(dColumn - sColumn);
        int length = horizontal + vertical;

        if (battleShips > 0)
            return length == 2;
        else if (destroyers > 0)
            return length == 1;
        else
            return false;
    }

    void colorButtonGreen(int r, int c) {
        Button button = (Button) grid2.getChildren().get((r * 9) + c + 1);
        button.setStyle("-fx-background-color: green;");
    }

    void colorWhite(int r, int c) {
        Button button = (Button) grid2.getChildren().get((r * 9) + c + 1);
        button.setStyle("-fx-background-color: white;");
    }

    void colorDefault(int r, int c) {
        Button button = (Button) grid2.getChildren().get((r * 9) + c + 1);
        button.setStyle("-fx-background-color: #F0F0F0;");
    }

    void colorGrey(int r, int c) {
        Button button = (Button) grid2.getChildren().get((r * 9) + c + 1);
        button.setStyle("-fx-background-color: grey;");
    }

    void placeShip() {
        if (sRow == dRow) {
            int columnStarts = Math.min(sColumn, dColumn);
            int columnEnds = Math.max(sColumn, dColumn);
            for (int i = columnStarts; i <= columnEnds; i++) {
                colorWhite(sRow, i);
                boolGrid2[sRow][i] = true;
            }
        } else {
            int rowStarts = Math.min(sRow, dRow);
            int rowEnds = Math.max(sRow, dRow);
            for (int i = rowStarts; i <= rowEnds; i++) {
                colorWhite(i, sColumn);
                boolGrid2[i][sColumn] = true;
            }
        }
    }

    void placeBS(int r, int c) {
        if (placingSource) {
            sRow = r;
            sColumn = c;
            if (sourceValidity()) {
                colorGrey(r, c);
                placingSource = false;
            }
        } else {
            dRow = r;
            dColumn = c;
            if (destinationValidity()) {
                placeShip();
                battleShips--;
            } else
                colorDefault(sRow, sColumn);
            placingSource = true;
        }
    }

    void placeD(int r, int c) {
        if (placingSource) {
            sRow = r;
            sColumn = c;
            if (sourceValidity()) {
                colorGrey(r, c);
                placingSource = false;
            }
        } else {
            dRow = r;
            dColumn = c;
            if (destinationValidity()) {
                placeShip();
                destroyers--;
            } else
                colorDefault(sRow, sColumn);
            placingSource = true;
        }
    }

    void placeS(int r, int c) {
        sRow = r;
        sColumn = c;
        if (sourceValidity()) {
            colorWhite(r, c);
            boolGrid2[sRow][sColumn] = true;
            submarines--;
        }
    }

    public void buttonClicked(Button button, int r, int c) {
        if (battleShips > 0) {
            placeBS(r, c);
            setInstruction();
        } else if (destroyers > 0) {
            placeD(r, c);
            setInstruction();
        } else if (submarines > 0) {
            placeS(r, c);
            setInstruction();
        }
    }

    public void proceedB(ActionEvent e2) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Shooter1.fxml"));
        Shooter1.startingBoard();
        Shooter2.startingBoard();
        Stage stage = (Stage) ((Button) e2.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Battleship: Player 1");
        stage.show();
    }
}
