package com.hccx.sqljava.domain.query;

import com.hccx.sqljava.domain.bean.GroupBy;
import com.hccx.sqljava.domain.bean.Limit;
import com.hccx.sqljava.domain.bean.OrderBy;
import com.hccx.sqljava.domain.bean.Where;
import lombok.Data;

/**
 * 项目名称：sql-java
 * <br>类描述：
 * <br>创建人：htliu
 * <br>创建时间：2021/4/23 0023 11:37
 * <br>修改人:
 * <br>修改时间：2021/4/23 0023 11:37
 * <br>修改备注：
 *
 * @author htliu
 * @date 2021/4/23 0023 11:37
 */
@Data
public class Query<T> {
    private Where<T> where;
    private OrderBy<T> orderBy;
    private GroupBy<T> groupBy;
    private Limit limit;
}
