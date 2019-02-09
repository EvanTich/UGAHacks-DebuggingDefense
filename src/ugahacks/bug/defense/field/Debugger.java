package ugahacks.bug.defense.field;

import ugahacks.bug.defense.Pos;

import java.util.List;

public class Debugger {

    Pos pos;

    // shooting*
    double speed, range, damage;

    public Debugger(Pos pos, double speed, double range, double damage) {
        this.pos = pos;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
    }

    public void update(List<Bug> bugs, double dt) {

    }
}
