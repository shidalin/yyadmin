package com.yonyou.iviewadmin.system.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.iviewadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.iviewadmin.core.AbstractController;
import com.yonyou.iviewadmin.core.Result;
import com.yonyou.iviewadmin.core.ResultGenerator;
import com.yonyou.iviewadmin.system.entity.RoleMenu;
import com.yonyou.iviewadmin.system.service.RoleMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 角色菜单关联表 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2017-11-05
 */
@RestController
@RequestMapping("/system/roleMenu")
public class RoleMenuController extends AbstractController {

    @Autowired
    private RoleMenuService service;

    @SystemLogAnnotation("新增角色菜单关联表信息")
    @RequiresPermissions("RoleMenu:add")
    @PostMapping("/add")
    public Result create(@RequestBody RoleMenu t){
        if(service.insert(t)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("新增失败");
        }
    }

    @SystemLogAnnotation("批量删除角色菜单关联表信息")
    @RequiresPermissions("RoleMenu:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[]ids){
        if(service.deleteBatchIds(Arrays.asList(ids))){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("批量删除失败");
        }
    }

    @SystemLogAnnotation("删除角色菜单关联表信息")
    @RequiresPermissions("RoleMenu:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id){
        if(service.deleteById(id)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @SystemLogAnnotation("修改角色菜单关联表信息")
    @RequiresPermissions("RoleMenu:update")
    @PostMapping("/update")
    public Result update(@RequestBody RoleMenu t){
        if(service.updateById(t)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("更新失败");
        }
    }


    @RequiresPermissions("RoleMenu:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id){
        return ResultGenerator.genSuccessResult(service.selectById(id));
    }

    @RequiresPermissions("RoleMenu:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<RoleMenu> pageEntity){
        pageEntity=service.selectPage(pageEntity,new EntityWrapper<RoleMenu>());
        return ResultGenerator.genSuccessResult(pageEntity);
    }
}
