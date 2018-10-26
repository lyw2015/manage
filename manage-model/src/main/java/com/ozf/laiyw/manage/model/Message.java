package com.ozf.laiyw.manage.model;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/15 17:20
 */
public class Message {

    private String id;
    private String title;
    private String context;

    public Message(String id, String title, String context) {
        this.id = id;
        this.title = title;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
