package com.neko233.game.roundGame.command;

import com.neko233.game.roundGame.RoundGameContext;
import com.neko233.game.roundGame.order.PlayerOrder;
import com.neko233.game.roundGame.order.PlayerOrderDone;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 回合中, 玩家接收玩家给的指令
 *
 * @author SolarisNeko
 * Date on 2022-12-12
 */
@Slf4j
public class RoundApiMiddle implements RoundApi {


    @Override
    public void execute(RoundGameContext context) throws InterruptedException {
        long startMs = System.currentTimeMillis();
        while (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startMs) < 10) {
            PlayerOrder playerOrder = context.takeCurrentUserOrder();
            if (playerOrder == null) {
                TimeUnit.MILLISECONDS.sleep(50);
                continue;
            }

            if (playerOrder instanceof PlayerOrderDone) {
                break;
            }

            // 是否当前操作用户
            Long userId = playerOrder.player().userId();
            if (!Objects.equals(userId, context.getCurrentRoundUserId())) {
                log.warn("userId = {}, send illegal order name = {}", userId, playerOrder.orderName());
                continue;
            }

            // 执行某个指令成功后, 就可以结束了
            playerOrder.execute(context);
        }


    }


}
