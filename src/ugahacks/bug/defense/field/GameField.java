package ugahacks.bug.defense.field;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
        super(640 / 2f, 480 / 2f);
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

        setOnMouseMoved(this::onMouseOver);
        setOnMouseReleased(this::onClick);

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
        debuggers = new ArrayList<>();
        bugs = new ArrayList<>();
        mainPath = new Path(new Pos(0, 0), new Pos(0, 0));
    }

    /**
     * This method draws all of the Objects in the game.
     * @param g the GraphicsContext to be drawn on
     */
    public void draw(GraphicsContext g) {
        g.setFill(Color.BLACK);
        g.fill();

        g.setFill(Color.WHITE);
        Path current = mainPath;
        while(current != null) {
            g.strokeLine(current.start.x, current.start.y, current.end.x, current.end.y);
            current = current.nextPath;
        }

        debuggers.forEach(d -> d.draw(g));
        bugs.forEach(b -> b.draw(g));
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
        // TODO
        // if in debugger buy mode
        // show range of tower,
    }

    public void onClick(MouseEvent e) {
        // TODO
        // if in debugger buy mode
        // put on board
    }
}
