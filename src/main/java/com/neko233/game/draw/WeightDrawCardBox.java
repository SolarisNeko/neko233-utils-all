package com.neko233.game.draw;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 权重抽卡盒子
 *
 * @param <CARD> 抽取的卡
 */
public class WeightDrawCardBox<CARD> implements Serializable, Cloneable {

    private final Map<CARD, Integer> weightCardMap;
    private final int totalWeight;

    public WeightDrawCardBox(Map<CARD, Integer> weightCardMap) {
        this(weightCardMap, weightCardMap.values().stream().mapToInt(i -> i).sum());
    }

    private WeightDrawCardBox(Map<CARD, Integer> weightCardMap, int totalWeight) {
        this.weightCardMap = weightCardMap;
        this.totalWeight = totalWeight;
        assert !isEmpty();
    }

    public WeightDrawCardBox<CARD> clone() {
        return new WeightDrawCardBox<>(this.weightCardMap);
    }


    public CARD randomInRepeat() {
        Map<CARD, Integer> tempMap = this.weightCardMap;
        if (tempMap.isEmpty()) {
            return null;
        }
        float index = (ThreadLocalRandom.current().nextFloat() * this.totalWeight);
        for (Map.Entry<CARD, Integer> entry : tempMap.entrySet()) {
            index -= entry.getValue();
            if (index < 0) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("WeightDrawCardBox totalWeight has gone crazy! currentIndex:" + index);
    }


    public CARD randomOne() {
        return randomInRepeat();
    }


    public List<CARD> randomInMutex(int times, Function<CARD, CARD> extraHandleFunction) {
        if (times <= 0 || times > this.weightCardMap.size()) {
            throw new RuntimeException("WeightDrawCardBox randomRepeatedlyMutex not enough currentSize=" + this.weightCardMap.size() + ",require times=" + times);
        }
        int totalWeight = this.totalWeight;
        Set<CARD> result = new HashSet<>(times);
        while (result.size() < times) {
            int index = (int) (ThreadLocalRandom.current().nextDouble() * totalWeight);
            for (Map.Entry<CARD, Integer> entry : this.weightCardMap.entrySet()) {
                index -= entry.getValue();
                if (index <= 0) {
                    CARD key = entry.getKey();
                    if (result.contains(key)) {
                        continue;
                    }
                    if (extraHandleFunction != null) {
                        result.add(extraHandleFunction.apply(key));
                        break;
                    }
                    result.add(key);
                    break;
                }
            }
        }
        return new ArrayList<>(result);
    }

    public List<CARD> randomRepeatedly(int times) {
        List<CARD> result = new ArrayList<>(times);
        for (int i = 0; i < times; i++) {
            result.add(randomInRepeat());
        }
        return result;
    }

    public List<CARD> randomInMutex(int times) {
        return randomInMutex(times, null);
    }


    /**
     * 过滤条件下，返回一个新的盒子
     *
     * @return 新的盒子，内部都是符合条件的卡牌
     */
    public WeightDrawCardBox<CARD> generateNewBox(Predicate<CARD> filter) {
        return this.filter(filter).orElse(null);
    }

    /**
     * 返回新的 RandomBox。若新的 RandomBox 内元素为空，则返回 Option.empty()
     *
     * @param filterFunc 过滤
     * @return 可能为空的新盒子
     */
    public Optional<WeightDrawCardBox<CARD>> filter(Predicate<CARD> filterFunc) {
        Map<CARD, Integer> weightCardMap = new HashMap<>(this.weightCardMap.size(), 1.0f);
        int totalWeight = 0;
        for (Map.Entry<CARD, Integer> entry : this.weightCardMap.entrySet()) {
            CARD key = entry.getKey();
            if (filterFunc == null || filterFunc.test(key)) {
                continue;
            }
            weightCardMap.put(entry.getKey(), entry.getValue());
            totalWeight += entry.getValue();
        }

        if (totalWeight == 0 || this.weightCardMap.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new WeightDrawCardBox<>(weightCardMap, totalWeight));
        }
    }

    public Integer getWeight(CARD card) {
        return this.weightCardMap.get(card);
    }

    public boolean isEmpty() {
        return totalWeight <= 0 || weightCardMap.isEmpty();
    }

    public int getCardCount() {
        return weightCardMap.size();
    }

    public Set<CARD> getKeys() {
        return weightCardMap.keySet();
    }

}
