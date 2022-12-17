package com.neko233.game.map.util;

import com.google.common.collect.Lists;
import com.neko233.game.map.key.Map3DKey;
import com.neko233.game.map.Grid;
import com.neko233.game.map.Map3D;

import java.util.*;

/**
 * 消除/合成类类 Game Tool
 *
 * @author SolarisNeko on 2022-11-21
 **/
public class ComposeGameUtil {

    /**
     * BFS scan around target point ｜ BFS 扫描目标范围点的 9 宫格
     *
     * @param map3D   3D 地图
     * @param targetX 目标x轴
     * @param targetY 目标y轴
     * @return 基于 Grid 聚合的 Map
     */
    public static ScanGridResult scanTargetPointAround9Grid(Map3D map3D, int targetX, int targetY) {
        Grid targetGrid = map3D.coordinate(targetX, targetY, 0);
        if (targetGrid == null) {
            return null;
        }


        List<Map3DKey> needCheckCoordinateList = generateToCheckCoordinateList(targetX, targetY, null);

        // 物品, 序号集合
        Map<Grid, List<Map3DKey>> sameThingMap = new HashMap<>(9);

        // 自动地图限制
        for (Map3DKey coordinate3D : needCheckCoordinateList) {
            Grid grid = map3D.getGrid(coordinate3D);
            if (grid == null) {
                continue;
            }
            sameThingMap.merge(grid, Lists.newArrayList(coordinate3D), (v1, v2) -> {
                v1.addAll(v2);
                return v1;
            });
        }

        return ScanGridResult.builder()
                .centerGrid(targetGrid)
                .aggByGridMap(sameThingMap)
                .build();


    }

    private static List<Map3DKey> generateToCheckCoordinateList(Integer x, Integer y, Integer z) {
        int[] range = new int[]{-1, 0, 1};
        int dimension3D = 3;
        Set<Map3DKey> coordinate3DSet = new HashSet<>();

        int tempX = Optional.ofNullable(x).orElse(0);
        int tempY = Optional.ofNullable(y).orElse(0);
        int tempZ = Optional.ofNullable(z).orElse(0);
        for (int idx : range) {
            if (x != null) {
                coordinate3DSet.add(Map3DKey.builder()
                        .x(tempX + idx)
                        .y(tempY)
                        .z(tempZ)
                        .build()
                );
            }
            if (y != null) {
                coordinate3DSet.add(Map3DKey.builder()
                        .x(tempX)
                        .y(tempY + idx)
                        .z(tempZ)
                        .build()
                );
            }
            if (z != null) {
                coordinate3DSet.add(Map3DKey.builder()
                        .x(tempX)
                        .y(tempY)
                        .z(tempZ + idx)
                        .build()
                );
            }
        }

        return new ArrayList<>(coordinate3DSet);
    }


    /**
     * 九宫格序号生成 | 自动检查墙壁
     *
     * @param targetX  目标点x
     * @param targetY  目标点y
     * @param xMaxSize 最大x 大小
     * @param yMaxSize 最大 y 大小
     * @return 返回合法的九宫格 CSSV
     */
    public static List<String> generateCsvForNineAreaList(int targetX, int targetY,
                                                          int xMaxSize, int yMaxSize) {
        List<String> mapPositionList = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            int x = targetX + i;
            if (x < 0) {
                continue;
            }
            if (x > xMaxSize - 1) {
                continue;
            }

            for (int j = -1; j <= 1; j++) {
                int y = targetY + j;
                if (y < 0) {
                    continue;
                }
                if (y > yMaxSize - 1) {
                    continue;
                }
                mapPositionList.add(x + "," + y);
            }
        }

        return mapPositionList;
    }


    public static List<Map3DKey> findCanMergeList(ScanGridResult scanGridResult) {
        // default 3 compose
        return findCanMergeList(scanGridResult, 3);
    }

    public static List<Map3DKey> findCanMergeList(ScanGridResult scanGridResult, int limitSize) {
        if (limitSize <= 0) {
            return new ArrayList<>();
        }
        Grid centerGrid = scanGridResult.getCenterGrid();
        if (centerGrid == null) {
            return new ArrayList<>();
        }

        // 检查九宫格, 是否满足3消条件
        Map<Grid, List<Map3DKey>> aggGridMap = Optional.ofNullable(scanGridResult.getAggByGridMap()).orElse(new HashMap<>());

        // 连成一片才能返回
        List<Map3DKey> chainGridList = new ArrayList<>();
        for (Map.Entry<Grid, List<Map3DKey>> gridListEntry : aggGridMap.entrySet()) {
            Grid scanGrid = gridListEntry.getKey();
            List<Map3DKey> sameGridCoordinate = gridListEntry.getValue();

            if (!centerGrid.equals(scanGrid)) {
                continue;
            }
            //
            if (sameGridCoordinate.size() >= limitSize) {
                chainGridList.addAll(sameGridCoordinate);
                return chainGridList;
            }
        }
        return new ArrayList<>();
    }
}
