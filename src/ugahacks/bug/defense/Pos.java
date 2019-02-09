package ugahacks.bug.defense;

public class Pos {

    public double x, y;

    public Pos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Pos other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }
}
