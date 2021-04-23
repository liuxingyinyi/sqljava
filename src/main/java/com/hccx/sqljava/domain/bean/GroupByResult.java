package com.hccx.sqljava.domain.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 项目名称：sql-java
 * <br>类描述：
 * <br>创建人：htliu
 * <br>创建时间：2021/4/23 0023 16:20
 * <br>修改人:
 * <br>修改时间：2021/4/23 0023 16:20
 * <br>修改备注：
 *
 * @author htliu
 * @date 2021/4/23 0023 16:20
 */
@Data
@Accessors(chain = true)
public class GroupByResult<T> {
    /**
     * 分组
     */
    private String groupBy;
    /**
     *
     */
    private List<T> groupList;
    private List<GroupByResult<T>> groupChildren;
}
