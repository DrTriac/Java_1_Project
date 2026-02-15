package be.kdg.demo_raycaster.engine.model;

public class PlayerModel {
    public double x,y,dir;
    // graphics mathematics, get a sertain view-angle
    public double fov = Math.toRadians(60);
    //initialise player movement speed and rotation speed
    public double moveSpeed = 3.0;
    public double rotSpeed = 2.0;

    //get the player to move
    public PlayerModel(double x, double y, double dir)
    {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
}
