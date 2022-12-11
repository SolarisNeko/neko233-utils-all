package com.neko233.game.roundGame;

import com.alibaba.fastjson2.JSON;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author SolarisNeko
 * Date on 2022-12-12
 */
public class RoundGameContextTest {

    @Test
    public void testActionChain() {
        ArrayList<RoundActor> list = new ArrayList<RoundActor>() {{
            add(RoundActor.builder().group("1").petId(1).speed(5).build());
            add(RoundActor.builder().group("1").petId(2).speed(1).build());
            add(RoundActor.builder().group("1").petId(3).speed(1).build());
            add(RoundActor.builder().group("2").petId(4).speed(1).build());
            add(RoundActor.builder().group("2").petId(5).speed(1).build());
            add(RoundActor.builder().group("2").petId(6).speed(1).build());
        }};
        RoundGameContext roundGameContext = new RoundGameContext(list);

        System.out.println(JSON.toJSONString(roundGameContext));
    }

}