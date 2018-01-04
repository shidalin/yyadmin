package com.yonyou.iviewadmin.common.utils;

import com.yonyou.iviewadmin.core.ServiceException;
import com.yonyou.iviewadmin.system.entity.UserToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
//        return HttpContextUtils.getHttpSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static UserToken getUserEntity() {
        return (UserToken) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getUserId() {
        return getUserEntity().getUserId();
    }

    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(String key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new ServiceException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

}
