package com.group7.store.entity.order;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;


public class Order {
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
     * 收货地址编号
     */
    private int addressId;
    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp orderTime;
    /**
     * 发货时间
     */
    private Timestamp shipTime;
    /**
     * 收货时间
     */
    private Timestamp getTime;
    /**
     * 评价时间
     */
    private Timestamp evaluateTime;
    /**
     * 结束时间
     */
    private Timestamp closeTime;
    /**
     * 自动确认收货的时间 默认14天
     */
    private Timestamp confirmTime;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 物流公司 用id号进行辨识是哪个物流公司,这样物流公司也可以进行管理
     */
    private int logisticsCompany;
    /**
     * 物流单号
     */
    private String logisticsNum;
    /**
     * 订单是否被用户删除的标记
     */
    private boolean beUserDelete;

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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Timestamp getShipTime() {
        return shipTime;
    }

    public void setShipTime(Timestamp shipTime) {
        this.shipTime = shipTime;
    }

    public Timestamp getGetTime() {
        return getTime;
    }

    public void setGetTime(Timestamp getTime) {
        this.getTime = getTime;
    }

    public Timestamp getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Timestamp evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }

    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
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

    public int getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(int logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public boolean isBeUserDelete() {
        return beUserDelete;
    }

    public void setBeUserDelete(boolean beUserDelete) {
        this.beUserDelete = beUserDelete;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", account='" + account + '\'' +
                ", addressId=" + addressId +
                ", orderTime=" + orderTime +
                ", shipTime=" + shipTime +
                ", getTime=" + getTime +
                ", evaluateTime=" + evaluateTime +
                ", closeTime=" + closeTime +
                ", confirmTime=" + confirmTime +
                ", orderStatus='" + orderStatus + '\'' +
                ", logisticsNum='" + logisticsNum + '\'' +
                '}';
    }
}
