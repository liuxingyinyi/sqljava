package com.hccx.sqljava.service.impl;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.hccx.sqljava.domain.bean.*;
import com.hccx.sqljava.domain.entity.Account;
import com.hccx.sqljava.domain.query.Query;
import com.hccx.sqljava.service.IAccountService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.ws.ResponseWrapper;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * 项目名称：sql-java
 * <br>类描述：
 * <br>创建人：htliu
 * <br>创建时间：2021/4/23 0023 14:04
 * <br>修改人:
 * <br>修改时间：2021/4/23 0023 14:04
 * <br>修改备注：
 *
 * @author htliu
 * @date 2021/4/23 0023 14:04
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceImplTest {
    /**
     * 数据量
     */
    public static final int size = 20;
    @Autowired
    private IAccountService accountService;
    private List<Account> accountList = Lists.newArrayList();


    @Before
    public void setUp() throws Exception {
        Random random = new Random();
        Supplier<String> randomStr = () -> "" + random.nextInt(10);
        Supplier<Date> randomDate = () -> DateTime.now().minusSeconds(random.nextInt(1000)).toDate();
        Supplier<String> randomGender = () -> random.nextInt(10) % 2 == 0 ? "男" : "女";
        accountService.clean();
        for (int i = 0; i < 20; i++) {
            Account account = new Account();
            account.setName(randomStr.get())
                    .setNickName(randomStr.get())
                    .setGender(randomGender.get())
                    .setId(Long.valueOf(randomStr.get()))
                    .setCreateTime(randomDate.get())
                    .setModifyTime(randomDate.get());
            accountList.add(account);
            accountService.addAccount(account);
        }
    }

    @Test
    public void addAccount() {
    }

    /**
     * 分页测试
     */
    @Test
    public void page() {
        Query<Account> query = new Query<>();
        Limit limit = new Limit(3, 4);
        query.setLimit(limit);
        int index = (limit.getPage() - 1) * limit.getPageSize();
        compareList(accountList.subList(index, index + limit.getPageSize()), accountService.list(query));
    }

    /**
     * name 正序
     */
    @Test
    public void orderName() {
        Query<Account> query = new Query<>();
        Limit limit = new Limit(1, size);
        query.setLimit(limit);
        OrderBy<Account> orderBy = new OrderBy<>();
        query.setOrderBy(orderBy);
        orderBy.add(new OrderBy.Config<Account>()
                .setField(Account::getName)
        );
        accountList.sort((o1, o2) -> ComparisonChain.start()
                .compare(o1.getName(), o2.getName())
                .result());
        compareList(accountList, accountService.list(query));

    }

    /**
     * name 倒序
     */
    @Test
    public void orderNameDesc() {
        Query<Account> query = new Query<>();
        Limit limit = new Limit(1, size);
        query.setLimit(limit);
        OrderBy<Account> orderBy = new OrderBy<>();
        query.setOrderBy(orderBy);
        orderBy.add(new OrderBy.Config<Account>()
                .setField(Account::getName)
                .setDesc(true)
        );
        accountList.sort((o1, o2) -> ComparisonChain.start()
                .compare(o2.getName(), o1.getName())
                .result());
        compareList(accountList, accountService.list(query));
    }


    /**
     * 名称时间排序
     */
    @Test
    public void orderNameAndCreateTime() {
        Query<Account> query = new Query<>();
        Limit limit = new Limit(1, size);
        query.setLimit(limit);
        OrderBy<Account> orderBy = new OrderBy<>();
        query.setOrderBy(orderBy);
        orderBy.add(new OrderBy.Config<Account>()
                .setField(Account::getName)
        ).add(new OrderBy.Config<Account>()
                .setField(Account::getCreateTime)
                .setDesc(true)
        );
        accountList.sort((o1, o2) -> ComparisonChain.start()
                .compare(o1.getName(), o2.getName())
                .compare(o2.getCreateTime(), o1.getCreateTime())
                .result());
        compareList(accountList, accountService.list(query));
    }

    @Test
    public void where() {
        Query<Account> query = new Query<>();
        Limit limit = new Limit(1, size);
        query.setLimit(limit);
        Where<Account> where = new Where<>();
        query.setWhere(where);
        where.and(Account::getName, "1")
                .and(Account::getId, 15L)
                .or(Account::getGender, "未知")
        ;
        Account account = new Account();
        account.setId(15L)
                .setName("1");
        accountService.addAccount(account);
        Account queryAccount = accountService.list(query).get(0);
        assertEquals(account, queryAccount);
    }


    /**
     * 列表比较
     *
     * @param leftList
     * @param rightList
     */
    private void compareList(List<Account> leftList, List<Account> rightList) {
        assertEquals(leftList.size(), rightList.size());
        for (int i = 0; i < leftList.size(); i++) {
            assertEquals(leftList.get(i), rightList.get(i));
        }
    }

    @Test
    public void group() {
        accountService.clean();
        Stream.of(1, 2, 3, 4, 5)
                .flatMap(new Function<Integer, Stream<Account>>() {
                    @Override
                    public Stream<Account> apply(Integer a) {
                        return Stream.of(1, 2, 3)
                                .map(new Function<Integer, Account>() {
                                    @Override
                                    public Account apply(Integer b) {
                                        Account account = new Account();
                                        account.setId(Long.valueOf(a.toString()))
                                                .setName(a + "-" + b);
                                        return account;
                                    }
                                });
                    }
                }).forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                accountService.addAccount(account);
                accountService.addAccount(account);
            }
        });
        Query<Account> query = new Query<>();
        Limit limit = new Limit(1, size);
        query.setLimit(limit);
        GroupBy<Account> groupBy = new GroupBy<>();
        query.setGroupBy(groupBy);
        Linked<Function<Account, Object>> linked = new Linked<>();
        groupBy.setGroupFieldLink(linked);
        linked
                .add(Account::getId)
                .add(Account::getName)
        ;
        List<GroupByResult<Account>> groupByResultList = accountService.group(query);
        for (GroupByResult<Account> groupByResult : groupByResultList) {
            if ("1".equals(groupByResult.getGroupBy())) {
                assertEquals(groupByResult.getGroupList().size(), 6);
                assertEquals(groupByResult.getGroupChildren().size(), 3);
                for (GroupByResult<Account> groupChild : groupByResult.getGroupChildren()) {
                    if ("1-2".equals(groupChild.getGroupBy())) {
                        assertEquals(2, groupChild.getGroupList().size());
                    }
                }
            }
        }
    }
}