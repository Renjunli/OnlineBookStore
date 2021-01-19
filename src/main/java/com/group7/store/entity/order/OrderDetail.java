package com.group7.store.entity.order;


public class OrderDetail {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 图书id
     */
    private int bookId;
    /**
     * 某本图书的下单数量
     */
    private int num;
    /**
     * 下单时候图书的单价
     */
    private double price;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", bookId=" + bookId +
                ", num=" + num +
                ", price=" + price +
                '}';
    }
}
