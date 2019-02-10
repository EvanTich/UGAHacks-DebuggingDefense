package ugahacks.bug.defense;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ugahacks.bug.defense.field.Debugger;
import ugahacks.bug.defense.field.GameField;

public class Shop extends VBox{
    Shop(Stage mainStage, GameField game) {

        //ShopTab
        Button hardwareShopBut = new Button("Hardware");
        Button softwareShopBut = new Button("Software");
        HBox shopTabs = new HBox(softwareShopBut, hardwareShopBut);
        this.getChildren().add(shopTabs);

        //SoftwareShop
        Button tower1 = new Button("JDB");
        tower1.setOnAction(e -> {
            game.buyMode = true;
            game.towerToPlace = 0;
        });
        Button tower2 = new Button("GDB");
        tower2.setOnAction(e -> {
            game.buyMode = true;
            game.towerToPlace = 1;
        });
        Button tower3 = new Button("ULT");
        tower3.setOnAction(e -> {
            game.buyMode = true;
            game.towerToPlace = 2;
        });
        Label debuggerLabel = new Label("Debugger");
        Label memLabel = new Label("Memory");
        Label tower1Mem = new Label("4");
        Label tower2Mem = new Label("8");
        Label tower3Mem = new Label("16");
        HBox softwareTitles = new HBox(debuggerLabel, memLabel);
        HBox twr1 = new HBox(tower1, tower1Mem);
        HBox twr2 = new HBox(tower2, tower2Mem);
        HBox twr3 = new HBox(tower3, tower3Mem);
        HBox.setHgrow(tower1, Priority.ALWAYS);
        HBox.setHgrow(tower1Mem, Priority.ALWAYS);
        HBox.setHgrow(tower2, Priority.ALWAYS);
        HBox.setHgrow(tower2Mem, Priority.ALWAYS);
        HBox.setHgrow(tower3, Priority.ALWAYS);
        HBox.setHgrow(tower3Mem, Priority.ALWAYS);
        VBox sftwreShp = new VBox(softwareTitles, twr1, twr2, twr3);
        sftwreShp.setSpacing(2.5);
        sftwreShp.setAlignment(Pos.CENTER_LEFT);
        softwareShopBut.setOnAction(e -> {if(this.getChildren().size() > 1) {this.getChildren().remove(1);}
            this.getChildren().add(sftwreShp);
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
        hardwareShopBut.setOnAction(e -> {if(this.getChildren().size() > 1) {this.getChildren().remove(1);}
            this.getChildren().add(hrdwreShp);
            mainStage.sizeToScene();});
    }

    public void changeSelectedTower(Debugger tower) {
        String towerType = "";
        switch(tower.id) {
            case ('0'):
                towerType = "JDB";
                break;
            case ('1'):
                towerType = "GDB";
                break;
            case ('2'):
                towerType = "ULT";
                break;
        }

        Label towerName = new Label(towerType);
        Label dmgStat = new Label("Damage: " + tower.damage);
        Label rangeStat = new Label("Range: " + tower.range);
        Label rofStat = new Label("Speed: " + tower.speed);
        VBox stats = new VBox(dmgStat, rangeStat, rofStat);
        HBox currentInfo = new HBox(towerName, stats);

        Button upgrade = new Button("UpGrade");
        Button uninstall = new Button("Uninstall");
        VBox towerOptions = new VBox(upgrade, uninstall);

        VBox towerUpgrade = new VBox(currentInfo, towerOptions);
        this.getChildren().remove(1);
        this.getChildren().add(towerUpgrade);

    }
}

