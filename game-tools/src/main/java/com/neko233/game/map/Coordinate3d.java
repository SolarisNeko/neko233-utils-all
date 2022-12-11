package com.neko233.game.map;

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
public class Coordinate3d {

    private int x;
    private int y;
    private int z;

    public static Coordinate3d from(int x, int y, int z) {
        return Coordinate3d.builder()
                .x(x)
                .y(y)
                .z(z)
                .build();
    }

}
