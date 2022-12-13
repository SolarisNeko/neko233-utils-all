package com.neko233.game.roundGame.order;

import com.alibaba.fastjson2.JSON;
import com.neko233.game.common.player.Player;
import com.neko233.game.roundGame.RoundGameContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2022-12-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerOrderPing implements PlayerOrder {

    private Player player;

    @Override
    public Player player() {
        return this.player;
    }

    @Override
    public String orderName() {
        return "ping";
    }

    @Override
    public void execute(RoundGameContext context) {
        System.out.println(player.userId() + " : " + JSON.toJSONString(context));
    }
}
