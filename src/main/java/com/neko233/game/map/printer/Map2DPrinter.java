package com.neko233.game.map.printer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SolarisNeko on 2022-12-11
 **/
public class Map2DPrinter {

    public static void main(String[] args) {
        // 这个矩阵是转置了 +90°
        Integer[][] demo2d = new Integer[][]{
                {0, 1, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 1, 0}
        };


        Integer[][] demo2d_2 = new Integer[][]{
                {1, 1, 1},
                {0, 1, 0},
                {0, 1},
        };

        List<String> xyList = Map2DPrinter.get2dGraphHumanlyWithFormat(demo2d);
        xyList.forEach(System.out::print);
    }

    public static void print2dGraphHumanly(Object[][] array2D) {
        List<String> xyList = get2dGraphHumanlyWithFormat(array2D);
        xyList.forEach(System.out::print);
    }

    /**
     * 将二维数组打印成 humanly
     * Iterable index like
     * [0, y.max] ... [x.max, y.max]
     * [0, y.max-1] ... [x.max, y.max-1]
     *
     * @param array2D 二维数组
     */
    public static List<String> get2dGraphHumanly(Object[][] array2D) {
        List<String> xyBuilder = new ArrayList<>();
        for (int y = array2D[0].length - 1; y >= 0; y--) {
            for (int x = 0; x < array2D.length; x++) {
                try {
                    xyBuilder.add(String.valueOf(array2D[x][y]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    xyBuilder.add("/");
                }
                xyBuilder.add("\t");
            }
            xyBuilder.add("\n");
        }
        return xyBuilder;
    }

    /**
     * rotate -90 的 array2D, 正常数学视图
     *
     * @param array2D 二维数组, 需要使用包装类. 基本数据类型兼容太麻烦
     * @return 待打印的信息
     */
    public static List<String> get2dGraphHumanlyWithFormat(Object[][] array2D) {
        List<String> xyBuilder = new ArrayList<>();
        for (int y = array2D[0].length - 1; y >= 0; y--) {
            // edge
            xyBuilder.add(y + " |\t");

            for (int x = 0; x < array2D.length; x++) {
                try {
                    xyBuilder.add(String.valueOf(array2D[x][y]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    xyBuilder.add("/");
                }
                xyBuilder.add("\t");
            }
            xyBuilder.add("\n");
        }

        // y-Axis Tips
        xyBuilder.add("y |\t");
        for (int y = 0; y < array2D[0].length; y++) {
            xyBuilder.add("- \t");
        }
        // x-Axis Tips
        xyBuilder.add("\n");
        xyBuilder.add("\\ x\t");
        for (int x = 0; x < array2D.length; x++) {
            xyBuilder.add(x + "\t");
        }
        return xyBuilder;
    }
}
