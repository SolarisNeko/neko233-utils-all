package com.neko233.game.map;

import lombok.Data;

import java.util.Map;

/**
 * @author SolarisNeko
 * Date on 2022-12-10
 */
@Data
public class Map3D {

    // state
    private Map<Coordinate3d, Grid> map3D;     // 坐标 : 格子

    // agg - min
    private int xMinSize;
    private int yMinSize;
    private int zMinSize;
    // agg - max
    private int xMaxSize;
    private int yMaxSize;
    private int zMaxSize;


    public Map3D(Map<Coordinate3d, Grid> map3D) {
        this.map3D = map3D;
        this.xMaxSize = 0;
        this.yMaxSize = 0;
        this.zMaxSize = 0;
        map3D.keySet()
                .forEach(coordinate3D -> {
                    if (coordinate3D == null) {
                        return;
                    }
                    // min
                    if (coordinate3D.getX() != 0 && coordinate3D.getX() < xMinSize) {
                        this.xMinSize = coordinate3D.getX();
                    }
                    if (coordinate3D.getY() != 0 && coordinate3D.getY() < yMinSize) {
                        this.yMinSize = coordinate3D.getY();
                    }
                    if (coordinate3D.getZ() != 0 && coordinate3D.getZ() < zMinSize) {
                        this.zMinSize = coordinate3D.getZ();
                    }
                    // max
                    if (coordinate3D.getX() != 0 && coordinate3D.getX() > xMaxSize) {
                        this.xMaxSize = coordinate3D.getX();
                    }
                    if (coordinate3D.getY() != 0 && coordinate3D.getY() > yMaxSize) {
                        this.yMaxSize = coordinate3D.getY();
                    }
                    if (coordinate3D.getZ() != 0 && coordinate3D.getZ() > zMaxSize) {
                        this.zMaxSize = coordinate3D.getZ();
                    }
                });

    }

    public static void swapGrid(Map3D map3D,
                                int fromX, int fromY, int fromZ,
                                int toX, int toY, int toZ) {
        Coordinate3d from = Coordinate3d.from(fromX, fromY, fromZ);
        Coordinate3d to = Coordinate3d.from(toX, toY, toZ);

        Grid originalGrid = map3D.coordinate(from);
        Grid targetGrid = map3D.coordinate(to);

        map3D.putGrid(from, targetGrid)
                .putGrid(to, originalGrid);
    }


    public Grid coordinate(int x, int y, int z) {
        return coordinate(Coordinate3d.from(x, y, z));
    }

    public Grid coordinate(Coordinate3d coordinate3D) {
        return map3D.get(coordinate3D);
    }

    public Map3D putGrid(Coordinate3d coordinate3D, Grid grid) {
        map3D.put(coordinate3D, grid);
        return this;
    }


    public int getThingId(Coordinate3d coordinate3D) {
        Grid grid = map3D.get(coordinate3D);
        if (grid == null) {
            return -1;
        }
        return grid.getThingId();
    }

    public Grid getGrid(Coordinate3d coordinate3D) {
        return map3D.get(coordinate3D);
    }

}
