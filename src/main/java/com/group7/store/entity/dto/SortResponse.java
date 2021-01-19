package com.group7.store.entity.dto;


import com.group7.store.entity.book.BookSort;

import java.util.List;


public class SortResponse {
    /**
     * 父级的分类名
     */
    private BookSort upperSort;
    /**
     * 分类的子集
     */
    private List<BookSort> children;


    public BookSort getUpperSort() {
        return upperSort;
    }

    public void setUpperSort(BookSort upperSort) {
        this.upperSort = upperSort;
    }

    public List<BookSort> getChildren() {
        return children;
    }

    public void setChildren(List<BookSort> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "SortResponse{" +
                "upperSort=" + upperSort +
                ", children=" + children +
                '}';
    }
}
