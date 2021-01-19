package com.group7.store.entity.book;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 */
public class BookSort {
    private int id;
    private String sortName;
    /**
     * 上一级的标签名
     */
    private String upperName;
    private String level;
    private int rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getUpperName() {
        return upperName;
    }

    public void setUpperName(String upperName) {
        this.upperName = upperName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "BookSort{" +
                "id=" + id +
                ", sortName='" + sortName + '\'' +
                ", upperName='" + upperName + '\'' +
                ", level='" + level + '\'' +
                ", rank=" + rank +
                '}';
    }
}

