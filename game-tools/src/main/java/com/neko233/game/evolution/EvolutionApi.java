package com.neko233.game.evolution;

/**
 * @author SolarisNeko
 * Date on 2022-12-18
 */
public interface EvolutionApi<T> {

    /**
     * you must promise this is unique
     *
     * @return unique Id
     */
    T evolutionId();

    T nextEvolutionId();

}
