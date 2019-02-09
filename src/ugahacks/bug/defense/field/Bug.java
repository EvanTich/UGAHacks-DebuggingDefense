package ugahacks.bug.defense.field;

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
}
