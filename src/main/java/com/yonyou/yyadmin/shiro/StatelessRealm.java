package com.yonyou.yyadmin.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yonyou.yyadmin.base.ServiceException;
import com.yonyou.yyadmin.base.StatusCode;
import com.yonyou.yyadmin.common.utils.Constant;
import com.yonyou.yyadmin.system.entity.User;
import com.yonyou.yyadmin.system.service.ShiroService;
import com.yonyou.yyadmin.system.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限认证（安全数据源）
 *
 * @author shidalin
 */
@Component
public class StatelessRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //仅支持StatelessToken 类型的Token
        return authenticationToken instanceof StatelessToken;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) authenticationToken;
        if (statelessToken.isLogin()) {
            //登录请求
            String username = statelessToken.getPrincipal() == null ? "" : statelessToken.getPrincipal().toString();
            String password = statelessToken.getCredentials() == null ? "" : statelessToken.getCredentials().toString();
            User user = userService.selectOne(new EntityWrapper<User>().eq("user_code", username));
            if (user == null) {
                // 非法账号异常
                throw new UnknownAccountException();
            }
            if (!password.equals(user.getUserPswd())) {
                //错误凭证异常
                throw new IncorrectCredentialsException();
            }
            if (user.getStatus() == StatusCode.SUSPENDED.code) {
                //禁用的账号异常
                throw new DisabledAccountException();
            }
            //生成对应token信息
            //用户权限列表,注意：权限标识不能有空值
            Set<String> permsSet = shiroService.getUserPermissions(username);
            //更新jwt信息
            String jwt = CryptoUtil.issueJwt(username, Constant.EXPIRES, permsSet);
            statelessToken.setJwt(jwt);
        } else {
            //普通请求,验证jwt
            //转移至授权出校验jwt，认证授权一次校验
        }
        //传递jwt至授权方法,固定第一个参数
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(statelessToken, statelessToken.getCredentials(), getName());
        return info;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //认证方法传递过来的jwt
        StatelessToken statelessToken = (StatelessToken) principals.getPrimaryPrincipal();
        if (statelessToken.isLogin()) {
            //登录请求
            //登录请求不需要授权，移至登录认证部分
        } else {
            //普通请求
            String jwt = statelessToken.getJwt();
            try {
                //解析jwt
                Claims claims = CryptoUtil.parseJWT(statelessToken.getJwt());
                //获取权限信息,简单数据格式转换
                ArrayList<String> permsSet = (ArrayList<String>) claims.get(CryptoUtil.PERMISSION_KEY);
                HashSet<String> params = new HashSet<>();
                params.addAll(permsSet);
                //更新授权信息
                simpleAuthorizationInfo.setStringPermissions(params);
            } catch (ExpiredJwtException e) {
                // jwt过期
                throw new ServiceException("TOKEN过期，请重新登录");
            } catch (SignatureException e) {
                // jwt非法请求
                throw new ServiceException("TOKEN失效，非法请求");
            } catch (Exception e) {
                // jwt 异常请求
                throw new ServiceException("请求失败，" + e.getMessage());
            }
        }
        return simpleAuthorizationInfo;
    }


}
