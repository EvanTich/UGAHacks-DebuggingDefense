package ugahacks.bug.defense.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ugahacks.bug.defense.Pos;

public class Bug {

    //Bug Images
    private final Image BUG1 = new Image("ugahacks/UGAHacksAssets/Bug1.png");

    Pos pos;
    int hp;

    Path onPath; // can be null, this doesnt move if null

    public Bug(int hp, Path onPath) {
        pos = new Pos(onPath.start.x, onPath.start.y);
        this.hp = hp;
        this.onPath = onPath;
    }

    public void move(double speed, double dt) {
        if(onPath != null)
            onPath.move(this, speed, dt);
    }

    public void draw(GraphicsContext g) {
        g.setFill(Color.GREEN);
        g.fillRect(pos.x - 9 / 2f, pos.y - 9 / 2f, 9, 9);
    }
}
