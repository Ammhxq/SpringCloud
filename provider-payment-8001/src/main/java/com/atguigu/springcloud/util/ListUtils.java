package com.atguigu.springcloud.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenwei
 */
public class ListUtils {
    /**
     * 根据 a b 两个集合中的某个属性来取交集
     *
     * @param a            集合a
     * @param b            集合b
     * @param getIdentityA 获取集合A中的某个特征主键的方法
     * @param getIdentityB 获取集合B中的某个特征主键的方法
     * @param <T>          集合a泛型
     * @param <V>          集合b泛型
     * @param <R>          特征主键类型
     * @return 返回集合a中的数据
     */
    public static <T, V, R> List<T> intersection(List<T> a, List<V> b, Function<T, R> getIdentityA, Function<V, R> getIdentityB) {
        Map<R, T> byIdentityA = a.stream().collect(Collectors.toMap(getIdentityA, Function.identity(), (v1, v2) -> v1));
        Map<R, V> byIdentityB = b.stream().collect(Collectors.toMap(getIdentityB, Function.identity(), (v1, v2) -> v1));
        List<T> res = new ArrayList<>();
        for (Map.Entry<R, T> entry : byIdentityA.entrySet()) {
            if (byIdentityB.containsKey(entry.getKey())) {
                res.add(entry.getValue());
            }
        }
        return res;
    }

    /**
     * 根据 a b 两个集合中的某个属性来取差集
     *
     * @param a            集合a
     * @param b            集合b
     * @param getIdentityA 获取集合A中的某个特征主键的方法
     * @param getIdentityB 获取集合B中的某个特征主键的方法
     * @param <T>          集合a泛型
     * @param <V>          集合b泛型
     * @param <R>          特征主键类型
     * @return 返回集合a中的数据
     */
    public static <T, V, R> List<T> subtract(List<T> a, List<V> b, Function<T, R> getIdentityA, Function<V, R> getIdentityB) {
        Map<R, T> byIdentityA = a.stream().collect(Collectors.toMap(getIdentityA, Function.identity(), (v1, v2) -> v1));
        Map<R, V> byIdentityB = b.stream().collect(Collectors.toMap(getIdentityB, Function.identity(), (v1, v2) -> v1));
        List<T> res = new ArrayList<>();
        for (Map.Entry<R, T> entry : byIdentityA.entrySet()) {
            if (!byIdentityB.containsKey(entry.getKey())) {
                res.add(entry.getValue());
            }
        }
        return res;
    }

    /**
     * 包含复制属性的功能
     *
     * @param a
     * @param b
     * @param getIdentityA
     * @param getIdentityB
     * @param copyValueFromB2Afun 将b中的的某些属性复制给a用到的方法
     * @param <T>
     * @param <V>
     * @param <R>
     * @return 返回A类型的数据
     */
    public static <T, V, R> List<T> intersectionForUpdate(List<T> a, List<V> b,
                                                          Function<T, R> getIdentityA, Function<V, R> getIdentityB,
                                                          BiConsumer<V, T> copyValueFromB2Afun) {
        Map<R, T> byIdentityA = a.stream().collect(Collectors.toMap(getIdentityA, Function.identity(), (v1, v2) -> v1));
        Map<R, V> byIdentityB = b.stream().collect(Collectors.toMap(getIdentityB, Function.identity(), (v1, v2) -> v1));
        List<T> res = new ArrayList<>();
        for (Map.Entry<R, T> entry : byIdentityA.entrySet()) {
            V valueB = byIdentityB.get(entry.getKey());
            if (valueB != null) {
                T valueA = entry.getValue();
                if (copyValueFromB2Afun != null) {
                    copyValueFromB2Afun.accept(valueB, valueA);
                }
                res.add(valueA);
            }
        }
        return res;
    }

    /**
     * 数组转列表
     *
     * @param predicate 过滤条件
     * @param array
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> List<T> asList(Predicate<? super T> predicate, T... array) {
        if (array == null || array.length == 0) {
            return new ArrayList<>();
        }
        return Stream.of(array).filter(predicate).collect(Collectors.toList());
    }

    public static <T> List<T> pageGetAll(int page, int limit, BiFunction<Integer, Integer, List<? extends T>> function) {
        List<T> result = new ArrayList<>();
        List<? extends T> subResult;
        do {
            subResult = function.apply(page++, limit);
            if (!CollectionUtils.isEmpty(subResult)) {
                result.addAll(subResult);
            }
        } while (subResult.size() == limit);
        return result;
    }

    public static <T> List<T> pageGetAll(Page page, Function<Page, List<? extends T>> function) {
        List<T> result = new ArrayList<>();
        List<? extends T> subResult;
        do {
            subResult = function.apply(page);
            if (!CollectionUtils.isEmpty(subResult)) {
                result.addAll(subResult);
            }
        } while (subResult.size() == page.getLimit());
        return result;
    }
    public static Integer plus(Integer num, IntFunction<Integer> function) {
        return function.apply(num);
    }
    public static Integer add(Integer num) {
        return num+num;
    }
    public static Integer mul(Integer num) {
        return num*num;
    }
}
