package ugahacks.bug.defense;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ugahacks.bug.defense.field.GameField;

public class TowerDefenseGame extends Application {

    private Stage mainStage;
    public static int memory = 1000;
    public static int money = 10000000;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setScene(createStart());
        mainStage.setTitle("UGAHacks Tower Defense");
        mainStage.show();
    }

    private Scene createStart() {
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
        startGame.setOnAction(e -> mainStage.setScene(createPlay()));
        startGame.setLayoutX(290);
        startGame.setLayoutY(400);
        errorField.getChildren().add(startGame);
        Scene startScene = new Scene(errorField);
        return startScene;
    }

    private Scene createPlay() {
        //GameField
        BorderPane gamePane = new BorderPane();
        Scene playing = new Scene(gamePane);
        GameField game = new GameField();
        gamePane.setCenter(game);

        Shop shop = new Shop(mainStage, game);
        gamePane.setRight(shop);

        //BottomDisplay
        Button quit = new Button("ESC");
        quit.setOnAction(e -> mainStage.setScene(createStart()));
        Rectangle dispImg = new Rectangle(320, 120, Color.GREEN);
        Label memoryLbl = new Label("Memory: " + memory);
        Label moneyLbl = new Label("Money:  " + money);
        VBox resources = new VBox(memoryLbl, moneyLbl);
        resources.setSpacing(2);
        HBox BotDisp = new HBox(quit, dispImg, resources);
        gamePane.setBottom(BotDisp);
        game.play();

        return playing;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
