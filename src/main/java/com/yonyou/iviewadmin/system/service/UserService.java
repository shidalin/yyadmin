package com.yonyou.iviewadmin.system.service;

import com.yonyou.iviewadmin.core.AbstractService;
import com.yonyou.iviewadmin.system.entity.User;
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
     * @param accessToken
     * @return
     */
    User queryUserByAccessToken(String accessToken) throws AuthenticationException;
}
