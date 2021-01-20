package com.atguigu.springcloud.util;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author chenwei
 * @version 1.0
 * @date 2020-02-26
 */
public class BathDbOpUtils {
    private BathDbOpUtils() {

    }

    public static <T, R, S> int compareAndDealToDatabase(Supplier<List<T>> getNewListFun,
                                                         Supplier<List<T>> getOldListFun,
                                                         Function<T, R> getIdentityFun,
                                                         Function<T, S> getIdFun,
                                                         Function<List<T>, Integer> insertListFun,
                                                         Function<List<S>, Integer> deleteListIdFun) {
        return compareAndDealToDatabase(getNewListFun,
                getOldListFun,
                getIdentityFun,
                getIdFun,
                insertListFun,
                deleteListIdFun,
                null,
                null,
                null
        );
    }


    /**
     *
     * @param getNewListFun 获取新的列表
     * @param getOldListFun 获取旧的列表
     * @param getIdentityFun    获取判断是否为同一条数据的标志的方法
     * @param getIdFun  获取主键的方法
     * @param insertListFun 插入列表方法
     * @param deleteListIdFun   删除列表方法
     * @param filterKeyFun  获取判断新旧数据是否一模一样的标志的方法(更新使用)
     * @param updateListFun 更新列表方法(更新使用)
     * @param copyOldDataToNewDataForUpdateFun  复制旧的列表中的某些数据到新的列表中的方法(很多时候是复制主键进来)(更新使用)
     * @param <T>
     * @param <R>
     * @param <S>
     * @return
     */
    public static <T, R, S,K> int compareAndDealToDatabase(Supplier<List<T>> getNewListFun,
                                                         Supplier<List<T>> getOldListFun,
                                                         Function<T, R> getIdentityFun,
                                                         Function<T, S> getIdFun,
                                                         Function<List<T>, Integer> insertListFun,
                                                         Function<List<S>, Integer> deleteListIdFun,

                                                         Function<T, K> filterKeyFun,
                                                         Function<List<T>, Integer> updateListFun,
                                                         BiConsumer<T, T> copyOldDataToNewDataForUpdateFun) {
        if (getNewListFun == null || getIdentityFun == null || getOldListFun == null) {
            return 0;
        }
        if (insertListFun == null && deleteListIdFun == null && updateListFun == null) {
            return 0;
        }
        List<T> oldList = getOldListFun.get();
        List<T> newList = getNewListFun.get();

        int count = 0;
        if (insertListFun != null) {
            List<T> toInsertList = ListUtils.subtract(
                    newList, oldList,
                    getIdentityFun, getIdentityFun);
            if (!CollectionUtils.isEmpty(toInsertList)) {
                count += insertListFun.apply(toInsertList);
            }
        }
        if (deleteListIdFun != null) {
            List<T> toDeleteList = ListUtils.subtract(
                    oldList, newList,
                    getIdentityFun, getIdentityFun);
            List<S> collect = toDeleteList.stream().map(getIdFun).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                count += deleteListIdFun.apply(collect);
            }
        }
        if (updateListFun != null) {
            //被过滤重复项的数据
            List<T> newListFilter = newList;
            //过滤掉新列表里面与旧列表相同的数据
            if (filterKeyFun != null) {
                Set<K> collect = oldList.stream().map(filterKeyFun).collect(Collectors.toSet());
                newListFilter = newList.stream().filter(it -> {
                    K apply = filterKeyFun.apply(it);
                    return !collect.contains(apply);
                }).collect(Collectors.toList());
            }

            List<T> toUpdateTagList = ListUtils.intersectionForUpdate(
                    newListFilter, oldList,
                    getIdentityFun, getIdentityFun,
                    copyOldDataToNewDataForUpdateFun);
            if (CollectionUtil.isNotEmpty(toUpdateTagList)) {
                count += updateListFun.apply(toUpdateTagList);
            }
        }
        return count;
    }
}
