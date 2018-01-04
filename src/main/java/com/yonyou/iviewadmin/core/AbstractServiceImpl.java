package com.yonyou.iviewadmin.core;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yonyou.iviewadmin.system.entity.User;
import org.apache.shiro.SecurityUtils;

import java.util.Date;

/**
 * Created by shidalin on 2017/9/27.
 **/
public class AbstractServiceImpl<M extends BaseMapper<T>, T extends AbstractModel> extends ServiceImpl<M, T> implements AbstractService<T> {

    protected User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    protected String getUserId() {
        if(getUser()==null){
            return "";
        }
        return getUser().getId();
    }

    @Override
    public boolean insert(T t) {
        t.setDeleted(Boolean.FALSE);
        t.setGmtCreate(new Date());
//        t.setGmtCreator(this.getUserId());
        t.setGmtModified(new Date());
//        t.setGmtModifier(this.getUserId());
        t.setStatus(StatusCode.NORMAL.code);
        return super.insert(t);
    }

    @Override
    public boolean updateById(T t) {
        t.setGmtModifier(this.getUserId());
        t.setGmtModified(new Date());
        return super.updateById(t);
    }
}
