package ugahacks.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ugahacks.bug.defense.Pos;
import ugahacks.bug.defense.field.GameField;
import ugahacks.bug.defense.field.Path;

public class TestGameField extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        GameField game = new GameField();
        root.setCenter(game);
        Scene scene = new Scene(root);

        game.play();
        game.buyMode = true;
        game.towerToPlace = 0;

        game.startWaves();

        primaryStage.setScene(scene);
        primaryStage.setTitle("FIELD TEST!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
