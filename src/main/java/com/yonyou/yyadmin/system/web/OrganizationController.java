package com.yonyou.yyadmin.system.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.base.AbstractController;
import com.yonyou.yyadmin.base.Result;
import com.yonyou.yyadmin.base.ResultGenerator;
import com.yonyou.yyadmin.system.entity.Organization;
import com.yonyou.yyadmin.system.service.OrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 组织 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2017-11-05
 */
@RestController
@RequestMapping("/system/organization")
public class OrganizationController extends AbstractController {

    @Autowired
    private OrganizationService service;

    @SystemLogAnnotation("新增组织信息")
    @RequiresPermissions("Organization:add")
    @PostMapping("/add")
    public Result create(@RequestBody Organization t) {
        if (service.insert(t)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("新增失败");
        }
    }

    @SystemLogAnnotation("批量删除组织信息")
    @RequiresPermissions("Organization:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[] ids) {
        if (service.deleteBatchIds(Arrays.asList(ids))) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("批量删除失败");
        }
    }

    @SystemLogAnnotation("删除组织信息")
    @RequiresPermissions("Organization:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id) {
        if (service.deleteById(id)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @SystemLogAnnotation("修改组织信息")
    @RequiresPermissions("Organization:update")
    @PostMapping("/update")
    public Result update(@RequestBody Organization t) {
        if (service.updateById(t)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("更新失败");
        }
    }


    @RequiresPermissions("Organization:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id) {
        return ResultGenerator.genSuccessResult(service.selectById(id));
    }

    @RequiresPermissions("Organization:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<Organization> pageEntity) {
        pageEntity = service.selectPage(pageEntity, new EntityWrapper<Organization>());
        return ResultGenerator.genSuccessResult(pageEntity);
    }
}
