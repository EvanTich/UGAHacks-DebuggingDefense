package ugahacks.bug.defense.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ugahacks.bug.defense.Pos;

public class Bug {

    Pos pos;
    int hp;

    Path onPath; // can be null, this doesnt move if null

    public Bug(Pos pos, int hp, Path onPath) {
        this.pos = pos;
        this.hp = hp;
        this.onPath = onPath;
    }

    public void move(double speed, double dt) {
        if(onPath != null)
            onPath.move(this, speed, dt);
    }

    public void draw(GraphicsContext g) {
        g.setFill(Color.GREEN);
        g.fillRect(pos.x - 3 / 2f, pos.y - 3 / 2f, 3, 3);
    }
}
