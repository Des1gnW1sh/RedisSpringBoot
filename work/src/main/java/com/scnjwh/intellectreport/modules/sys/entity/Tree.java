package com.scnjwh.intellectreport.modules.sys.entity;

import java.util.List;

/**
 * Created by Administrator on 2018-1-22.
 */
public class Tree {
    private Integer id;
    private Integer parentId;
    private String Name;

    private List<Tree> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
