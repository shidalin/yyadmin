package com.yonyou.yyadmin.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yonyou.yyadmin.common.utils.Constant;
import com.yonyou.yyadmin.core.AbstractServiceImpl;
import com.yonyou.yyadmin.oauth2.TokenGenerator;
import com.yonyou.yyadmin.system.entity.UserToken;
import com.yonyou.yyadmin.system.mapper.UserTokenMapper;
import com.yonyou.yyadmin.system.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 用户TOKEN 服务实现类
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */
@Service
public class UserTokenServiceImpl extends AbstractServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    @Autowired
    private UserTokenMapper userTokenMapper;

    @Override
    public UserToken createToken(String userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + Constant.EXPIRES * 1000);

        //判断是否生成过token
        UserToken userToken = this.selectOne(new EntityWrapper<UserToken>().eq("user_id",userId));
        if (userToken == null) {
            userToken = new UserToken();
            userToken.setUserId(userId);
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireTime);
            //保存token
            this.insert(userToken);
        } else {
            //更新token信息
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireTime);
            //更新token
            this.updateById(userToken);
        }
        return userToken;
    }
}
