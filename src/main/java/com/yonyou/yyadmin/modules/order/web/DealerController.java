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
import com.yonyou.yyadmin.modules.order.entity.Dealer;
import com.yonyou.yyadmin.modules.order.service.DealerService;

import java.util.Arrays;

/**
 * <p>
 * 经销商 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2018-04-02
 */
@RestController
@RequestMapping("/order/dealer")
public class DealerController extends AbstractController {

    @Autowired
    private DealerService service;

    @SystemLogAnnotation("新增经销商信息")
    @RequiresPermissions("Dealer:add")
    @PostMapping("/add")
    public Result create(@RequestBody Dealer t){
            if(service.insert(t)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("新增失败");
            }
            }

    @SystemLogAnnotation("批量删除经销商信息")
    @RequiresPermissions("Dealer:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[]ids){
            if(service.deleteBatchIds(Arrays.asList(ids))){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("批量删除失败");
            }
    }

    @SystemLogAnnotation("删除经销商信息")
    @RequiresPermissions("Dealer:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id){
            if(service.deleteById(id)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("删除失败");
            }
     }

    @SystemLogAnnotation("修改经销商信息")
    @RequiresPermissions("Dealer:update")
    @PostMapping("/update")
    public Result update(@RequestBody Dealer t){
            if(service.updateById(t)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("更新失败");
            }
    }


    @RequiresPermissions("Dealer:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id){
            return ResultGenerator.genSuccessResult(service.selectById(id));
            }

    @RequiresPermissions("Dealer:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<Dealer> pageEntity){
            pageEntity=service.selectPage(pageEntity,new EntityWrapper<Dealer>());
            return ResultGenerator.genSuccessResult(pageEntity);
            }
    }
