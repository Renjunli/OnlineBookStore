package com.group7.store.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;


public class OrderStatistic {
    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp orderTime;
    /**
     * 下单总数
     */
    private int count;
    /**
     * 下单总价
     */
    private double amount;

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderStatistic{" +
                "orderTime=" + orderTime +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }
}
