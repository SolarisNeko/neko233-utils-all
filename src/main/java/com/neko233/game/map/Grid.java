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
public class Grid {

    private Boolean isOpen = true;
    private Integer thingId;

}
