package com.yonyou.iviewadmin.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 * 收集认证账号和凭证
 *
 * @author shidalin
 */
public class OAuth2Token implements AuthenticationToken {

    private String token;

    private String principal;

    private String credential;

    public OAuth2Token(String principal, String credential, String token) {
        this.principal = principal;
        this.credential = credential;
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credential;
    }

    public String getToken() {
        return token;
    }
}
