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
    private int memory;
    private int money;

    @Override
    public void start(Stage primaryStage) throws Exception {
     mainStage = primaryStage;
     mainStage.setScene(createStart());
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
        GameField TwrDfncGame = new GameField();
        gamePane.setCenter(TwrDfncGame);

        VBox shop = new VBox();
        gamePane.setRight(shop);

        //ShopTab
        Button hardwareShopBut = new Button("Hardware");
        Button softwareShopBut = new Button("Software");
        HBox shopTabs = new HBox(softwareShopBut, hardwareShopBut);
        shop.getChildren().add(shopTabs);

        //SoftwareShop
        Button tower1 = new Button("Debugger");
        Button tower2 = new Button("D38ugg3r");
        Button tower3 = new Button("DEBUGGER");
        Label tower1Mem = new Label("Memory: 4");
        Label tower2Mem = new Label("Memory: 8");
        Label tower3Mem = new Label("Memory: 16");
        HBox twr1 = new HBox(tower1, tower1Mem);
        HBox twr2 = new HBox(tower2, tower2Mem);
        HBox twr3 = new HBox(tower3, tower3Mem);
        VBox sftwreShp = new VBox(twr1, twr2, twr3);
        sftwreShp.setSpacing(2.5);
        softwareShopBut.setOnAction(e -> {if(shop.getChildren().size() > 1) {shop.getChildren().remove(1);}
        shop.getChildren().add(sftwreShp);
        mainStage.sizeToScene();});

        //HardwareShop
        Button upGrade1 = new Button("RAM");
        Button upGrade2 = new Button("CPU");
        Button upGrade3 = new Button("GPU");
        Label upGrade1Cost = new Label("Cost: $1000");
        Label upGrade2Cost = new Label("Cost: $2000");
        Label upGrade3Cost = new Label("Cost: $3000");
        HBox up1 = new HBox(upGrade1, upGrade1Cost);
        HBox up2 = new HBox(upGrade2, upGrade2Cost);
        HBox up3 = new HBox(upGrade3, upGrade3Cost);
        VBox hrdwreShp = new VBox(up1, up2, up3);
        hrdwreShp.setSpacing(2.5);
        hardwareShopBut.setOnAction(e -> {if(shop.getChildren().size() > 1) {shop.getChildren().remove(1);}
            shop.getChildren().add(hrdwreShp);
            mainStage.sizeToScene();});

        //BottomDisplay
        Button quit = new Button("ESC");
        quit.setOnAction(e -> mainStage.setScene(createStart()));
        Rectangle dispImg = new Rectangle(320, 120, Color.GREEN);
        Label memoryLbl = new Label("Memory" + memory);
        Label moneyLbl = new Label("Money" + money);
        VBox resources = new VBox(memoryLbl, moneyLbl);
        resources.setSpacing(2);
        HBox BotDisp = new HBox(quit, dispImg, resources);
        gamePane.setBottom(BotDisp);

        return playing;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
