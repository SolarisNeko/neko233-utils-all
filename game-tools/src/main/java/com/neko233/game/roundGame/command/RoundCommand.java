package com.neko233.game.roundGame.command;

/**
 * 回合制命令
 *
 * @author SolarisNeko
 * Date on 2022-12-12
 */

public interface RoundCommand<T> {

    Long userId();

    String commandName();

    T data();

}
