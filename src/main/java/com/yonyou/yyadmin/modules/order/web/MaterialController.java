package com.yonyou.yyadmin.modules.order.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.base.AbstractController;
import com.yonyou.yyadmin.base.Result;
import com.yonyou.yyadmin.base.ResultGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yonyou.yyadmin.modules.order.entity.Material;
import com.yonyou.yyadmin.modules.order.service.MaterialService;

import java.util.Arrays;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2018-04-02
 */
@RestController
@RequestMapping("/order/material")
public class MaterialController extends AbstractController {

    @Autowired
    private MaterialService service;

    @SystemLogAnnotation("新增商品信息")
    @RequiresPermissions("Material:add")
    @PostMapping("/add")
    public Result create(@RequestBody Material t){
            if(service.insert(t)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("新增失败");
            }
            }

    @SystemLogAnnotation("批量删除商品信息")
    @RequiresPermissions("Material:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[]ids){
            if(service.deleteBatchIds(Arrays.asList(ids))){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("批量删除失败");
            }
    }

    @SystemLogAnnotation("删除商品信息")
    @RequiresPermissions("Material:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id){
            if(service.deleteById(id)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("删除失败");
            }
     }

    @SystemLogAnnotation("修改商品信息")
    @RequiresPermissions("Material:update")
    @PostMapping("/update")
    public Result update(@RequestBody Material t){
            if(service.updateById(t)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("更新失败");
            }
    }


    @RequiresPermissions("Material:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id){
            return ResultGenerator.genSuccessResult(service.selectById(id));
            }

    @RequiresPermissions("Material:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<Material> pageEntity){
            pageEntity=service.selectPage(pageEntity,new EntityWrapper<Material>());
            return ResultGenerator.genSuccessResult(pageEntity);
            }
    }
