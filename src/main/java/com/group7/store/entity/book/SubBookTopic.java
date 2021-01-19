package com.group7.store.entity.book;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 */
public class SubBookTopic {
    /**
     * 书单id
     */
    private int topicId;
    private int bookId;
    /**
     * 推荐理由
     */
    private String recommendReason;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(String recommendReason) {
        this.recommendReason = recommendReason;
    }

    @Override
    public String toString() {
        return "SubBookTopic{" +
                "topicId='" + topicId + '\'' +
                ", bookId=" + bookId +
                ", recommendReason='" + recommendReason + '\'' +
                '}';
    }
}