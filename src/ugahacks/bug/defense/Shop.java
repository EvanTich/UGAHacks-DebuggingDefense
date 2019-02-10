package ugahacks.bug.defense;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ugahacks.bug.defense.field.Bug;
import ugahacks.bug.defense.field.Debugger;
import ugahacks.bug.defense.field.GameField;

import static ugahacks.bug.defense.TowerDefenseGame.*;

public class Shop extends VBox{


    private GridPane softwareShop;
    private Stage mainStage;
    private GameField game;
    private int ramUp = 1000;
    private int cpuUp = 2000;
    private int gpuUp = 3000;

    public Debugger selectedTower;


    Shop(Stage mainStage, GameField game) {

        this.mainStage = mainStage;
        this.game = game;

        this.setMaxWidth(160);

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
        HBox softwareTitles = new HBox(20, debuggerLabel, memLabel);
        //Formatting
        softwareTitles.setMinWidth(160);

        softwareShop = new GridPane();
        softwareShop.setHgap(20);
        softwareShop.setVgap(8);
        softwareShop.add(debuggerLabel, 0, 0);
        softwareShop.add(memLabel, 1, 0);
        softwareShop.add(tower1, 0, 1);
        softwareShop.add(tower1Mem, 1, 1);
        softwareShop.add(tower2, 0, 2);
        softwareShop.add(tower2Mem, 1, 2);
        softwareShop.add(tower3, 0, 3);
        softwareShop.add(tower3Mem, 1, 3);

//        softwareShop = new VBox(softwareTitles, twr1, twr2, twr3);
        softwareShop.setAlignment(Pos.CENTER_LEFT);
        softwareShopBut.setOnAction(e -> {if(this.getChildren().size() > 1) {this.getChildren().remove(1);}
            this.getChildren().add(softwareShop);
            mainStage.sizeToScene();});

        //HardwareShop

        Label upGrade1Cost = new Label("$1000");
        Label upGrade2Cost = new Label("$2000");
        Label upGrade3Cost = new Label("$3000");

        Button upGrade1 = new Button("RAM");
        upGrade1.setOnAction(e -> {
            if(money.get() >= ramUp) {
                memory.set(memory.get() + 20);
                money.set(money.get() - ramUp);
                ramUp *= 2;
                upGrade1Cost.setText("$" + ramUp);
            }
        });
        Button upGrade2 = new Button("CPU");
        upGrade2.setOnAction(e -> {
            if(money.get() >= cpuUp) {
                health.set(health.get() + 20);
                money.set(money.get() - cpuUp);
                cpuUp *= 2;
                upGrade2Cost.setText("$" + cpuUp);
            }
        });
        Button upGrade3 = new Button("GPU");
        upGrade3.setOnAction(e -> {
            if(money.get() >= gpuUp) {
                Bug.moneyMultiply++;
                money.set(money.get() - gpuUp);
                gpuUp *= 2;
                upGrade3Cost.setText("$" + gpuUp);
            }
        });
      //  Label upGrade1Cost = new Label("$1000");
      //  Label upGrade2Cost = new Label("$2000");
      //  Label upGrade3Cost = new Label("$3000");

        GridPane hardwareShop = new GridPane();
        hardwareShop.setHgap(20);
        hardwareShop.setVgap(8);
        hardwareShop.add(new Label("Upgrade"), 0, 0);
        hardwareShop.add(new Label("Cost"), 1, 0);
        hardwareShop.add(upGrade1, 0, 1);
        hardwareShop.add(upGrade1Cost, 1, 1);
        hardwareShop.add(upGrade2, 0, 2);
        hardwareShop.add(upGrade2Cost, 1, 2);
        hardwareShop.add(upGrade3, 0, 3);
        hardwareShop.add(upGrade3Cost, 1, 3);

        hardwareShopBut.setOnAction(e -> {if(this.getChildren().size() > 1) {this.getChildren().remove(1);}
            this.getChildren().add(hardwareShop);
            mainStage.sizeToScene();});
    }

    public void returnToShop(){
        if(this.getChildren().size() > 1) {
            this.getChildren().remove(1);
        }

        this.getChildren().add(softwareShop);
        mainStage.sizeToScene();
    }

    public void changeSelectedTower(Debugger tower) {
        selectedTower = tower;
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

        Button upgrade = new Button("Upgrade");
        upgrade.setOnAction(e -> {
            // check if we have enough memory
            tower.upgrade();
            changeSelectedTower(tower);
        });
        Button uninstall = new Button("Uninstall");
        uninstall.setOnAction(e -> {
            game.debuggers.remove(tower);
            memory.set(memory.get() + (tower.id == 0 ? 4 : tower.id == 1 ? 8 : 16));
        });
        VBox towerOptions = new VBox(upgrade, uninstall);

        VBox towerUpgrade = new VBox(currentInfo, towerOptions);
        this.getChildren().remove(1);
        this.getChildren().add(towerUpgrade);

    }
}

