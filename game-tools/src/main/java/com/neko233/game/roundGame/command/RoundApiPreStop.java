package com.neko233.game.roundGame.command;

import com.neko233.game.roundGame.RoundGameContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 回合开始
 *
 * @author SolarisNeko
 * Date on 2022-12-12
 */
@Slf4j
public class RoundApiPreStop implements RoundApi {

    @Override
    public void execute(RoundGameContext context) {
        log.info("Round = {}. current userId = {} operate finish.",
                context.getCurrentRoundCount(),
                context.getCurrentRoundUserId());
    }

}
