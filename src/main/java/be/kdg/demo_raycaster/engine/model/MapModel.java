package be.kdg.demo_raycaster.engine.model;

public class MapModel {

    private final int[][] grid = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1} };


    //wee what texture needs to be used for the specified wall
    public int getCell(int x, int y)
    {
        if(x<0 || y<0 || y>= grid.length || x>= grid[0].length)
        {
            return 1;
        }
        return grid[x][y];
    }

    public Boolean isWall(int x, int y)
    {
        //wall detection algorithm
        if(x<0 || y<0 || y>= grid.length || x>= grid[0].length)
        {
            return true;
        }
        return  grid[y][x] == 1;
    }
}
