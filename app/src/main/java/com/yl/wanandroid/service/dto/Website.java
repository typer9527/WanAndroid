package com.yl.wanandroid.service.dto;

public class Website implements CollectItem {

    private int id;
    private String link;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getItemTitle() {
        return name;
    }

    @Override
    public String getItemDes() {
        return link;
    }

    @Override
    public int getItemId() {
        return id;
    }

    @Override
    public int getItemOriginId() {
        return -1;
    }

    @Override
    public boolean isArticle() {
        return false;
    }
}
