package com.neko233.game.roundGame;

import com.google.common.collect.Lists;
import com.neko233.game.common.Player;
import com.neko233.game.roundGame.command.RoundCommand;
import com.neko233.game.roundGame.fsm.RoundFsm;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 回合制 单场战斗
 *
 * @author SolarisNeko
 * Date on 2022-12-11
 */
@Data
public class RoundGameContext {


    private final List<Player> players; // 分队伍的棋子
    private final Map<Long, List<RoundActor>> userIdMap = new HashMap<>(); // 分队伍的棋子
    // state
    private Long maxSpeed; // 行动条
    private final List<RoundActor> actionLink = new LinkedList<>(); // 行动条
    private RoundFsm fsm; // 全局游戏状态


    public RoundGameContext(List<Player> players, List<RoundActor> allActorList) {
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

        fsm = new RoundFsm(new ArrayList<>(userIdMap.keySet()));
    }

    public void command(RoundCommand command) {

    }


    /**
     * 开始游戏
     */
    public void startGame() {
        fsm.start();
    }

    public void update() {
        if (fsm.getIsFinish()) {
            notifyGameStop();
            return;
        }

        updateByBusiness();

    }

    private void updateByBusiness() {

    }

    private void notifyGameStop() {

    }

}
