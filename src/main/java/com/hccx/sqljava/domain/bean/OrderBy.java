package com.hccx.sqljava.domain.bean;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 项目名称：sql-java
 * <br>类描述：
 * <br>创建人：htliu
 * <br>创建时间：2021/4/23 0023 11:25
 * <br>修改人:
 * <br>修改时间：2021/4/23 0023 11:25
 * <br>修改备注：
 *
 * @author htliu
 * @date 2021/4/23 0023 11:25
 */
@Data
public class OrderBy<E> {
    private List<Config<E>> configList = Lists.newArrayList();


    public List<Config<E>> add(Config<E> config) {
        configList.add(config);
        return configList;
    }


    @Data
    @Accessors(chain = true)
    public static final class Config<T> {
        /**
         * 排序字段
         */
        private Function<T, Comparable<?>> field;
        /**
         *
         */
        private boolean desc = false;
    }
}
