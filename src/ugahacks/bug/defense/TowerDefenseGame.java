package ugahacks.bug.defense;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TowerDefenseGame extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
     Scene gameScene = createStart();
     primaryStage.setScene(gameScene);
     primaryStage.show();

    }

    Scene createStart() {
        Group errorField = new Group();
        errorField.minWidth(640);
        errorField.minHeight(480);
        Rectangle groupBckgrnd = new Rectangle(640, 480, Color.BLACK);
        errorField.getChildren().add(groupBckgrnd);
        for(int i = 0; i < 20; i++) {
            Text errorBox = new Text("ERROR");
            errorBox.setFill(Color.RED);
            errorBox.setStyle("-fx-font: 24 arial;");
            errorBox.setX((Math.random() * 400) + (5 * i));
            errorBox.setY((Math.random() * 250) + (2.5 * i));
            errorField.getChildren().add(errorBox);
        }
        Button startGame = new Button("Debug >0<");
        startGame.setLayoutX(290);
        startGame.setLayoutY(400);
        errorField.getChildren().add(startGame);
        Scene startScene = new Scene(errorField);
        return startScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
