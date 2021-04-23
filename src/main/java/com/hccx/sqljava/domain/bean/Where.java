package com.hccx.sqljava.domain.bean;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 项目名称：sql-java
 * <br>类描述：
 * <br>创建人：htliu
 * <br>创建时间：2021/4/23 0023 11:24
 * <br>修改人:
 * <br>修改时间：2021/4/23 0023 11:24
 * <br>修改备注：
 *
 * @author htliu
 * @date 2021/4/23 0023 11:24
 */
@Data
@Accessors(chain = true)
public class Where<E> {

    private Predicate<E> predicate = e -> true;

    public Where<E> and(Function<E, Object> field, Object value) {
        predicate = this.predicate.and(generatePredicate(field, value));
        return this;
    }

    public Where<E> or(Function<E, Object> field, Object value) {
        predicate = this.predicate.or(generatePredicate(field, value));
        return this;
    }

    private Predicate<E> generatePredicate(Function<E, Object> field, Object value) {
        return e -> Objects.equals(value, field.apply(e));
    }

}
