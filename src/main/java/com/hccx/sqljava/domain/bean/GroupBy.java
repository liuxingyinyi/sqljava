package com.hccx.sqljava.domain.bean;

import com.google.common.collect.Lists;
import com.hccx.sqljava.domain.entity.Account;
import lombok.Data;

import java.util.List;
import java.util.function.Function;

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
public class GroupBy<T> {
    /**
     * 分组字段
     */
    private Linked<Function<Account, Object>> groupFieldLink;

}
