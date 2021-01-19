package com.group7.store.entity.dto;

import java.sql.Timestamp;


public class TopicBook {
    private int id;
    private String bookName;
    private String author;
    /**
     * 出版社
     */
    private String publish;
    /**
     * 出版时间
     */
    private Timestamp birthday;
    /**
     * 书的封面图
     */
    private String coverImg;
    private String isbn;
    /**
     * 推荐理由
     */
    private String recommendReason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(String recommendReason) {
        this.recommendReason = recommendReason;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "TopicBook{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", publish='" + publish + '\'' +
                ", birthday=" + birthday +
                ", coverImg='" + coverImg + '\'' +
                ", isbn='" + isbn + '\'' +
                ", recommendReason='" + recommendReason + '\'' +
                '}';
    }
}
