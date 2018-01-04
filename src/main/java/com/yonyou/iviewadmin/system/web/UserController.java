package com.yonyou.iviewadmin.system.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.iviewadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.iviewadmin.core.AbstractController;
import com.yonyou.iviewadmin.core.Result;
import com.yonyou.iviewadmin.core.ResultGenerator;
import com.yonyou.iviewadmin.system.entity.User;
import com.yonyou.iviewadmin.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */
@RestController
@RequestMapping("/system/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService service;

    @SystemLogAnnotation("新增用户信息")
    @RequiresPermissions("User:add")
    @PostMapping("/add")
    public Result create(@RequestBody User t) {
        if (service.insert(t)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("新增失败");
        }
    }

    @SystemLogAnnotation("批量删除用户信息")
    @RequiresPermissions("User:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[] ids) {
        if (service.deleteBatchIds(Arrays.asList(ids))) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("批量删除失败");
        }
    }

    @SystemLogAnnotation("删除用户信息")
    @RequiresPermissions("User:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id) {
        if (service.deleteById(id)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @SystemLogAnnotation("修改用户信息")
    @RequiresPermissions("User:update")
    @PostMapping("/update")
    public Result update(@RequestBody User t) {
        if (service.updateById(t)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("更新失败");
        }
    }


    @RequiresPermissions("User:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id) {
        return ResultGenerator.genSuccessResult(service.selectById(id));
    }

    @RequiresPermissions("User:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<User> pageEntity) {
        pageEntity = service.selectPage(pageEntity, new EntityWrapper<User>());
        return ResultGenerator.genSuccessResult(pageEntity);
    }
}
