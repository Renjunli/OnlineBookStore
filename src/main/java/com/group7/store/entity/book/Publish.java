package com.group7.store.entity.book;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 */
public class Publish {
    /**
     * 编号
     */
    private int id;
    /**
     * 出版社姓名
     */
    private String name;
    /**
     * 出版社是否显示
     */
    private boolean showPublish;
    /**
     * 排序值
     */
    private Integer rank;
    /**
     * 该出版社中有多少书
     */
    private Integer num;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowPublish() {
        return showPublish;
    }

    public void setShowPublish(boolean showPublish) {
        this.showPublish = showPublish;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Publish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", showPublish=" + showPublish +
                ", rank=" + rank +
                ", num=" + num +
                '}';
    }
}

