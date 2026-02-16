package be.kdg.demo_raycaster.engine.model;

public class Raycaster {
    private final MapModel map;

    public Raycaster(MapModel map) {
        this.map = map;
    }

    public double castRay(double px, double py, double rayDirX, double rayDirY) {
        int mapX = (int) px;
        int mapY = (int) py;

        double deltaDistX = (rayDirX == 0) ? 1e30 : Math.abs(1.0 / rayDirX);
        double deltaDistY = (rayDirY == 0) ? 1e30 : Math.abs(1.0 / rayDirY);

        double sideDistX, sideDistY;
        int stepX, stepY;


    // Step 1: check the directon of the vector (on the X axis, is our vector going towards infinity or towards zero?)
        if (rayDirX < 0) {
            stepX = -1;
            sideDistX = (px - mapX) * deltaDistX;
        } else {
            stepX = 1;
            sideDistX = (mapX + 1.0 - px) * deltaDistX;
        }
    // Step 2: check the directop of the vector (on the Y axis, is our vector going up or down? towards infinity or towards zero?)
        if (rayDirY < 0) {
            stepY = -1;
            sideDistY = (py - mapY) * deltaDistY;
        } else {
            stepY = 1;
            sideDistY = (mapY + 1.0 - py) * deltaDistY;
        }

        boolean hit = false;
        int side = 0;

        while (!hit) {
            if (sideDistX < sideDistY) {
                sideDistX += deltaDistX;
                mapX += stepX;
                side = 0;
            } else {
                sideDistY += deltaDistY;   // FIXED
                mapY += stepY;
                side = 1;
            }

            if (map.isWall(mapX, mapY)) hit = true;
        }

        return (side == 0) ? (sideDistX - deltaDistX) : (sideDistY - deltaDistY);
    }
}
