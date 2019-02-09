package ugahacks.bug.defense.field;

import javafx.scene.canvas.GraphicsContext;
import ugahacks.bug.defense.Pos;

import java.util.Comparator;
import java.util.List;

public class Debugger {

    Pos pos;

    // shooting*
    double speed, // lower = faster
           range, // bigger = farther
           damage; // bigger = better

    private double shootTimer;

    public Debugger(Pos pos, double speed, double range, double damage) {
        this.pos = pos;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
    }

    public void update(List<Bug> bugs, double dt) {
        shootTimer += dt;
        if(shootTimer >= speed) {
            shootTimer = 0;
            shoot(bugs);
        }
    }

    public void shoot(List<Bug> bugs) {
        // find closest bug and shoot
        Bug closest = bugs.stream().max(Comparator.comparingDouble(b -> b.pos.distance(pos))).orElse(null);

        if(closest != null) {
            // TODO: animate

            closest.hp -= damage;
        }
    }

    public void draw(GraphicsContext g) {
        g.fillRect(pos.x, pos.y - 9, 3, 9);
    }
}
