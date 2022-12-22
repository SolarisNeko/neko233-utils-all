package com.neko233.game.map.printer;

import com.neko233.game.map.Map3D;

/**
 * @author SolarisNeko
 * Date on 2022-12-11
 */
public class Map3DPrinter {

    /**
     * 2D 打印地图, x, y 轴
     *
     * @param map3D 3D 地图
     * @return 打印的 String
     */
    public static String printMap2D(Map3D map3D) {
        StringBuilder builder = new StringBuilder();
        for (int x = map3D.getXMinSize(); x < map3D.getXMaxSize(); x++) {
            for (int y = map3D.getYMinSize(); y < map3D.getYMaxSize(); y++) {
                builder.append(x).append(",").append(y).append(",").append(0)
                        .append("/").append(map3D.coordinate(x, y, 0));
                builder.append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
