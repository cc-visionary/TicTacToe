package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.menu.MenuController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = generateMenuLoader().load();
        primaryStage.setTitle("MCO2: TicTacToe");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Generate the Menu Loader to be loaded into the primaryStage
     * @return FXMLLoader which was generated
     */
    public FXMLLoader generateMenuLoader() {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/main/menu/Menu.fxml"));
        // set menu loader's controller to MenuController
        menuLoader.setController(new MenuController());

        return menuLoader;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
