package com.group7.store.entity.dto;

import java.sql.Timestamp;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 * @Description: 购物车的前后端交互实体类
 */
public class CartBookDto {
    private String account;
    /**
     * 图书的id
     */
    private int id;
    private Integer num;
    /**
     * 添加到购物车的时间
     */
    private Timestamp addTime;
    private String bookName;
    private String author;
    private String isbn;
    /**
     * 出版社
     */
    private String publish;
    /**
     * 出版时间
     */
    private Timestamp birthday;
    /**
     * 书的原价
     */
    private double marketPrice;
    /**
     * 书的售价
     */
    private double price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 书的概述，在详情页展示
     */
    private String description;
    /**
     * 书的封面图
     */
    private String coverImg;
    /**
     * 是否上架
     */
    private boolean put;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public boolean isPut() {
        return put;
    }

    public void setPut(boolean put) {
        this.put = put;
    }

    @Override
    public String toString() {
        return "CartBookDto{" +
                "account='" + account + '\'' +
                ", id=" + id +
                ", num=" + num +
                ", addTime=" + addTime +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publish='" + publish + '\'' +
                ", birthday=" + birthday +
                ", marketPrice=" + marketPrice +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", put=" + put +
                '}';
    }
}
