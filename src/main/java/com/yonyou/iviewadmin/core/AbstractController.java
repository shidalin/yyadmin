package com.yonyou.iviewadmin.core;

import com.baomidou.mybatisplus.service.IService;
import com.yonyou.iviewadmin.system.entity.UserToken;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.yonyou.prm.model.system.User;

/**
 * Controller公共组件
 */
public abstract class AbstractController<S extends IService<T>, T extends AbstractModel<T>> {


    protected Logger logger = LoggerFactory.getLogger(getClass());


    protected UserToken getToken() {
        return (UserToken) SecurityUtils.getSubject().getPrincipal();
    }

    protected String getUserId() {
        if (getToken() == null) {
            return "";
        }
        return getToken().getUserId();
    }


}
