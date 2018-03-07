package com.yonyou.yyadmin.shiro;

import com.google.gson.Gson;
import com.yonyou.yyadmin.base.Result;
import com.yonyou.yyadmin.base.ResultCode;
import com.yonyou.yyadmin.base.ResultGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 根据当前请求上下文信息每次请求时都要登录的认证过滤器
 *
 * @author shidalin
 */
public class StatelessAuthcFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String jwt = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(jwt)) {
            return null;
        }
        //非登录请求，请求标志默认为false
        //增加获取subject,预先解析payload
        String jwtPayload = CryptoUtil.parseJwtPayload(jwt);
        Map<String, Object> jwtPayloadMap = CryptoUtil.readValue(jwtPayload);
        //获取userCode
        String sub = jwtPayloadMap.get("sub") == null ? "" : jwtPayloadMap.get("sub").toString();
        return new StatelessToken(sub, jwt);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //无session访问，每次都需要验证
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token) || "undefined".equals(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            Result result = ResultGenerator.genFailResult("invalid token");
            result.setCode(ResultCode.UNAUTHORIZED);
            result.setMessage("用户没有访问权限");
            String json = new Gson().toJson(result);
            httpResponse.getWriter().print(json);
            return false;
        }
        //需要权限认证的请求
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result result = ResultGenerator.genFailResult(throwable.getMessage());
            result.setCode(ResultCode.UNAUTHORIZED);
            String json = new Gson().toJson(result);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            return true;
        }
        return false;
    }

    /**
     * 获取请求的jwt
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("jwt");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("jwt");
        }
        return token;
    }
}
