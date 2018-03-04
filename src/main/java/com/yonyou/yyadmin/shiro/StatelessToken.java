package com.yonyou.yyadmin.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 * 收集认证账号和凭证
 *
 * @author shidalin
 */
public class StatelessToken implements AuthenticationToken {

    private String principal;// 主体，用户名
    private String credentials;// 凭证，用户密码
    private String jwt; // json web token
    private boolean isLogin; // 登录标志


    public StatelessToken(String jwt) {
        this.principal = null;
        this.credentials = null;
        this.jwt = jwt;
        this.isLogin = Boolean.FALSE;
    }

    public StatelessToken(String principal, String credentials, String jwt) {
        this.principal = principal;
        this.credentials = credentials;
        this.jwt = jwt;
        this.isLogin = Boolean.FALSE;
    }

    public StatelessToken(String principal, String credentials, String jwt, boolean isLogin) {
        this.principal = principal;
        this.credentials = credentials;
        this.jwt = jwt;
        this.isLogin = isLogin;
    }

    //主体
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    //凭证
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    // 是否是登录
    public boolean isLogin() {
        return isLogin;
    }
}
