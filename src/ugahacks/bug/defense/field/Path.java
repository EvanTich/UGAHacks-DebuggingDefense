package ugahacks.bug.defense.field;

import ugahacks.bug.defense.Pos;

public class Path {

    Pos start, end;

    Path nextPath;

    public Path(Pos start, Pos end) {
        this.start = start;
        this.end = end;
    }

    public Path connect(Path nextPath) {
        this.nextPath = nextPath;
        return nextPath;
    }

    public void move(Bug bug, double speed, double dt) {
        double bad = bug.pos.x;
        double dx = bad - (bug.pos.x += slope() * speed * dt);

        bug.pos.y += slope() * speed * dt * dx;

        if((Math.abs(bug.pos.x - end.x)<.5) && (Math.abs(bug.pos.x - end.x)<.5)) // if it is past the end or at the end, change to the next path
            bug.onPath = nextPath;

    }

    public double slope() {
        return (end.y - start.y) / (end.x - start.x);
    }
}
