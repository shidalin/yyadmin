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
import com.yonyou.yyadmin.modules.order.entity.PoOrderItem;
import com.yonyou.yyadmin.modules.order.service.PoOrderItemService;

import java.util.Arrays;

/**
 * <p>
 * 订单_商品信息 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2018-04-02
 */
@RestController
@RequestMapping("/order/poOrderItem")
public class PoOrderItemController extends AbstractController {

    @Autowired
    private PoOrderItemService service;

    @SystemLogAnnotation("新增订单_商品信息信息")
    @RequiresPermissions("PoOrderItem:add")
    @PostMapping("/add")
    public Result create(@RequestBody PoOrderItem t){
            if(service.insert(t)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("新增失败");
            }
            }

    @SystemLogAnnotation("批量删除订单_商品信息信息")
    @RequiresPermissions("PoOrderItem:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[]ids){
            if(service.deleteBatchIds(Arrays.asList(ids))){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("批量删除失败");
            }
    }

    @SystemLogAnnotation("删除订单_商品信息信息")
    @RequiresPermissions("PoOrderItem:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id){
            if(service.deleteById(id)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("删除失败");
            }
     }

    @SystemLogAnnotation("修改订单_商品信息信息")
    @RequiresPermissions("PoOrderItem:update")
    @PostMapping("/update")
    public Result update(@RequestBody PoOrderItem t){
            if(service.updateById(t)){
            return ResultGenerator.genSuccessResult();
            }else{
            return ResultGenerator.genFailResult("更新失败");
            }
    }


    @RequiresPermissions("PoOrderItem:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id){
            return ResultGenerator.genSuccessResult(service.selectById(id));
            }

    @RequiresPermissions("PoOrderItem:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<PoOrderItem> pageEntity){
            pageEntity=service.selectPage(pageEntity,new EntityWrapper<PoOrderItem>());
            return ResultGenerator.genSuccessResult(pageEntity);
            }
    }
