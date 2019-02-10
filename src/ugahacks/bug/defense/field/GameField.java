package ugahacks.bug.defense.field;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import ugahacks.bug.defense.Pos;
import ugahacks.bug.defense.Shop;
import ugahacks.bug.defense.TowerDefenseGame;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class GameField extends Canvas {

    public static final double WIDTH = 640 / 2f;
    public static final double HEIGHT = 480 / 2f;

    public static final Path mainPath = Path.createPath(
            new Pos(0, HEIGHT / 2), new Pos(WIDTH / 10, HEIGHT / 2),
            new Pos(WIDTH / 8, HEIGHT / 4), new Pos(WIDTH / 7, HEIGHT * 4 / 5)
    );

    private AnimationTimer gameLoop;
    private BooleanProperty paused;

    private long lastNanoTime;

    private List<Debugger> debuggers;
    private List<Bug> bugs;
    private Line2D[] mainPathLines;

    public boolean buyMode; // if true then can place a tower
    public int towerToPlace;

    private boolean drawCircle;
    private Pos circlePos;
    private int circleRange;

    public Debugger selectedTower;

    private Shop gameShop;

    public boolean showRanges;

    public GameField() {
        super(WIDTH, HEIGHT);
        paused = new SimpleBooleanProperty(true);
        lastNanoTime = System.nanoTime();
        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double dt = (currentNanoTime - lastNanoTime) / 1e9;
                draw(getGraphicsContext2D());
                update(dt);
                lastNanoTime = currentNanoTime;
            }
        };
        gameLoop.stop();
        paused.addListener((ob, old, value) -> {
            if(value) { // if paused
                gameLoop.stop();
            } else {
                gameLoop.start();
            }
        });
        buyMode = false;
        towerToPlace = -1;

        setOnMouseMoved(this::onMouseOver);
        setOnMouseReleased(this::onClick);

        mainPathLines = Path.toLine2DArray(mainPath);
        debuggers = new ArrayList<>();
        bugs = new ArrayList<>();

        showRanges = false;
    }

    public void pause() {
        paused.set(true);
    }

    public void play() {
        paused.set(false);
    }

    /**
     * This method draws all of the Objects in the game.
     * @param g the GraphicsContext to be drawn on
     */
    public void draw(GraphicsContext g) {
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setStroke(Color.WHITE);
        Path current = mainPath;
        while(current != null) {
            g.strokeLine(current.start.x, current.start.y, current.end.x, current.end.y);
            current = current.nextPath;
        }

        debuggers.forEach(d -> {
            d.draw(g);

            if(showRanges) {
                drawCircle(g, Color.GRAY, d.pos, d.range);
            }
        });
        bugs.forEach(b -> b.draw(g));

        if(drawCircle) {
            drawCircle(g, Color.GRAY, circlePos, circleRange);

            // draw tower too
            g.setFill(towerToPlace == 0 ? Color.RED : towerToPlace == 1 ? Color.BLUE : Color.PURPLE);
            g.fillRect(circlePos.x - 3 / 2f, circlePos.y - 9, 3, 9);
        }
    }

    private void drawCircle(GraphicsContext g, Color color, Pos pos, double radius) {
        // draw circle maaan
        g.setStroke(color);
        g.strokeOval(pos.x - radius, pos.y - radius, radius * 2, radius * 2);
    }

    /**
     * This method is called every frame to update:
     * positions, game logic, and other things.
     * @param dt time since last frame
     */
    public void update(double dt) {
        debuggers.forEach(d -> d.update(bugs, dt));
        for(int i = 0; i < bugs.size(); i++) {
            if(bugs.get(i).hp <= 0)
                bugs.remove(i--);

            bugs.get(i).move(1, dt);
        }
    }

    public void onMouseOver(MouseEvent e) {
        // if in debugger buy mode, show range of tower
        if(buyMode && towerToPlace != -1) {
            // draw circle
            drawCircle = true;
            circlePos = new Pos(e.getX(), e.getY());

            double range = Debugger.JDB.range;
            if(towerToPlace == 1) {
                range = Debugger.GDB.range;
            } else if(towerToPlace == 2) {
                range = Debugger.ULT.range;
            }
            circleRange = (int) range;

        } else {
            drawCircle = false;
        }
    }

    public void setShop(Shop shop) {
        this.gameShop = shop;
    }

    public void onClick(MouseEvent e) {
        // if in debugger buy mode, put on board
        Pos pos = new Pos(e.getX(), e.getY());
        if(buyMode && towerToPlace != -1) {
            int cost = 4 * (int)Math.pow(2, towerToPlace); // 4, 8, or 16
            if(TowerDefenseGame.memory.get() - cost >= 0 && debuggers.stream().noneMatch(d -> d.pos.distance(pos) <= 5)) {
                boolean canPlace = true;
                for(Line2D line : mainPathLines)
                    if(line.intersects(pos.x - 4, pos.y - 4, 8, 8))
                        canPlace = false;

                if(canPlace) {
                    TowerDefenseGame.memory.set(TowerDefenseGame.memory.get() - cost);
                    debuggers.add(Debugger.makeDebugger(pos, towerToPlace));
                    buyMode = false;
                    drawCircle = false;
                }
            }
        } else {
            // if not in debugger mode, then select a tower (or you want to squash bugs)

            // get selected object
            Debugger selected = null;
            for(Debugger d : debuggers)
                if(new Rectangle(d.pos.x - 3 / 2f, d.pos.y - 9, 3, 9).contains(pos.x, pos.y)) {
                    selected = d;
                    gameShop.changeSelectedTower(d);
                    break;
                }

            selectedTower = selected; // upgrading
        }
    }
}
