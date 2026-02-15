package be.kdg.demo_raycaster.engine.presenter;

import be.kdg.demo_raycaster.engine.model.MapModel;
import be.kdg.demo_raycaster.engine.model.PlayerModel;
import be.kdg.demo_raycaster.engine.model.Raycaster;
import be.kdg.demo_raycaster.engine.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GamePresenter {

    private final GameView view;
    private final PlayerModel player;
    private final MapModel map;
    private final Raycaster raycaster;

    public GamePresenter(GameView view) {
        this.view = view;
        this.map = new MapModel();
        this.player = new PlayerModel(5.5, 5.5, 0.0);
        this.raycaster = new Raycaster(map);
    }

    public void startGameLoop() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) {
                    last = now;
                    return;
                }
                double dt = (now - last) / 1e9;
                last = now;

                update(dt);
                render();
            }
        }.start();
    }

    private void update(double dt) {
        double move = player.moveSpeed * dt;
        double rot = player.rotSpeed * dt;

        if (view.isKeyPressed(KeyCode.LEFT)) player.dir -= rot;
        if (view.isKeyPressed(KeyCode.RIGHT)) player.dir += rot;

        double dx = Math.cos(player.dir);
        double dy = Math.sin(player.dir);



        if (view.isKeyPressed(KeyCode.W)) tryMove(player.x + dx * move, player.y + dy * move);
        if (view.isKeyPressed(KeyCode.S)) tryMove(player.x - dx * move, player.y - dy * move);

        if (view.isKeyPressed(KeyCode.UP))    tryMove(player.x + dx * move, player.y + dy * move);
        if (view.isKeyPressed(KeyCode.DOWN))  tryMove(player.x - dx * move, player.y - dy * move);



        double sx = Math.cos(player.dir + Math.PI / 2);
        double sy = Math.sin(player.dir + Math.PI / 2);

        if (view.isKeyPressed(KeyCode.A)) tryMove(player.x - sx * move, player.y - sy * move);
        if (view.isKeyPressed(KeyCode.D)) tryMove(player.x + sx * move, player.y + sy * move);
    }

    private void tryMove(double nx, double ny) {
        if (!map.isWall((int) nx, (int) ny)) {
            player.x = nx;
            player.y = ny;
        }
    }

    private void render() {
        GraphicsContext gc = view.getGc();
        int w = GameView.WIDTH;
        int h = GameView.HEIGHT;

        // --- 1. Draw sky and floor ---
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0, 0, w, h / 2);

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, h / 2, w, h / 2);



        // --- 2. Player direction vector ---
        double dirX = Math.cos(player.dir);
        double dirY = Math.sin(player.dir);

        // --- 3. Camera plane (perpendicular to direction) ---
        // plane length = tan(FOV/2)
        double planeX = -dirY * Math.tan(player.fov / 2);
        double planeY =  dirX * Math.tan(player.fov / 2);

        // --- 4. Raycasting: one ray per vertical column ---
        for (int x = 0; x < w; x++) {

            // cameraX goes from -1 (left) to +1 (right)
            double cameraX = 2.0 * x / w - 1.0;

            // ray direction = direction + camera plane * cameraX
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;

            // distance to wall using DDA
            double dist = raycaster.castRay(player.x, player.y, rayDirX, rayDirY);

            // projected wall height
            int lineHeight = (int) (h / dist);

            int start = -lineHeight / 2 + h / 2;
            int end   =  lineHeight / 2 + h / 2;

            // simple shading: darker for far walls
            double shade = Math.max(0.2, 1.0 - dist / 10.0);

            gc.setStroke(Color.color(shade, 0, 0)); // red-ish shading

            gc.setFill(Color.color(shade, 0, 0));
            gc.fillRect(x, start, 1, end - start);


            gc.strokeLine(x, start, x, end);
            if (Double.isNaN(dist) || Double.isInfinite(dist) || dist > 1000) {
                System.out.println("BAD DIST: " + dist + " at column " + x);
            }

        }
    }

}



