package com.group7.store.entity.dto;


import com.group7.store.entity.book.Book;

import java.util.List;


public class SortBookRes {
    private int sortId;
    private String sortName;
    private List<Book> bookList;

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "SortAndBook{" +
                "sortId=" + sortId +
                ", sortName='" + sortName + '\'' +
                ", bookList=" + bookList +
                '}';
    }
}
