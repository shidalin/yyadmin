package com.yonyou.yyadmin.system.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.core.AbstractController;
import com.yonyou.yyadmin.core.Result;
import com.yonyou.yyadmin.core.ResultGenerator;
import com.yonyou.yyadmin.system.entity.UserRole;
import com.yonyou.yyadmin.system.service.UserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 用户角色关联表 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2017-11-05
 */
@RestController
@RequestMapping("/system/userRole")
public class UserRoleController extends AbstractController {

    @Autowired
    private UserRoleService service;

    @SystemLogAnnotation("新增用户角色关联表信息")
    @RequiresPermissions("UserRole:add")
    @PostMapping("/add")
    public Result create(@RequestBody UserRole t){
        if(service.insert(t)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("新增失败");
        }
    }

    @SystemLogAnnotation("批量删除用户角色关联表信息")
    @RequiresPermissions("UserRole:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[]ids){
        if(service.deleteBatchIds(Arrays.asList(ids))){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("批量删除失败");
        }
    }

    @SystemLogAnnotation("删除用户角色关联表信息")
    @RequiresPermissions("UserRole:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id){
        if(service.deleteById(id)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @SystemLogAnnotation("修改用户角色关联表信息")
    @RequiresPermissions("UserRole:update")
    @PostMapping("/update")
    public Result update(@RequestBody UserRole t){
        if(service.updateById(t)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("更新失败");
        }
    }


    @RequiresPermissions("UserRole:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id){
        return ResultGenerator.genSuccessResult(service.selectById(id));
    }

    @RequiresPermissions("UserRole:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<UserRole> pageEntity){
        pageEntity=service.selectPage(pageEntity,new EntityWrapper<UserRole>());
        return ResultGenerator.genSuccessResult(pageEntity);
    }
}
