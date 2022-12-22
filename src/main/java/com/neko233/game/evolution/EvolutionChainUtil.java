package com.neko233.game.evolution;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 进化工具
 *
 * @author SolarisNeko
 * Date on 2022-12-10
 */
@Slf4j
public class EvolutionChainUtil {

    /**
     * 进化单向链路 Map
     *
     * @param dataList d
     * @param <T>      any
     * @return Map
     */
    public <T> Map<T, T> generateSingleEvolutionChainMap(List<EvolutionApi<T>> dataList) {
        return Optional.ofNullable(dataList).orElse(new ArrayList<>())
                .stream()
                .collect(Collectors.toMap(
                        EvolutionApi::evolutionId,
                        EvolutionApi::nextEvolutionId,
                        (v1, v2) -> v2
                ));
    }
}
