package com.neko233.common.adug;

import java.util.Collection;
import java.util.Collections;

/**
 * ADUG = Add / Delete / Update / Get
 * all API is OOP
 *
 * @author SolarisNeko on 2023-01-05
 **/
public interface AdugApi<T> {

    /**
     * add
     */
    default boolean add(T item) {
        return add(Collections.singleton(item));
    }

    boolean add(Collection<T> item);


    /**
     * delete
     */
    default boolean delete(T item) {
        return delete(Collections.singleton(item));
    }


    boolean delete(Collection<T> item);


    /**
     * update
     */
    default boolean update(T item) {
        return update(Collections.singleton(item));
    }

    boolean update(Collection<T> item);


    /**
     * get
     */
    default boolean get(T item) {
        return get(Collections.singleton(item));
    }

    boolean get(Collection<T> item);


}
