package org.example.lab6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Shooter2 {
    @FXML
    private GridPane shooterGrid2;
    @FXML
    private Label instruction;
    @FXML
    private Button proceed;
    @FXML
    private Button finish;
    private Boolean[][] player1Board = Player1.getBoolGrid1(); // Assuming Player1 is the first player
    private static Boolean[][] clicks = new Boolean[9][9];
    private static Integer[][] hitMiss = new Integer[9][9];
    private static boolean turn = false; // Initially, it's not the second player's turn
    private static int hits = 0;

    @FXML
    void initialize() {
        //startingBoard();


        if(hitMiss==null)
            startingBoard();


        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Button button = new Button();
                button.setPrefSize(60, 60);
                button.setText("");

                int row = r;
                int column = c;
                button.setOnAction(event -> clicked(button, row, column));

                shooterGrid2.add(button, c, r);
                if (hitMiss[r][c] != null) {
                    if (hitMiss[r][c] == 2)
                        colorRed(r, c);
                    else if (hitMiss[r][c] == 1)
                        colorGreen(r, c);

//                   // proceed.setVisible(false);
//                    finish.setVisible(false);
//                    instruction.setText("Player 2's turn");
                }
            }
        }
    }

    public static void startingBoard() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                hitMiss[i][j] = 0;
                clicks[i][j] = false;
            }
    }

    void clicked(Button button, int r, int c) {
        if (turn) {
            if (clicks[r][c]) {
                instruction.setText("Already shot. Try another cell.");
            } else {
                if (player1Board[r][c]) {
                    instruction.setText("Hit! Shoot again.");
                    hits++;
                    hitMiss[r][c] = 2;
                    colorRed(r, c);
                } else {
                    instruction.setText("Miss!");
                    proceed.setVisible(true);
                    hitMiss[r][c] = 1;
                    colorGreen(r, c);
                    turn = false;
                }
                clicks[r][c] = true;
            }
        }

        if (hits == 10) {
            instruction.setText("The war is over. You won!!");
            finish.setVisible(true);
        }
    }

    public static void canTakeTurn() {
        turn = true;
    }

    void colorGreen(int r, int c) {
        Button button = (Button) shooterGrid2.getChildren().get(r * 9 + c +1);
        button.setStyle("-fx-background-color: green");
    }

    void colorRed(int r, int c) {
        Button button = (Button) shooterGrid2.getChildren().get(r * 9 + c +1);
        button.setStyle("-fx-background-color: red");
    }

    public void clickProceed(ActionEvent e2) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Shooter1.fxml"));
        Stage stage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        Shooter1.canTakeTurn();
        stage.setScene(scene);
        stage.setTitle("Battleship: Player 1");
        stage.show();
    }

//    public void clickFinish(ActionEvent e2) {
//        Stage stage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
//        stage.close();
//    }
}
