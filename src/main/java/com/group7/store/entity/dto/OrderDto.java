package com.group7.store.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group7.store.entity.order.Expense;
import com.group7.store.entity.user.Address;

import java.util.Date;
import java.util.List;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 */
public class OrderDto {
    /**
     * 编号
     */
    private int id;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 账户
     */
    private String account;
    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    /**
     * 发货时间
     */
    private Date shipTime;
    /**
     * 收货时间
     */
    private Date getTime;
    /**
     * 评价时间
     */
    private Date evaluateTime;
    /**
     * 结束时间
     */
    private Date closeTime;
    /**
     * 自动确认收货的时间 默认14天
     */
    private Date confirmTime;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 物流单号
     */
    private String logisticsNum;
    /**
     * 订单
     */
    private List<OrderDetailDto> orderDetailDtoList;
    /**
     * 订单费用明细
     */
    private Expense expense;
    /**
     * 收货地址
     */
    private Address address;
    private List<String> coverImgList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
    }

    public List<OrderDetailDto> getOrderDetailDtoList() {
        return orderDetailDtoList;
    }

    public void setOrderDetailDtoList(List<OrderDetailDto> orderDetailDtoList) {
        this.orderDetailDtoList = orderDetailDtoList;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getCoverImgList() {
        return coverImgList;
    }

    public void setCoverImgList(List<String> coverImgList) {
        this.coverImgList = coverImgList;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", account='" + account + '\'' +
                ", orderTime=" + orderTime +
                ", shipTime=" + shipTime +
                ", getTime=" + getTime +
                ", evaluateTime=" + evaluateTime +
                ", closeTime=" + closeTime +
                ", confirmTime=" + confirmTime +
                ", orderStatus='" + orderStatus + '\'' +
                ", logisticsNum='" + logisticsNum + '\'' +
                ", OrderDetailDtoList=" + orderDetailDtoList +
                ", expense=" + expense +
                ", address=" + address +
                '}';
    }
}
