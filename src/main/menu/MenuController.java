package main.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.gameScreen.GameScreenController;
import main.model.TicTacToe;

import java.io.IOException;

public class MenuController {
    @FXML
    public void choosePlayer(ActionEvent ae) throws IOException {
        goToGameScreen(ae, 'P');
    }

    @FXML
    public void chooseRandom(ActionEvent ae) throws IOException {
        goToGameScreen(ae, '0');
    }

    @FXML
    public void chooseOne(ActionEvent ae) throws IOException {
        goToGameScreen(ae, '1');
    }

    @FXML
    public void chooseTwo(ActionEvent ae) throws IOException {
        goToGameScreen(ae, '2');
    }

    private void goToGameScreen(ActionEvent ae, char choice) throws IOException {
        Stage primaryStage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Parent root = generateGameScreenLoader(choice).load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
    }

    private FXMLLoader generateGameScreenLoader(char choice) {
        FXMLLoader gameScreenLoader = new FXMLLoader(getClass().getResource("/main/gameScreen/GameScreen.fxml"));
        gameScreenLoader.setController(new GameScreenController(choice, new TicTacToe()));

        return gameScreenLoader;
    }
}
