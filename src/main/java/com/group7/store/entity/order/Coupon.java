package com.group7.store.entity.order;

import java.sql.Timestamp;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 */
public class Coupon {
    /**
     * 编号，自动增加
     */
    private int id;
    /**
     * 名称
     */
    private String name;
    /**
     * 发行数量
     */
    private Integer issuesNum;
    /**
     * 发行时间
     */
    private Timestamp issuesTime;
    /**
     * 面额
     */
    private Double money;
    /**
     * 每人限领张数
     */
    private Integer limitNum;
    /**
     * 使用门槛，(满多少可用)
     */
    private Integer thresholdMoney;
    /**
     * 起效时间
     */
    private Timestamp startTime;
    /**
     * 终止时间
     */
    private Timestamp endTime;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 使用范围，全场通用，指定分类，指定商品
     */
    private String range;
}
