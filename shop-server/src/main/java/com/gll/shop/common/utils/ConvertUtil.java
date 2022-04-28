package com.gll.shop.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gaoll
 * @date 2022年04月29日
 */
public class ConvertUtil {
    public static <T, R> IPage<R> convert(IPage<T> page, Function<? super T, ? extends R> mapper) {
        // 使用并行流 加快速度
        List<R> collect = page.getRecords().parallelStream().map(mapper).collect(Collectors.toList());
        return ((IPage<R>) page).setRecords(collect);
    }
}
