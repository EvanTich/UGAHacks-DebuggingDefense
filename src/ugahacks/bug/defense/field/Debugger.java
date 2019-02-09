package ugahacks.bug.defense.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ugahacks.bug.defense.Pos;

import java.util.Comparator;
import java.util.List;

public class Debugger {

    // no changing allowed (in code I mean, change the variables all you want here)
    public static final Debugger JDB = new Debugger(null, 1, 25, 25, 0);
    public static final Debugger GDB = new Debugger(null, .75, 35, 30, 1);
    public static final Debugger ULT = new Debugger(null, .5, 50, 40, 2); // ultra debugger

    Pos pos;

    // shooting*
    public double speed, // lower = faster
           range, // bigger = farther
           damage; // bigger = better

    public byte id;

    private double shootTimer;
    private Pos shootTo;
    private double drawShotTimer;

    public Debugger(Pos pos, double speed, double range, double damage, int id) {
        this.pos = pos;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.id = (byte)id;
    }

    public void update(List<Bug> bugs, double dt) {
        if(drawShotTimer >= 0) // for drawing the shot to the enemy
            drawShotTimer -= dt;

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
            shootTo = closest.pos; // for the visual
            drawShotTimer = .25;

            closest.hp -= damage;
        }
    }

    public void draw(GraphicsContext g) {
        g.setFill(id == 0 ? Color.RED : id == 1 ? Color.BLUE : Color.PURPLE);
        g.fillRect(pos.x - 3 / 2f, pos.y - 9, 3, 9);

        if(drawShotTimer > 0 && shootTo != null) {
            g.setFill(Color.AQUA); // haha funny joke
            g.setLineDashes(2);
            g.strokeLine(pos.x, pos.y, shootTo.x, shootTo.y);
        }
    }

    public void upgrade() {
        if(range == JDB.range) {
            // upgrade to GDB
            speed = GDB.speed;
            range = GDB.range;
            damage = GDB.damage;
        } else if(range == GDB.range) {
            // upgrade to ULT
            speed = ULT.speed;
            range = ULT.range;
            damage = ULT.damage;
        }
    }

    public static Debugger makeDebugger(Pos pos, int level) {
        switch(level) {
            default:
            case 0:
                return new Debugger(pos, JDB.speed, JDB.range, JDB.damage, JDB.id);
            case 1:
                return new Debugger(pos, GDB.speed, GDB.range, GDB.damage, GDB.id);
            case 2:
                return new Debugger(pos, ULT.speed, ULT.range, ULT.damage, ULT.id);
        }
    }
}
