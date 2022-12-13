package com.neko233.game.roundGame.command;

import com.neko233.game.roundGame.RoundGameContext;

/**
 * 回合制命令
 *
 * @author SolarisNeko
 * Date on 2022-12-12
 */

public interface RoundApi {

    void execute(RoundGameContext context) throws InterruptedException;

}
