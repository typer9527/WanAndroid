package com.yl.wanandroid.service.dto;

import java.util.List;

/**
 * 知识体系结构子节点
 */
public class TreeChild {
    private List<TreeChild> children;
    private int id;
    private String name;

    public List<TreeChild> getChildren() {
        return children;
    }

    public void setChildren(List<TreeChild> children) {
        this.children = children;
    }

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
}
