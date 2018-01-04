package com.yonyou.iviewadmin.system.service;

import org.apache.shiro.authc.AuthenticationException;

import java.util.Set;

/**
 * shiro相关接口
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     *
     * @param userId
     * @return
     * @throws Exception
     */
    Set<String> getUserPermissions(String userId) throws AuthenticationException;


}
