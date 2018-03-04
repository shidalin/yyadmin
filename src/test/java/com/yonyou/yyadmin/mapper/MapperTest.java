package com.yonyou.yyadmin.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yonyou.yyadmin.YYAdminApplicationTests;
import com.yonyou.yyadmin.system.entity.User;
import com.yonyou.yyadmin.system.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MapperTest extends YYAdminApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void mapperTestForSelect() {
        String userCode = "admin";
        User user = new User();
        user.setUserCode(userCode);
        User selectUser = userMapper.selectOne(user);
//                selectOne(new EntityWrapper().eq("user_code", userCode));
        selectUser.toString();
    }
}
