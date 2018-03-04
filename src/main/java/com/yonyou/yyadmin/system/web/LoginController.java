package com.yonyou.yyadmin.system.web;

import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.base.AbstractController;
import com.yonyou.yyadmin.base.Result;
import com.yonyou.yyadmin.base.ResultGenerator;
import com.yonyou.yyadmin.shiro.CryptoUtil;
import com.yonyou.yyadmin.shiro.StatelessToken;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆相关
 *
 * @author shidalin
 */
@RestController
public class LoginController extends AbstractController {
    /**
     * 用户登陆
     * 权限控制机制：
     * 登录请求根据username/password进行用户认证授权，生成jwt返回前端；
     * 普通请求根据jwt进行用户授权认证
     *
     * @return
     * @throws IOException
     */
    @SystemLogAnnotation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map paramsMap) throws IOException {
        String username = paramsMap.get("username") == null ? "" : paramsMap.get("username").toString();
        String password = paramsMap.get("password") == null ? "" : paramsMap.get("password").toString();
        //shiro登陆
        Subject subject = SecurityUtils.getSubject();
        //构建token，设置jwt登录标志
        StatelessToken statelessToken = new StatelessToken(username, password, null, Boolean.TRUE);
        //shiro登陆，进入realm认证授权
        subject.login(statelessToken);
        //是否认证，代理subject认证无异常，isAuthenticated=true;
        if (subject.isAuthenticated()) {
            //认证成功，返回jwt到前台
            String jwt = statelessToken.getJwt();
            Result result = ResultGenerator.genSuccessResult();
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("jwt", jwt);
            //认证部分处理是否过期
//            dataMap.put("expires", Constant.EXPIRES);
            result.setData(dataMap);
            result.setMessage("登录成功");
            return result;
        }
        //认证失败，返回错误信息
        //错误信息“用户编码密码错误”，不用“用户编码错误”，“用户密码错误”，防止一些恶意用户非法扫描帐号库
        Result result = ResultGenerator.genFailResult("登录失败");
        return result;
    }

    @SystemLogAnnotation("用户登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result logout(HttpServletRequest httpRequest) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //从header中获取token
        String jwt = httpRequest.getHeader("jwt");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(jwt)) {
            jwt = httpRequest.getParameter("jwt");
        }
        //失效jwt
        jwt = CryptoUtil.disabledJWT(jwt);
        Result result = ResultGenerator.genSuccessResult();
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("jwt", jwt);
        result.setData(dataMap);
        result.setMessage("登出成功");
        return result;
    }

}
