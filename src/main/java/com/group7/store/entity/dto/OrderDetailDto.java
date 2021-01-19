package com.group7.store.entity.dto;

import com.group7.store.entity.book.Book;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 */
public class OrderDetailDto {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 图书
     */
    private Book book;
    /**
     * 某本图书的下单数量
     */
    private String num;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
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
        return "OrderDetailDto{" +
                "orderId='" + orderId + '\'' +
                ", book=" + book +
                ", num='" + num + '\'' +
                ", price=" + price +
                '}';
    }
}