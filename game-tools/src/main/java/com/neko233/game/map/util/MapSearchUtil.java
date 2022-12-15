package com.neko233.game.map.util;

import com.neko233.game.map.Map2DKey;

import java.util.*;

/**
 * 图搜索工具
 *
 * @author SN on 2022-12-11
 */
public class MapSearchUtil {

    private MapSearchUtil() {
    }

    /**
     * DFS 2D 地图
     *
     * @param target  目标起点
     * @param map     地图 : 是否可走
     * @param markMap 地图标记 : 是否已经走过
     * @param out     输出的 list, 请务必清空. Java 弊端
     */
    public static void dfs2D(Map2DKey target, Map<Map2DKey, Boolean> map, Map<Map2DKey, Boolean> markMap, List<Map2DKey> out) {
        if (out == null) {
            return;
        }
        // init
        if (markMap == null) {
            markMap = new HashMap<>();
        }
        if (target == null) {
            return;
        }

        // check
        if (isComeHere(markMap, target)) {
            return;
        }
        if (isCanGo(map, target)) {
            return;
        }

        // mark
        markMap.put(target, true);

        List<Map2DKey> toTravelList = Map2DKey.generateXyAroundGridList(Map2DKey.builder()
                .mapId(target.getMapId())
                .x(target.getX())
                .y(target.getY())
                .build()
        );

        out.add(target);

        for (Map2DKey newGrid : toTravelList) {
            dfs2D(newGrid, map, markMap, out);
        }
    }


    /**
     * BFS 2D 地图
     *
     * @param target   目标点
     * @param map2D    2D 地图
     * @param markMap  标记地图哪些已经走过, can null
     * @param bfsQueue 队列, can null
     * @param out      输出的结果
     */
    public static void bfs2D(Map2DKey target, Map<Map2DKey, Boolean> map2D, Map<Map2DKey, Boolean> markMap, Queue<Map2DKey> bfsQueue, List<Map2DKey> out) {
        // init
        if (markMap == null) {
            markMap = new HashMap<>();
        }
        if (bfsQueue == null) {
            bfsQueue = new PriorityQueue<>(map2D.size());
        }
        if (target == null && bfsQueue.size() == 0) {
            return;
        }
        if (target == null) {
            target = bfsQueue.remove();
        }
        if (target == null) {
            return;
        }
        // check
        if (isComeHere(markMap, target)) {
            return;
        }
        if (isCanGo(map2D, target)) {
            return;
        }

        // mark
        markMap.put(target, true);

        List<Map2DKey> toTravelList = Map2DKey.generateXyAroundGridList(Map2DKey.builder()
                .mapId(target.getMapId())
                .x(target.getX())
                .y(target.getY())
                .build()
        );

        // exclude have mark visit node
        for (Map2DKey newGrid : toTravelList) {
            // 是否访问过 | 默认没访问过
            if (isComeHere(markMap, newGrid)) {
                continue;
            }
            // 是否可以走
            if (isCanGo(map2D, newGrid)) {
                continue;
            }
            bfsQueue.add(newGrid);
            out.add(newGrid);
        }

        int snapshotSize = bfsQueue.size();
        for (int i = 0; i < snapshotSize; i++) {
            bfs2D(null, map2D, markMap, bfsQueue, out);
        }

    }

    private static boolean isCanGo(Map<Map2DKey, Boolean> map, Map2DKey newGrid) {
        return !map.getOrDefault(newGrid, false);
    }

    private static boolean isComeHere(Map<Map2DKey, Boolean> markMap, Map2DKey newGrid) {
        return markMap.getOrDefault(newGrid, false);
    }
}
