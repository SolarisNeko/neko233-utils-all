package com.neko233.game.roundGame.order;

import com.neko233.game.common.player.Player;
import com.neko233.game.roundGame.RoundGameContext;

/**
 * 指令
 *
 * @author SolarisNeko
 * Date on 2022-12-13
 */
public interface PlayerOrder {

    Player player();

    String orderName();

    void execute(RoundGameContext context);

}
