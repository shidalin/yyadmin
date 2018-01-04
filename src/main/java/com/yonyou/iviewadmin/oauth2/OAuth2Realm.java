package com.yonyou.iviewadmin.oauth2;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yonyou.iviewadmin.core.StatusCode;
import com.yonyou.iviewadmin.system.entity.User;
import com.yonyou.iviewadmin.system.entity.UserToken;
import com.yonyou.iviewadmin.system.service.ShiroService;
import com.yonyou.iviewadmin.system.service.UserService;
import com.yonyou.iviewadmin.system.service.UserTokenService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 权限认证（安全数据源）
 *
 * @author shidalin
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) authenticationToken;
        String token = oAuth2Token.getToken();
        String password = oAuth2Token.getCredentials() == null ? "" : oAuth2Token.getCredentials().toString();
        String username = oAuth2Token.getPrincipal();
        //当前登陆用户
        User user = null;
        UserToken userToken = null;
        //携带token登陆，根据token判断权限
        if (!StringUtils.isEmpty(token) && !"undefined".equals(token)) {
            userToken = userTokenService.selectOne(new EntityWrapper<UserToken>().eq("token", token));
            //token失效
            if (userToken == null || userToken.getExpireTime().getTime() < System.currentTimeMillis()) {
                throw new IncorrectCredentialsException("token失效，请重新登录");
            }
            user = userService.queryUserByAccessToken(token);
            //常见错误异常
            //DisabledAccountException（禁用的帐号）、LockedAccountException（锁定的帐号）、
            // UnknownAccountException（错误的帐号）、ExcessiveAttemptsException（登录失败次数过多）、
            // IncorrectCredentialsException （错误的凭证）、ExpiredCredentialsException（过期的凭证）
            if (user == null) {
                //错误账号
                throw new UnknownAccountException();
            }
        } else {
            user = userService.selectOne(new EntityWrapper<User>().eq("user_code", username).eq("user_pswd", password));
            if (!user.getUserPswd().equals(password)) {
                //错误凭证
                throw new IncorrectCredentialsException();
            }
            if (user.getStatus() == StatusCode.SUSPENDED.code) {
                //禁用的账号
                throw new DisabledAccountException();
            }
            //生成对应token信息
            userToken = userTokenService.createToken(user.getId());
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userToken, oAuth2Token.getCredentials(), getName());
        return info;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserToken userToken = (UserToken) principals.getPrimaryPrincipal();
        String userId = userToken.getUserId();
        //用户权限列表
        //注意：权限标识不能有空值
        Set<String> permsSet = shiroService.getUserPermissions(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }


}
