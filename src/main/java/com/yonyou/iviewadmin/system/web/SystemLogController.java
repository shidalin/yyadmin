package com.yonyou.iviewadmin.system.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.iviewadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.iviewadmin.core.AbstractController;
import com.yonyou.iviewadmin.core.Result;
import com.yonyou.iviewadmin.core.ResultGenerator;
import com.yonyou.iviewadmin.system.entity.SystemLog;
import com.yonyou.iviewadmin.system.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2017-11-05
 */
@RestController
@RequestMapping("/system/systemLog")
public class SystemLogController extends AbstractController {

    @Autowired
    private SystemLogService service;

    @SystemLogAnnotation("新增系统日志信息")
    @RequiresPermissions("SystemLog:add")
    @PostMapping("/add")
    public Result create(@RequestBody SystemLog t){
        if(service.insert(t)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("新增失败");
        }
    }

    @SystemLogAnnotation("批量删除系统日志信息")
    @RequiresPermissions("SystemLog:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[]ids){
        if(service.deleteBatchIds(Arrays.asList(ids))){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("批量删除失败");
        }
    }

    @SystemLogAnnotation("删除系统日志信息")
    @RequiresPermissions("SystemLog:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id){
        if(service.deleteById(id)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @SystemLogAnnotation("修改系统日志信息")
    @RequiresPermissions("SystemLog:update")
    @PostMapping("/update")
    public Result update(@RequestBody SystemLog t){
        if(service.updateById(t)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("更新失败");
        }
    }


    @RequiresPermissions("SystemLog:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id){
        return ResultGenerator.genSuccessResult(service.selectById(id));
    }

    @RequiresPermissions("SystemLog:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<SystemLog> pageEntity){
        pageEntity=service.selectPage(pageEntity,new EntityWrapper<SystemLog>());
        return ResultGenerator.genSuccessResult(pageEntity);
    }
}
