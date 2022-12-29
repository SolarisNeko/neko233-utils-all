package com.neko233.idGenerator;

import javax.annotation.Nullable;
import java.util.List;

public interface IdGenerator {

    String getName();

    /**
     * @return null = 无法获取 / long = id
     */
    @Nullable
    Long nextId();

    List<Long> nextIds(int count);

}