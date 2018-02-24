package com.yonyou.yyadmin.common.aspect;

import com.google.gson.Gson;
import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.common.utils.HttpContextUtils;
import com.yonyou.yyadmin.common.utils.IPUtils;
import com.yonyou.yyadmin.system.entity.SystemLog;
import com.yonyou.yyadmin.system.entity.User;
import com.yonyou.yyadmin.system.service.SystemLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类,自动记录操作日志
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SystemLogService systemLogService;

    @Pointcut("@annotation(com.yonyou.yyadmin.common.annotation.SystemLogAnnotation)")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SystemLog sysLog = new SystemLog();
        SystemLogAnnotation systemLogAnnotation = method.getAnnotation(SystemLogAnnotation.class);
        if (systemLogAnnotation != null) {
            //注解上的描述
            sysLog.setOpration(systemLogAnnotation.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
//            new StringBuffer();
//          for(Object arg:args){
//              if(arg!=null){
//
//              }
//          }
            String params = new Gson().toJson(args);
//            String params = JSON.toJSON(args[0]).toString();
            sysLog.setParams(params);
        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        //用户名
        if ("用户登录".equals(systemLogAnnotation.value())) {
            String username = request.getParameter("username");
            sysLog.setUserName(username);
        } else {
            String username = ((User) SecurityUtils.getSubject().getPrincipal()).getUserName();
            sysLog.setUserName(username);
        }
        sysLog.setGmtCreate(new Date());
        //保存系统日志
        systemLogService.insert(sysLog);
    }

}
