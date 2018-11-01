package com.ozf.laiyw.manage.model;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/15 17:20
 */
public class Message {

    private String id;
    private int fromUserId;
    private String fromUserName;
    private List<String> toUserIds;
    private Object data;//推送数据
    private String date;//推送时间

    public Message() {

    }

    public Message(Object data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public List<String> getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(List<String> toUserIds) {
        this.toUserIds = toUserIds;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
