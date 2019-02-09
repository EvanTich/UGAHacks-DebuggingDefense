package ugahacks.bug.defense.field;

import ugahacks.bug.defense.Pos;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

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

    public static Line2D[] toLine2DArray(Path start) {
        List<Line2D> lines = new ArrayList<>();
        while(start != null) {
            lines.add(new Line2D.Double(start.start.x, start.start.y, start.end.x, start.end.y));

            start = start.nextPath;
        }

        Line2D[] aLines = new Line2D[lines.size()];
        for(int i = 0; i < aLines.length; i++) {
            aLines[i] = lines.get(i);
        }

        return aLines;
    }

    public static Path createPath(Pos... locations) {
        if(locations.length < 2)
            return null;

        Path start = new Path(locations[0], locations[1]);
        Path all = start;
        for(int i = 1; i < locations.length - 1; i++)
            all = all.connect(new Path(locations[i], locations[i + 1]));

        return start;
    }
}
