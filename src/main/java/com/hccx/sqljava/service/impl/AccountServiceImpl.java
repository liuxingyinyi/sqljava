package com.hccx.sqljava.service.impl;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.hccx.sqljava.domain.bean.*;
import com.hccx.sqljava.domain.entity.Account;
import com.hccx.sqljava.domain.query.Query;
import com.hccx.sqljava.service.IAccountService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 项目名称：sql-java
 * <br>类描述：
 * <br>创建人：htliu
 * <br>创建时间：2021/4/23 0023 11:39
 * <br>修改人:
 * <br>修改时间：2021/4/23 0023 11:39
 * <br>修改备注：
 *
 * @author htliu
 * @date 2021/4/23 0023 11:39
 */
@Service
public class AccountServiceImpl implements IAccountService {
    private final List<Account> accountList = Lists.newArrayList();

    @Override
    public Account addAccount(Account account) {
        accountList.add(account);
        return account;
    }

    @Override
    public List<Account> list(Query<Account> query) {
        Limit limit = Optional.ofNullable(query.getLimit()).orElse(new Limit(1, 10));
        int page = limit.getPage();
        int pageSize = limit.getPageSize();
        return accountList.stream()
                .filter(new Predicate<Account>() {
                    @Override
                    public boolean test(Account account) {
                        return Optional.ofNullable(query.getWhere())
                                .map(Where::getPredicate)
                                .map(accountPredicate -> accountPredicate.test(account))
                                .orElse(true);
                    }
                })
                .sorted(new Comparator<Account>() {
                    @Override
                    public int compare(Account o1, Account o2) {
                        ComparisonChain comparisonChain = ComparisonChain.start();
                        OrderBy<Account> orderBy = query.getOrderBy();
                        if (orderBy != null) {
                            for (OrderBy.Config<Account> config : orderBy.getConfigList()) {
                                comparisonChain = config.isDesc() ?
                                        comparisonChain.compare(config.getField().apply(o2), config.getField().apply(o1)) :
                                        comparisonChain.compare(config.getField().apply(o1), config.getField().apply(o2));
                                ;
                            }
                        }
                        return comparisonChain.result();
                    }
                }).skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupByResult<Account>> group(Query<Account> query) {
        List<Account> accountList = list(query);
        GroupBy<Account> groupBy = query.getGroupBy();
        return groupByFieldNode(groupBy.getGroupFieldLink().getHead(), accountList);
    }

    /**
     * 分组
     *
     * @param groupField
     * @param accountList
     * @return
     */
    private List<GroupByResult<Account>> groupByFieldNode(Linked.Node<Function<Account, Object>> groupField, List<Account> accountList) {
        return accountList.stream()
                .collect(Collectors.groupingBy(new Function<Account, String>() {
                    @Override
                    public String apply(Account account) {
                        return Optional.ofNullable(groupField.getT().apply(account)).map(Object::toString)
                                .orElse(null);
                    }
                })).entrySet()
                .stream()
                .map(new Function<Map.Entry<String, List<Account>>, GroupByResult<Account>>() {
                    @Override
                    public GroupByResult<Account> apply(Map.Entry<String, List<Account>> stringListEntry) {
                        GroupByResult<Account> groupByResult = new GroupByResult<>();
                        List<Account> value = stringListEntry.getValue();
                        groupByResult.setGroupBy(stringListEntry.getKey())
                                .setGroupList(value);
                        if (groupField.getNext() != null) {
                            groupByResult.setGroupChildren(groupByFieldNode(groupField.getNext(), value));
                        }
                        return groupByResult;
                    }
                }).collect(Collectors.toList());
    }


    @Override
    public void clean() {
        accountList.clear();
    }
}
