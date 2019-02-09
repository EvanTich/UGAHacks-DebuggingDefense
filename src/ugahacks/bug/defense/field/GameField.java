package ugahacks.bug.defense.field;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ugahacks.bug.defense.Pos;

import java.util.ArrayList;
import java.util.List;

public class GameField extends Canvas {

    private AnimationTimer gameLoop;
    private BooleanProperty paused;

    private long lastNanoTime;

    private List<Debugger> debuggers;
    private List<Bug> bugs;
    private Path mainPath;

    public GameField() {
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
        init();
    }

    public void pause() {
        paused.set(true);
    }

    public void play() {
        paused.set(false);
    }

    /**
     * Initializes the specific game.
     */
    public void init() {
        bugs = new ArrayList<>();
        mainPath = new Path(new Pos(0, 0), new Pos(0, 0));
    }

    /**
     * This method draws all of the Objects in the game.
     * @param g the GraphicsContext to be drawn on
     */
    public void draw(GraphicsContext g) {

    }

    /**
     * This method is called every frame to update:
     * positions, game logic, and other things.
     * @param dt time since last frame
     */
    public void update(double dt) {
        debuggers.forEach(d -> d.update(bugs, dt));
        bugs.forEach(b -> b.onPath.move(b, 1, dt));
    }
}
