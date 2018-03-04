package com.yonyou.yyadmin.system.service;

import org.apache.shiro.authc.AuthenticationException;

import java.util.Set;

/**
 * shiro相关接口
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     *
     * @param userCode
     * @return
     * @throws Exception
     */
    Set<String> getUserPermissions(String userCode) throws AuthenticationException;


}
