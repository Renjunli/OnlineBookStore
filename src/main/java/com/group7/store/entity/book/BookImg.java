package com.group7.store.entity.book;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 */
public class BookImg {
    private int id;
    /**
     * 书的ISBN
     */
    private String isbn;
    /**
     * url
     */
    private String imgSrc;
    /**
     * 是否封面显示
     */
    private boolean cover;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }
}