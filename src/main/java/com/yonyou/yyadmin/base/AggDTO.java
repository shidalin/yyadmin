package com.yonyou.yyadmin.base;

import java.util.List;

public abstract class AggDTO<T extends AbstractModel> {
    private T parentEntity;
    private List<T> childEntityList;

    public T getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(T parentEntity) {
        this.parentEntity = parentEntity;
    }

    public List<T> getChildEntityList() {
        return childEntityList;
    }

    public void setChildEntityList(List<T> childEntityList) {
        this.childEntityList = childEntityList;
    }
}
