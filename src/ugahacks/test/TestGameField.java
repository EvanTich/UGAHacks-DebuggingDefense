package ugahacks.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ugahacks.bug.defense.Pos;
import ugahacks.bug.defense.field.GameField;
import ugahacks.bug.defense.field.Path;

public class TestGameField extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane root = new FlowPane();
        GameField game = new GameField(Path.createPath(new Pos(0, 0), new Pos(100, 20), new Pos(50, 50), new Pos(200, 145)), root);
        root.getChildren().addAll(new Button("SDAWDADS"), game);
        Scene scene = new Scene(root);

        game.play();
        game.buyMode = true;
        game.towerToPlace = 0;

        primaryStage.setScene(scene);
        primaryStage.setTitle("FIELD TEST!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
