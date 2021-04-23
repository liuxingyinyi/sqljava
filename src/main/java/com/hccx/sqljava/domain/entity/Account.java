package com.hccx.sqljava.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 项目名称：sql-java
 * <br>类描述：用户信息表
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
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
public class Account {
    /**
     * 主键
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date modifyTime;

}
