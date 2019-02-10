package ugahacks.bug.defense;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ugahacks.bug.defense.field.GameField;

public class TowerDefenseGame extends Application {

    public static TowerDefenseGame _instance;

    private Stage mainStage;

    public static IntegerProperty memory = new SimpleIntegerProperty(32);
    public static IntegerProperty money = new SimpleIntegerProperty(0);
    public static IntegerProperty health = new SimpleIntegerProperty(100);

    @Override
    public void start(Stage primaryStage) throws Exception {
        _instance = this;
        mainStage = primaryStage;
        mainStage.setScene(createStart());
        mainStage.setTitle("UGAHacks Tower Defense");
        mainStage.show();
    }

    private Scene createStart() {
        memory.set(32);
        money.set(0);
        health.set(100);

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
        startGame.setLayoutX(270);
        startGame.setLayoutY(400);
        startGame.setMinSize(100,20);
        Button credits = new Button("Credits");
        credits.setOnAction(e -> {
            Stage creditStage = new Stage();
            VBox creators = new VBox();
            Text evan = new Text("Evan Tichenor - Programming, Game Mechanics, Concept Design\n");
            Text cody = new Text("Cody Moore - Programming, Supplemental Programming, Concept Design, Head Flag Capturer\n");
            Text james = new Text("James Cannon - Programming, UI, Art, Concept Design");
            creators.getChildren().addAll(evan, cody, james);
            Scene creditScene = new Scene(creators);
            creditStage.setScene(creditScene);
            creditStage.setTitle("CREATORS");
            creditStage.show();
        });
        credits.setLayoutX(270);
        credits.setLayoutY(435);
        credits.setMinSize(100, 20);
        errorField.getChildren().addAll(startGame, credits);
        Scene startScene = new Scene(errorField);
        return startScene;
    }

    private Scene createPlay() {


        //GameField
        BorderPane gamePane = new BorderPane();
        Scene playing = new Scene(gamePane);
        GameField game = new GameField();
        gamePane.setLeft(game);

        Shop shop = new Shop(mainStage, game);
        game.setShop(shop);
        gamePane.setRight(shop);

        //BottomDisplay
        Button quit = new Button("ESC");
        quit.setOnAction(e -> mainStage.setScene(createStart()));
        Image hackerBoi = new Image("ugahacks/UGAHacksAssets/UGAHacks_HackerBoi.png");
        ImageView hackerBoiView = new ImageView(hackerBoi);
        hackerBoiView.setFitHeight(120);
        hackerBoiView.setFitWidth(320);
        Group hackerBoiGroup = new Group();
        hackerBoiGroup.getChildren().add(hackerBoiView);
        Label memoryLbl = new Label("Memory: " + memory);
        memoryLbl.textProperty().bind(memory.asString("Memory: %d"));
        Label moneyLbl = new Label("Money:  " + money);
        moneyLbl.textProperty().bind(money.asString("Money: %d"));
        Label healthLbl = new Label("Health: " + health);
        healthLbl.textProperty().bind(health.asString("Health: %d"));
        Button nextWave = new Button("Start");
        nextWave.setMinSize(80, 20);
        nextWave.setOnAction(e -> {
           nextWave.setText("Debugging");
           nextWave.setOnAction(null);
           game.gameStarter = true;
        });
        VBox resources = new VBox(memoryLbl, moneyLbl, healthLbl, nextWave);
        resources.setAlignment(Pos.CENTER);
        resources.setSpacing(5);
        resources.setMinWidth(159);
        quit.setLayoutX(0);
        quit.setLayoutY(0);
        hackerBoiGroup.getChildren().add(quit);
        HBox BotDisp = new HBox(hackerBoiGroup, resources);
        gamePane.setBottom(BotDisp);
        game.play();
        playing.getStylesheets().add("ugahacks/bug/defense/BugDefense.css");
        return playing;
    }

    public void gameOver(){
        Alert gameOver = new Alert(Alert.AlertType.CONFIRMATION);
        gameOver.setTitle("YOU LOST");
        gameOver.setHeaderText("GAME OVER");
        gameOver.setContentText("Game Over \n Play Again?");

        gameOver.setOnHidden(e -> {
            ButtonType but = gameOver.getResult();

            if(but == ButtonType.CANCEL) {
                // close program
                Platform.exit();
                System.exit(0);
            } else {
                this.mainStage.setScene(createStart());
            }
        });

        gameOver.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
