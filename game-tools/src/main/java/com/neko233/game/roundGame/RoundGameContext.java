package com.neko233.game.roundGame;

import com.google.common.collect.Lists;
import com.neko233.game.common.player.Player;
import com.neko233.game.roundGame.command.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 回合制 单场战斗
 *
 * @author SolarisNeko
 * Date on 2022-12-11
 */
@Slf4j
@Data
public class RoundGameContext {

    private Boolean isFinish;
    private final List<Player> players; // 分队伍的棋子
    private final Map<Long, List<RoundActor>> userIdMap = new HashMap<>(); // 分队伍的棋子
    // state
    private Long maxSpeed; // 行动条
    private final List<RoundActor> actionLink = new LinkedList<>(); // 行动条
    private List<Long> joinUserIdList; // 参与的玩家列表
    private Long currentOperateUserId; // 全局游戏状态


    public RoundGameContext(List<Player> players, List<RoundActor> allActorList) {
        this.isFinish = false;
        this.players = players;
        Map<Long, List<RoundActor>> userIdMap = allActorList.stream()
                .collect(Collectors.toMap(RoundActor::getUserId, Lists::newArrayList, (v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        }
                ));
        this.userIdMap.putAll(userIdMap);

        // 生成默认进度条
        List<RoundActor> speedSortLinkedList = allActorList.stream()
                .sorted((v1, v2) -> {
                    // 速度快的排前面
                    if (v1.getSpeed() > v2.getSpeed()) {
                        return -1;
                    }
                    return 1;
                })
                .collect(Collectors.toList());
        actionLink.addAll(speedSortLinkedList);
        try {
            RoundActor firstSpeedActor = speedSortLinkedList.get(0);
            maxSpeed = firstSpeedActor.getSpeed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        joinUserIdList = new ArrayList<>(userIdMap.keySet());
        currentOperateUserId = joinUserIdList.get(0);
    }

    /**
     * 周期回合链路
     */
    private List<RoundCommand> roundCommandChain = Lists.newArrayList(
            new RoundPreStartCommand(),
            new RoundStartCommand(),
            new RoundPostStartCommand(),
            new RoundMiddleCommand(),
            new RoundPreStopCommand(),
            new RoundStopCommand(),
            new RoundPostStopCommand()
    );

    /**
     * 游戏调度
     */
    public void scheduleGame() {
        try {
            new GameBeginCommand().execute(this);

            // lifecycle
            for (RoundCommand roundCommand : roundCommandChain) {
                if (isFinish) {
                    break;
                }
                roundCommand.execute(this);
            }

            new GameFinishCommand().execute(this);
        } catch (InterruptedException e) {
            isFinish = true;
            log.error("game is shutdown. ", e);


        }
    }


}
