package com.yonyou.yyadmin.modules.order.dto;

import com.yonyou.yyadmin.modules.order.entity.PoOrder;
import com.yonyou.yyadmin.modules.order.entity.PoOrderItem;

import java.util.List;

public class PoOrderDTO {

    private PoOrder parentEntity;
    private List<PoOrderItem> childEntityList;

    public PoOrder getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(PoOrder parentEntity) {
        this.parentEntity = parentEntity;
    }

    public List<PoOrderItem> getChildEntityList() {
        return childEntityList;
    }

    public void setChildEntityList(List<PoOrderItem> childEntityList) {
        this.childEntityList = childEntityList;
    }
}
