package com.neko233.game.roundGame;

import com.google.common.collect.Lists;
import com.neko233.game.roundGame.fsm.RoundFsm;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 回合制 单场战斗
 *
 * @author SolarisNeko
 * Date on 2022-12-11
 */
@Data
public class RoundGameContext {


    private final Map<String, List<RoundActor>> groupMap = new HashMap<>(); // 分队伍的棋子
    // state
    private Long maxSpeed; // 行动条
    private final List<RoundActor> actionLink = new LinkedList<>(); // 行动条
    private RoundFsm fsm; // 全局游戏状态


    public RoundGameContext(List<RoundActor> allActorList) {
        Map<String, List<RoundActor>> collect = allActorList.stream()
                .collect(Collectors.toMap(RoundActor::getGroup, Lists::newArrayList, (v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        }
                ));
        this.groupMap.putAll(collect);

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
        RoundActor firstSpeedActor = speedSortLinkedList.stream().findFirst().orElseThrow(() -> {
            throw new RuntimeException("not found the action chain first element ");
        });
        maxSpeed = firstSpeedActor.getSpeed();

        fsm = new RoundFsm(collect.keySet());
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
