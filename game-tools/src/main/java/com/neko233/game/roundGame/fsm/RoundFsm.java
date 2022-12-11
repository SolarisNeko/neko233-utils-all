package com.neko233.game.roundGame.fsm;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2022-12-12
 */
@Data
public class RoundFsm {

    private Boolean isFinish;
    private List<Long> usernameGroup; // 玩家们
    private Long currentOperateUserId;

    public RoundFsm(List<Long> joinUserIdList) {
        this.usernameGroup = new ArrayList<>(joinUserIdList);
        try {
            currentOperateUserId = joinUserIdList.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        isFinish = false;
    }

    public void start() {


    }

    public void update() {


    }

    public void end() {
        isFinish = true;
    }
}
