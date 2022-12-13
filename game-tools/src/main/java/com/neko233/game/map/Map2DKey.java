package com.neko233.game.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Map2DKey {

    private int mapId;
    private int x;
    private int y;

    public static void main(String[] args) {
        List<Map2DKey> output = Map2DKey.generateXyAroundGridList(Map2DKey.builder()
                .mapId(0)
                .x(0)
                .y(0)
                .build()
        );

        System.out.println(output);
    }

    /**
     * 生成：【上下左右】移动 1 个单位的 List
     *
     * @param target 目标
     * @return 上下左右的生成姐u共
     */
    public static List<Map2DKey> generateXyAroundGridList(Map2DKey target) {
        List<Map2DKey> dataList = new ArrayList<>();
        int x = target.getX();
        int y = target.getY();

        for (int offset = -1; offset <= 1; offset++) {
            // target 不处理
            if (offset == 0) {
                continue;
            }
            // x
            Map2DKey xBuild = Map2DKey.builder()
                    .mapId(target.getMapId())
                    .x(x + offset)
                    .y(y)
                    .build();
            dataList.add(xBuild);
            // y
            Map2DKey yBuild = Map2DKey.builder()
                    .mapId(target.getMapId())
                    .x(x)
                    .y(y + offset)
                    .build();
            dataList.add(yBuild);
        }

        return dataList;
    }

    public static Map2DKey from(int x, int y) {
        return from(0, x, y);
    }

    public static Map2DKey from(int mapId, int x, int y) {
        return Map2DKey.builder()
                .mapId(mapId)
                .x(x)
                .y(y)
                .build();
    }

    public static Map<Map2DKey, Boolean> from2dGraphWithBoolean(int mapId, int[][] graph) {
        Map<Map2DKey, Boolean> map = new HashMap<>();
        for (int y = 0; y < graph.length; ++y) {
            for (int x = 0; x < graph[y].length; x++) {
                // 1 = 可以走 / 0 = 不可以走
                map.put(Map2DKey.from(mapId, x, y), graph[x][y] == 1);
            }
        }
        return map;
    }
}