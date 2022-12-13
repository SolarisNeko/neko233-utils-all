package com.neko233.game.common.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2022-12-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimplePlayer implements Player {

    private Long userId;


    @Override
    public Long userId() {
        return userId;
    }

}
