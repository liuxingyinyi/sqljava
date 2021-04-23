package com.hccx.sqljava.service;

import com.hccx.sqljava.domain.bean.GroupBy;
import com.hccx.sqljava.domain.bean.GroupByResult;
import com.hccx.sqljava.domain.bean.OrderBy;
import com.hccx.sqljava.domain.bean.Where;
import com.hccx.sqljava.domain.entity.Account;
import com.hccx.sqljava.domain.query.Query;

import java.util.List;

/**
 * 项目名称：sql-java
 * <br>类描述：
 * <br>创建人：htliu
 * <br>创建时间：2021/4/23 0023 11:36
 * <br>修改人:
 * <br>修改时间：2021/4/23 0023 11:36
 * <br>修改备注：
 *
 * @author htliu
 * @date 2021/4/23 0023 11:36
 */
public interface IAccountService {
    /**
     * 插入测试数据
     *
     * @param account
     * @return
     */
    Account addAccount(Account account);

    /**
     * 列表查询
     *
     * @param query
     * @return
     */
    List<Account> list(Query<Account> query);

    /**
     * 分组
     *
     * @param query
     * @return
     */
    List<GroupByResult<Account>> group(Query<Account> query);

    /**
     *
     */
    void clean();

}
