package com.yonyou.yyadmin.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yonyou.yyadmin.system.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */

@Component
public interface UserMapper extends BaseMapper<User> {

    List<String> queryAllPerms() throws AuthenticationException;

    List<String> queryPermsByUserId(String userId) throws AuthenticationException;

    User queryUserByAccessToken(String accessToken) throws AuthenticationException;
}