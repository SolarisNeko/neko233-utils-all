package com.neko233.game.map.key;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2022-12-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Map3DKey {

    private int mapId;
    private int x;
    private int y;
    private int z;

    public static Map3DKey from(int x, int y, int z) {
        return from(0, x, y, z);
    }

    public static Map3DKey from(int mapId, int x, int y, int z) {
        return Map3DKey.builder()
                .mapId(mapId)
                .x(x)
                .y(y)
                .z(z)
                .build();
    }

}
