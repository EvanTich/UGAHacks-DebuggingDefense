package ugahacks.bug.defense;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ugahacks.bug.defense.field.Debugger;

public class Shop extends VBox{

    Shop(Stage mainStage) {
        //ShopTab
        Button hardwareShopBut = new Button("Hardware");
        Button softwareShopBut = new Button("Software");
        HBox shopTabs = new HBox(softwareShopBut, hardwareShopBut);
        this.getChildren().add(shopTabs);

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

    void changeSelectedTower(Debugger tower) {
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

