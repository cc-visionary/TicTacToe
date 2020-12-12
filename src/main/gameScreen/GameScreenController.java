package main.gameScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.endGame.EndGameController;
import main.model.TicTacToe;

import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController {
    @FXML
    private Button topLeftButton, topCenterButton, topRightButton;

    @FXML
    private Button middleLeftButton, middleCenterButton, middleRightButton;

    @FXML
    private Button bottomLeftButton, bottomCenterButton, bottomRightButton;

    @FXML
    private Label playerLabel;

    @FXML
    private GridPane board;

    private char choice;
    private TicTacToe ticTacToe;

    public GameScreenController(char choice, TicTacToe ticTacToe) {
        this.choice = choice;
        this.ticTacToe = ticTacToe;
    }

    @FXML
    public void topLeftPressed() {
        moveChosen(0, 0);
    }

    @FXML
    public void topCenterPressed() {
        moveChosen(0, 1);
    }

    @FXML
    public void topRightPressed() {
        moveChosen(0, 2);
    }

    @FXML
    public void middleLeftPressed() {
        moveChosen(1, 0);
    }

    @FXML
    public void middleCenterPressed() {
        moveChosen(1, 1);
    }

    @FXML
    public void middleRightPressed() {
        moveChosen(1, 2);
    }

    @FXML
    public void bottomLeftPressed() {
        moveChosen(2, 0);
    }

    @FXML
    public void bottomCenterPressed() {
        moveChosen(2, 1);
    }

    @FXML
    public void bottomRightPressed() {
        moveChosen(2, 2);
    }

    private void moveChosen(int row, int column) {
        ticTacToe.move(row, column);
        refresh();
        checkWin();

        // if random or smart, AI will select its move
        switch (choice) {
            case '0':
                playerLabel.setText("AI's Turn");
                ticTacToe.randomMove();
                break;
            case '1':
                playerLabel.setText("AI's Turn");
                ticTacToe.smartOne();
                break;
            case '2':
                playerLabel.setText("AI's Turn");
                ticTacToe.smartTwo();
                break;
            case '3':
                playerLabel.setText("AI's Turn");
                ticTacToe.smartThree();
                break;
            case '4':
                playerLabel.setText("AI's Turn");
                ticTacToe.smartFour();
                break;
            case '5':
                playerLabel.setText("AI's Turn");
                ticTacToe.smartFive();
                break;
        }
        refresh();
        checkWin();

        playerLabel.setText("Player " + (ticTacToe.getTurn() % 2 == 1 ? 1 : 2) + "'s Turn");
    }

    private void checkWin() {
        String text = "";
        if(ticTacToe.hasWon(ticTacToe.getP1Moves())) {
            // player 1 has won
            text = "Player 1 has won!";
        } else if(ticTacToe.hasWon(ticTacToe.getP2Moves())) {
            // player 2 has won
            text = "Player 2 has won!";
        } else if(ticTacToe.isDraw()) {
            // its a draw
            text = "It's a draw!";
        }

        if(!text.equals("")) {
            FXMLLoader endGameLoader = new FXMLLoader(getClass().getResource("/main/endGame/EndGame.fxml"));
            EndGameController endGameController = new EndGameController(text);
            endGameLoader.setController(endGameController);

            Stage stage = (Stage) ((Parent) board).getScene().getWindow();

            try {
                stage.setScene(new Scene(endGameLoader.load()));
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    private void refresh() {
        if(ticTacToe.getP1Moves()[0][0] == 1) {
            topLeftButton.setText("X");
            topLeftButton.setDisable(true);
        }
        if(ticTacToe.getP1Moves()[0][1] == 1) {
            topCenterButton.setText("X");
            topCenterButton.setDisable(true);
        }
        if(ticTacToe.getP1Moves()[0][2] == 1) {
            topRightButton.setText("X");
            topRightButton.setDisable(true);
        }
        if(ticTacToe.getP1Moves()[1][0] == 1) {
            middleLeftButton.setText("X");
            middleLeftButton.setDisable(true);
        }
        if(ticTacToe.getP1Moves()[1][1] == 1) {
            middleCenterButton.setText("X");
            middleCenterButton.setDisable(true);
        }
        if(ticTacToe.getP1Moves()[1][2] == 1) {
            middleRightButton.setText("X");
            middleRightButton.setDisable(true);
        }
        if(ticTacToe.getP1Moves()[2][0] == 1) {
            bottomLeftButton.setText("X");
            bottomLeftButton.setDisable(true);
        }
        if(ticTacToe.getP1Moves()[2][1] == 1) {
            bottomCenterButton.setText("X");
            bottomCenterButton.setDisable(true);
        }
        if(ticTacToe.getP1Moves()[2][2] == 1) {
            bottomRightButton.setText("X");
            bottomRightButton.setDisable(true);
        }

        if(ticTacToe.getP2Moves()[0][0] == 1) {
            topLeftButton.setText("O");
            topLeftButton.setDisable(true);
        }
        if(ticTacToe.getP2Moves()[0][1] == 1) {
            topCenterButton.setText("O");
            topCenterButton.setDisable(true);
        }
        if(ticTacToe.getP2Moves()[0][2] == 1) {
            topRightButton.setText("O");
            topRightButton.setDisable(true);
        }
        if(ticTacToe.getP2Moves()[1][0] == 1) {
            middleLeftButton.setText("O");
            middleLeftButton.setDisable(true);
        }
        if(ticTacToe.getP2Moves()[1][1] == 1) {
            middleCenterButton.setText("O");
            middleCenterButton.setDisable(true);
        }
        if(ticTacToe.getP2Moves()[1][2] == 1) {
            middleRightButton.setText("O");
            middleRightButton.setDisable(true);
        }
        if(ticTacToe.getP2Moves()[2][0] == 1) {
            bottomLeftButton.setText("O");
            bottomLeftButton.setDisable(true);
        }
        if(ticTacToe.getP2Moves()[2][1] == 1) {
            bottomCenterButton.setText("O");
            bottomCenterButton.setDisable(true);
        }
        if(ticTacToe.getP2Moves()[2][2] == 1) {
            bottomRightButton.setText("O");
            bottomRightButton.setDisable(true);
        }
    }
}