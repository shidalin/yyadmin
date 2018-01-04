package com.yonyou.iviewadmin.system.service.impl;

import com.yonyou.iviewadmin.core.AbstractServiceImpl;
import com.yonyou.iviewadmin.system.entity.User;
import com.yonyou.iviewadmin.system.mapper.UserMapper;
import com.yonyou.iviewadmin.system.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> queryAllPerms() throws AuthenticationException {
        return userMapper.queryAllPerms();
    }

    @Override
    public List<String> queryPermsByUserId(String userId) throws AuthenticationException {
        return userMapper.queryPermsByUserId(userId);
    }

    @Override
    public boolean insert(User user) {
        //用户密码加密
        user.setUserPswd(new Sha256Hash(user.getUserPswd(), user.getSalt()).toHex());
        return super.insert(user);
    }

    @Override
    public User queryUserByAccessToken(String accessToken) throws AuthenticationException {
        return userMapper.queryUserByAccessToken(accessToken);
    }
}
