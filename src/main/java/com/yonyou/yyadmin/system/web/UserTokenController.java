package com.yonyou.yyadmin.system.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.core.AbstractController;
import com.yonyou.yyadmin.core.Result;
import com.yonyou.yyadmin.core.ResultGenerator;
import com.yonyou.yyadmin.system.entity.UserToken;
import com.yonyou.yyadmin.system.service.UserTokenService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 用户TOKEN 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2017-11-05
 */
@RestController
@RequestMapping("/system/userToken")
public class UserTokenController extends AbstractController {

    @Autowired
    private UserTokenService service;

    @SystemLogAnnotation("新增用户TOKEN信息")
    @RequiresPermissions("UserToken:add")
    @PostMapping("/add")
    public Result create(@RequestBody UserToken t){
        if(service.insert(t)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("新增失败");
        }
    }

    @SystemLogAnnotation("批量删除用户TOKEN信息")
    @RequiresPermissions("UserToken:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[]ids){
        if(service.deleteBatchIds(Arrays.asList(ids))){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("批量删除失败");
        }
    }

    @SystemLogAnnotation("删除用户TOKEN信息")
    @RequiresPermissions("UserToken:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id){
        if(service.deleteById(id)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @SystemLogAnnotation("修改用户TOKEN信息")
    @RequiresPermissions("UserToken:update")
    @PostMapping("/update")
    public Result update(@RequestBody UserToken t){
        if(service.updateById(t)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("更新失败");
        }
    }


    @RequiresPermissions("UserToken:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id){
        return ResultGenerator.genSuccessResult(service.selectById(id));
    }

    @RequiresPermissions("UserToken:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<UserToken> pageEntity){
        pageEntity=service.selectPage(pageEntity,new EntityWrapper<UserToken>());
        return ResultGenerator.genSuccessResult(pageEntity);
    }
}
