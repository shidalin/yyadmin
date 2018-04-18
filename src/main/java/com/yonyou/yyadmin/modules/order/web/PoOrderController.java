package com.yonyou.yyadmin.modules.order.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.base.AbstractController;
import com.yonyou.yyadmin.base.Result;
import com.yonyou.yyadmin.base.ResultGenerator;
import com.yonyou.yyadmin.modules.order.dto.PoOrderDTO;
import com.yonyou.yyadmin.modules.order.entity.PoOrderItem;
import com.yonyou.yyadmin.modules.order.service.PoOrderItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yonyou.yyadmin.modules.order.entity.PoOrder;
import com.yonyou.yyadmin.modules.order.service.PoOrderService;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2018-04-02
 */
@RestController
@RequestMapping("/order/poOrder")
public class PoOrderController extends AbstractController {

    @Autowired
    private PoOrderService service;

    @Autowired
    private PoOrderItemService poOrderItemService;

    @SystemLogAnnotation("新增订单信息")
    @RequiresPermissions("PoOrder:add")
    @PostMapping("/add")
    public Result create(@RequestBody PoOrderDTO t) {
        PoOrder parentEntity = t.getParentEntity();
        List<PoOrderItem> childEntityList = t.getChildEntityList();
        if (service.insert(parentEntity)) {
            parentEntity = service.selectOne(new EntityWrapper<PoOrder>().eq("order_code", parentEntity.getOrderCode()));
            for (PoOrderItem childEntity : childEntityList) {
                childEntity.setPid(parentEntity.getId());
            }
            //批量保存子表数据
            poOrderItemService.insertBatch(childEntityList);
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("新增失败");
        }
    }

    @SystemLogAnnotation("批量删除订单信息")
    @RequiresPermissions("PoOrder:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[] ids) {
        if (service.deleteBatchIds(Arrays.asList(ids))) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("批量删除失败");
        }
    }

    @SystemLogAnnotation("删除订单信息")
    @RequiresPermissions("PoOrder:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id) {
        if (service.deleteById(id)) {
            //删除子表数据
            poOrderItemService.delete(new EntityWrapper<PoOrderItem>().eq("pid",id));
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @SystemLogAnnotation("修改订单信息")
    @RequiresPermissions("PoOrder:update")
    @PostMapping("/update")
    public Result update(@RequestBody PoOrder t) {
        if (service.updateById(t)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("更新失败");
        }
    }


    @RequiresPermissions("PoOrder:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id) {
        return ResultGenerator.genSuccessResult(service.selectById(id));
    }

    @RequiresPermissions("PoOrder:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<PoOrder> pageEntity) {
        pageEntity = service.selectPage(pageEntity, new EntityWrapper<PoOrder>());
        return ResultGenerator.genSuccessResult(pageEntity);
    }
}
