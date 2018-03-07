package com.yonyou.yyadmin.system.service.impl;

import com.yonyou.yyadmin.base.AbstractServiceImpl;
import com.yonyou.yyadmin.system.entity.User;
import com.yonyou.yyadmin.system.mapper.UserMapper;
import com.yonyou.yyadmin.system.service.UserService;
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
    public List<String> queryMenuByUserCode(String userCode) throws AuthenticationException {
        return userMapper.queryMenuByUserCode(userCode);
    }
}
