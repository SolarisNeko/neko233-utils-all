package com.neko233.game.roundGame;

import com.alibaba.fastjson2.JSON;
import com.neko233.game.common.Player;
import com.neko233.game.common.SimplePlayer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2022-12-12
 */
public class RoundGameContextTest {

    @Test
    public void testActionChain() {
        ArrayList<RoundActor> list = new ArrayList<RoundActor>() {{
            add(RoundActor.builder().userId(1L).petId(1).speed(5L).build());
            add(RoundActor.builder().userId(1L).petId(2).speed(1L).build());
            add(RoundActor.builder().userId(1L).petId(3).speed(1L).build());
            add(RoundActor.builder().userId(2L).petId(4).speed(1L).build());
            add(RoundActor.builder().userId(2L).petId(5).speed(1L).build());
            add(RoundActor.builder().userId(2L).petId(6).speed(1L).build());
        }};

        List<Player> players = new ArrayList<Player>() {{
            add(SimplePlayer.builder().userId(1L).build());
            add(SimplePlayer.builder().userId(2L).build());
        }};

        RoundGameContext roundGameContext = new RoundGameContext(players, list);

        System.out.println(JSON.toJSONString(roundGameContext));
    }

}