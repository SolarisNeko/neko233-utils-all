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
     * DFS
     *
     * @param target  目标起点
     * @param map     地图 : 是否可走
     * @param markMap 地图标记 : 是否已经走过
     * @param out     输出的 list, 请务必清空. Java 弊端
     */
    public static void dfs(Map2DKey target, Map<Map2DKey, Boolean> map, Map<Map2DKey, Boolean> markMap, List<Map2DKey> out) {
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
            dfs(newGrid, map, markMap, out);
        }
    }


    /**
     * @param target
     * @param map
     * @param markMap
     * @param bfsQueue
     * @param out
     */
    public static void bfs(Map2DKey target, Map<Map2DKey, Boolean> map, Map<Map2DKey, Boolean> markMap, Queue<Map2DKey> bfsQueue, List<Map2DKey> out) {
        // init
        if (markMap == null) {
            markMap = new HashMap<>();
        }
        if (bfsQueue == null) {
            bfsQueue = new PriorityQueue<>(map.size());
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

        // exclude have mark visit node
        for (Map2DKey newGrid : toTravelList) {
            // 是否访问过 | 默认没访问过
            if (isComeHere(markMap, newGrid)) {
                continue;
            }
            // 是否可以走
            if (isCanGo(map, newGrid)) {
                continue;
            }
            bfsQueue.add(newGrid);
            out.add(newGrid);
        }

        int snapshotSize = bfsQueue.size();
        for (int i = 0; i < snapshotSize; i++) {
            bfs(null, map, markMap, bfsQueue, out);
        }

    }

    private static boolean isCanGo(Map<Map2DKey, Boolean> map, Map2DKey newGrid) {
        return !map.getOrDefault(newGrid, false);
    }

    private static boolean isComeHere(Map<Map2DKey, Boolean> markMap, Map2DKey newGrid) {
        return markMap.getOrDefault(newGrid, false);
    }
}
