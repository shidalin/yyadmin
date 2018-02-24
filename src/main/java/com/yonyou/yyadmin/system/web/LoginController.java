package com.yonyou.yyadmin.system.web;

import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.common.utils.Constant;
import com.yonyou.yyadmin.core.AbstractController;
import com.yonyou.yyadmin.core.Result;
import com.yonyou.yyadmin.core.ResultGenerator;
import com.yonyou.yyadmin.oauth2.OAuth2Token;
import com.yonyou.yyadmin.system.entity.UserToken;
import com.yonyou.yyadmin.system.service.UserService;
import com.yonyou.yyadmin.system.service.UserTokenService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登陆
     * 前后分离session机制：
     * 权限控制机制：用户登陆验证成功之后，生成token，保存到redis<token,userCode>,返回到前端，前端每次请求带上token,过期自动销毁，根据携带的token判断权限
     *
     * @return
     * @throws IOException
     */
    @SystemLogAnnotation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map paramsMap, @RequestHeader String token) throws IOException {
        String username = paramsMap.get("username") == null ? "" : paramsMap.get("username").toString();
        String password = paramsMap.get("password") == null ? "" : paramsMap.get("password").toString();
        //密码加密
        password = new Sha256Hash(password, Constant.PASSWORD_SALT).toHex();
        //shiro登陆
        Subject subject = SecurityUtils.getSubject();
        //构建token
        OAuth2Token oAuth2Token = new OAuth2Token(username, password, token);
        //shiro登陆
        subject.login(oAuth2Token);
        //是否认证
        if (subject.isAuthenticated()) {
            //认证成功，生成token，存储到数据库，并返回到前台
            UserToken userToken = (UserToken) subject.getPrincipal();
            Result result = ResultGenerator.genSuccessResult();
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("token", userToken.getToken());
            dataMap.put("expires", Constant.EXPIRES);
            result.setData(dataMap);
            result.setMessage("登陆成功");
            return result;
        }
        //认证失败，返回错误信息
        //错误信息“用户编码密码错误”，不用“用户编码错误”，“用户密码错误”，防止一些恶意用户非法扫描帐号库
        Result result = ResultGenerator.genFailResult("登录失败");
        return result;
    }

}
