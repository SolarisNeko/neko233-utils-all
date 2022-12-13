package com.neko233.game.roundGame;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

/**
 * 回合制中的 Role | 无小数, 所有数据 * 100
 *
 * @author SolarisNeko
 * Date on 2022-12-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoundActor {

    private Long userId;
    private Integer petId;
    private String name;
    private Long hp;
    private Long attack;
    private Long defend;
    private Long speed;

    public static Comparator<RoundActor> speedSortByDesc() {
        return (v1, v2) -> {
            // 速度快的排前面
            if (v1.getSpeed() > v2.getSpeed()) {
                return -1;
            }
            return 1;
        };
    }
}
