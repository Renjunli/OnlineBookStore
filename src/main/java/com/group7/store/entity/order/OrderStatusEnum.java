package com.group7.store.entity.order;


public enum OrderStatusEnum {
    /**
     * 已经提交
     */
    SUBMIT(1, "已付款"),
    SHIPPED(2, "已发货"),
    RECEIVED(3, "已收货"),
    EVALUATED(4, "已评价"),
    /**
     * 未支付或者退货成功了
     */
    CLOSE(5, "已关闭"),
    /**
     * 用户层面的被删除,
     */
    BEDELETE(6, "被删除");

    private int index;
    private String name;

    OrderStatusEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
