package main.endGame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.menu.MenuController;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    @FXML
    private Label endGameLabel;

    private String text;

    public EndGameController(String text) {
        this.text = text;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        endGameLabel.setText(text);
    }

    @FXML
    public void onExit() {
        Platform.exit();
    }

    @FXML
    public void onContinue(ActionEvent ae) {
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/main/menu/Menu.fxml"));
        MenuController menuController = new MenuController();
        menuLoader.setController(menuController);

        try {
            stage.setScene(new Scene(menuLoader.load()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
