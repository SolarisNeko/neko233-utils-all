package com.neko233.game.map;

import com.neko233.game.map.key.Map2DKey;
import lombok.Data;

import java.util.Map;

/**
 * @author SolarisNeko
 * Date on 2022-12-10
 */
@Data
public class Map2D {

    // state
    private Map<Map2DKey, Grid> map3D;     // 坐标 : 格子

    // agg - min
    private int xMinSize;
    private int yMinSize;
    // agg - max
    private int xMaxSize;
    private int yMaxSize;


    public Map2D(Map<Map2DKey, Grid> map3D) {
        this.map3D = map3D;
        this.xMaxSize = 0;
        this.yMaxSize = 0;
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
                    // max
                    if (coordinate3D.getX() != 0 && coordinate3D.getX() > xMaxSize) {
                        this.xMaxSize = coordinate3D.getX();
                    }
                    if (coordinate3D.getY() != 0 && coordinate3D.getY() > yMaxSize) {
                        this.yMaxSize = coordinate3D.getY();
                    }
                });

    }

    public static void swapGrid(Map2D map3D,
                                int fromX, int fromY,
                                int toX, int toY) {
        Map2DKey from = Map2DKey.from(fromX, fromY);
        Map2DKey to = Map2DKey.from(toX, toY);

        Grid originalGrid = map3D.coordinate(from);
        Grid targetGrid = map3D.coordinate(to);

        map3D.putGrid(from, targetGrid)
                .putGrid(to, originalGrid);
    }


    public Grid coordinate(int x, int y, int z) {
        return coordinate(Map2DKey.from(x, y, z));
    }

    public Grid coordinate(Map2DKey coordinate3D) {
        return map3D.get(coordinate3D);
    }

    public Map2D putGrid(Map2DKey coordinate3D, Grid grid) {
        map3D.put(coordinate3D, grid);
        return this;
    }


    public int getThingId(Map2DKey coordinate3D) {
        Grid grid = map3D.get(coordinate3D);
        if (grid == null) {
            return -1;
        }
        return grid.getThingId();
    }

    public Grid getGrid(Map2DKey coordinate3D) {
        return map3D.get(coordinate3D);
    }

}
