package com.neko233.game.roundGame;

import com.google.common.collect.Lists;
import com.neko233.game.common.player.Player;
import com.neko233.game.roundGame.command.*;
import com.neko233.game.roundGame.order.PlayerOrder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
    // meta
    private final String uuid = UUID.randomUUID().toString();
    private final List<Player> players; // 分队伍的棋子
    private final Map<Long, List<RoundActor>> userIdMap = new HashMap<>(); // 双方阵容
    // state
    private Long maxSpeed; // 最大行动速度
    private final List<RoundActor> actionLink = new LinkedList<>(); // 行动条
    private List<Long> joinUserIdList; // 参与的玩家列表
    // current
    private int currentRoundCount = 1; // 当前回合. default 1
    public static final int MAX_ROUND_COUNT = 9999;
    private Long currentRoundUserId; // 当前操作的用户ID
    private final Queue<Long> operateQueue = new LinkedBlockingQueue<>(); // 实现轮流发起指令

    // player order
    private final BlockingQueue<PlayerOrder> orderQueue = new LinkedBlockingQueue<>(); // 实现轮流发起指令


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
                .sorted(RoundActor.speedSortByDesc())
                .collect(Collectors.toList());
        actionLink.addAll(speedSortLinkedList);
        try {
            RoundActor firstSpeedActor = speedSortLinkedList.get(0);
            maxSpeed = firstSpeedActor.getSpeed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        joinUserIdList = new ArrayList<>(userIdMap.keySet());
        operateQueue.addAll(joinUserIdList);
        currentRoundUserId = joinUserIdList.get(0);
    }


    public RoundGameContext addOrder(PlayerOrder order) {
        if (order == null) {
            return this;
        }
        orderQueue.add(order);
        return this;
    }

    public PlayerOrder takeCurrentUserOrder() {
        try {
            return this.orderQueue.remove();
        } catch (NoSuchElementException e) {
            return null;
        }
    }


    /**
     * 周期回合链路
     */
    private List<RoundApi> actionChainTemplate = Lists.newArrayList(
            new RoundApiPreStart(),
            new RoundApiStart(),
            new RoundApiPostStart(),
            new RoundApiMiddle(),
            new RoundApiPreStop(),
            new RoundApiStop(),
            new RoundApiPostStop()
    );

    /**
     * 游戏调度 Template
     */
    public void scheduleGame() {
        try {

            new GameApiAllBegin().execute(this);

            // lifecycle
            while (!isFinish && currentRoundCount < MAX_ROUND_COUNT) {
                Long userId = operateQueue.remove();
                if (userId == null) {
                    log.info("wtf userId is null");
                    break;
                }
                operateQueue.add(userId);

                // 当前操作用户
                this.currentRoundUserId = userId;
                log.info("current round userId = {}", userId);

                for (RoundApi roundApi : actionChainTemplate) {
                    if (isFinish) {
                        log.info("Round Game is finished. uuid = {}", uuid);
                        break;
                    }
                    roundApi.execute(this);
                }

                currentRoundCount += 1;
            }

            new GameAllFinishApi().execute(this);

        } catch (InterruptedException e) {
            isFinish = true;
            log.error("game is shutdown. ", e);
        }
    }


}
