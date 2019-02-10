package ugahacks.bug.defense.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ugahacks.bug.defense.Pos;

public class Bug {

    //Bug Images
    private final Image BUG1 = new Image("ugahacks/UGAHacksAssets/Bug1.png");
    private final Image BUG2 = new Image("ugahacks/UGAHacksAssets/Bug2.png");
    private final Image BUG3 = new Image("ugahacks/UGAHacksAssets/Bug3.png");

    private final int WIDTH = 12;
    private final int HEIGHT = 12;

    Pos pos;
    int hp, money, dmg;
    public static int moneyMultiply = 1;

    Path onPath; // can be null, this doesnt move if null

    private byte type;

    public Bug(int type, Path onPath) {
        pos = new Pos(onPath.start.x, onPath.start.y);
        this.type = (byte) type;
        this.onPath = onPath;
        money = type == 0 ? (moneyMultiply * 40) : type == 1 ? (moneyMultiply * 80) : (moneyMultiply * 160);
        hp = type == 0 ? 50 : type == 1 ? 100 : 200;
        dmg = type == 0 ? 2 : type == 1 ? 5 : 15;
    }

    public byte getType(){
        return type;
    }

    public void move(double speed, double dt) {
        if(onPath != null)
            onPath.move(this, speed, dt);
    }

    public void draw(GraphicsContext g) {
        g.setFill(Color.GREEN);
        g.drawImage(getImage(), pos.x - WIDTH / 2f, pos.y - HEIGHT / 2f, WIDTH, HEIGHT);
    }

    private Image getImage() {
        return type == 0 ? BUG1 : type == 1 ? BUG2 : BUG3;
    }
}
