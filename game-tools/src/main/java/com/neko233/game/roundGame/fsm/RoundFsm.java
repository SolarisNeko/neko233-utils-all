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
    private List<String> usernameGroup; // 玩家们
    private String currentOperateUserName;

    public RoundFsm(Collection<String> usernameGroup) {
        this.usernameGroup = new ArrayList<>(usernameGroup);
        currentOperateUserName = usernameGroup.stream().findFirst().orElseThrow(() -> {
            throw new RuntimeException("first state null");
        });
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
