package com.yonyou.iviewadmin.system.service.impl;

import com.yonyou.iviewadmin.common.utils.Constant;
import com.yonyou.iviewadmin.system.entity.User;
import com.yonyou.iviewadmin.system.service.ShiroService;
import com.yonyou.iviewadmin.system.service.UserService;
import com.yonyou.iviewadmin.system.service.UserTokenService;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenService userTokenService;

    @Override
    public Set<String> getUserPermissions(String userId) throws AuthenticationException {
        List<String> permsList;
        User user = userService.selectById(userId);
        //系统管理员，拥有最高权限
        if (Constant.SUPER_ADMIN.endsWith(user.getUserType())) {
            permsList = userService.queryAllPerms();
        } else {
            permsList = userService.queryPermsByUserId(userId);
        }
        //用户权限列表-去重
        Set<String> permsSet = new HashSet<>();
        if (permsList != null && permsList.size() > 0) {
            for (String perms : permsList) {
                //拆分权限
                String[] strings = perms.split(",");
                permsSet.addAll(Arrays.asList(strings));
            }
        }
        return permsSet;
    }


}
