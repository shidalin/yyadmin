package com.yonyou.yyadmin.system.service;

import com.yonyou.yyadmin.base.AbstractService;
import com.yonyou.yyadmin.system.entity.User;
import org.apache.shiro.authc.AuthenticationException;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */
public interface UserService extends AbstractService<User> {
    /**
     * 查询管理员全部权限
     *
     * @return
     * @throws Exception
     */
    List<String> queryAllPerms() throws AuthenticationException;

    /**
     * 查询用户权限
     *
     * @param userId
     * @return
     */
    List<String> queryPermsByUserId(String userId) throws AuthenticationException;

    /**
     * 查询用户
     *
     * @param userCode
     * @return
     */
    List<String> queryMenuByUserCode(String userCode) throws AuthenticationException;
}
