package com.neko233.game.map.util;

import com.alibaba.fastjson2.JSON;
import com.neko233.game.map.key.Map2DKey;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public class MapSearchUtilTest {

    @Test
    public void testDfs2D() {
        Map2DKey target = Map2DKey.builder()
                .mapId(0)
                .x(1)
                .y(1)
                .build();


        Map<Map2DKey, Boolean> map = Map2DKey.from2dGraphWithBoolean(0, DEMO_2D_GRAPH);

        List<Map2DKey> out = new ArrayList<>();

        MapSearchUtil.dfs2D(target, map, null, out);
        System.out.println(JSON.toJSONString(out));
    }

    @Test
    public void testBfs2D() {
        Map2DKey target = Map2DKey.builder()
                .mapId(0)
                .x(1)
                .y(1)
                .build();


        Map<Map2DKey, Boolean> map = Map2DKey.from2dGraphWithBoolean(0, DEMO_2D_GRAPH);

        List<Map2DKey> out = new ArrayList<>();

        // BFS
        MapSearchUtil.bfs2D(target, map, null, null, out);
        System.out.println(JSON.toJSONString(out));
    }


    /**
     * 这个矩阵是转置了 +90°
     */
    private final static Integer[][] DEMO_2D_GRAPH = new Integer[][]{
            {0, 1, 1, 1, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0}
    };


}