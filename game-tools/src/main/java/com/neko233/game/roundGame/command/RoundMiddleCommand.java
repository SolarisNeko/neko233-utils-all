package com.neko233.game.roundGame.command;

import com.neko233.game.common.player.Player;
import com.neko233.game.roundGame.RoundGameContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 回合中, 玩家接收玩家给的指令
 *
 * @author SolarisNeko
 * Date on 2022-12-12
 */
@Slf4j
public class RoundMiddleCommand implements RoundCommand {

    private final Map<Long, String> userInputMap = new HashMap<>();

    @Override
    public void execute(RoundGameContext context) throws InterruptedException {
        userInputMap.clear();

        final List<Player> players = context.getPlayers();
        long startMs = System.currentTimeMillis();
        while (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startMs) < 10) {
            for (Player player : players) {
                if (userInputMap.get(player.userId()) != null) {
                    continue;
                }
                String inputBody = player.inputBody();
                if (StringUtils.isBlank(inputBody)) {
                    continue;
                }
                userInputMap.put(player.userId(), inputBody);
            }


            TimeUnit.MILLISECONDS.sleep(50);
        }


        // TODO 执行命令
        userInputMap.forEach((userId, inputBody) -> {
            log.info("body = {}", inputBody);
        });


        cleanAllCommand();
    }

    private void cleanAllCommand() {
        userInputMap.clear();
    }

}
