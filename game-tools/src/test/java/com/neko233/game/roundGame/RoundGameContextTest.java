package com.neko233.game.roundGame;

import com.alibaba.fastjson2.JSON;
import com.neko233.game.common.player.Player;
import com.neko233.game.common.player.SimplePlayer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2022-12-12
 */
public class RoundGameContextTest {

    @Test
    public void testActionChain() throws InterruptedException {
        ArrayList<RoundActor> list = new ArrayList<RoundActor>() {{
            add(RoundActor.builder().userId(1L).petId(1).speed(5L).build());
            add(RoundActor.builder().userId(1L).petId(2).speed(1L).build());
            add(RoundActor.builder().userId(1L).petId(3).speed(1L).build());
            add(RoundActor.builder().userId(2L).petId(4).speed(1L).build());
            add(RoundActor.builder().userId(2L).petId(5).speed(1L).build());
            add(RoundActor.builder().userId(2L).petId(6).speed(1L).build());
        }};

        SimplePlayer build1 = SimplePlayer.builder().userId(1L).build();
        SimplePlayer build2 = SimplePlayer.builder().userId(2L).build();
        List<Player> players = new ArrayList<Player>() {{
            add(build1);
            add(build2);
        }};

        RoundGameContext roundGameContext = new RoundGameContext(players, list);
        System.out.println(JSON.toJSONString(roundGameContext));


        new Thread(() -> {
            roundGameContext.scheduleGame();
        }).start();

        TimeUnit.SECONDS.sleep(1);

        build1.setInputBody("hello, " + build1.userId());
        build2.setInputBody("hello, " + build2.userId());

        TimeUnit.SECONDS.sleep(10);
    }

}