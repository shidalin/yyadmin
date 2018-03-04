package com.yonyou.yyadmin.base;

import com.baomidou.mybatisplus.service.IService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 */
public abstract class AbstractController<S extends IService<T>, T extends AbstractModel<T>> {


    protected Logger logger = LoggerFactory.getLogger(getClass());

    public String getUserCode() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return principal == null ? "" : principal.toString();
    }

}
