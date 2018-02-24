package com.yonyou.yyadmin.system.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.core.AbstractController;
import com.yonyou.yyadmin.core.Result;
import com.yonyou.yyadmin.core.ResultGenerator;
import com.yonyou.yyadmin.system.entity.Menu;
import com.yonyou.yyadmin.system.entity.MenuDTO;
import com.yonyou.yyadmin.system.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends AbstractController {

    @Autowired
    private MenuService service;

    @SystemLogAnnotation("新增菜单信息")
    @RequiresPermissions("Menu:add")
    @PostMapping("/add")
    public Result create(@RequestBody Menu t) {
        if (service.insert(t)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("新增失败");
        }
    }

    @SystemLogAnnotation("批量删除菜单信息")
    @RequiresPermissions("Menu:removeAll")
    @PostMapping("/remove")
    public Result delete(@RequestBody String[] ids) {
        if (service.deleteBatchIds(Arrays.asList(ids))) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("批量删除失败");
        }
    }

    @SystemLogAnnotation("删除菜单信息")
    @RequiresPermissions("Menu:remove")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable String id) {
        if (service.deleteById(id)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @SystemLogAnnotation("修改菜单信息")
    @RequiresPermissions("Menu:update")
    @PostMapping("/update")
    public Result update(@RequestBody Menu t) {
        if (service.updateById(t)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("更新失败");
        }
    }


    @RequiresPermissions("Menu:detail")
    @PostMapping("/detail/{id}")
    public Result detail(@PathVariable String id) {
        return ResultGenerator.genSuccessResult(service.selectById(id));
    }

    @RequiresPermissions("Menu:list")
    @PostMapping("/list")
    public Result list(@RequestBody Page<Menu> pageEntity) {
        pageEntity = service.selectPage(pageEntity,new EntityWrapper<Menu>());
        return ResultGenerator.genSuccessResult(pageEntity);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("menu:select")
    public Result select() {
        //查询目录菜单
        List<Menu> list = service.selectList(new EntityWrapper<Menu>().eq("menu_type", "1"));
        //查询列表数据
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 权限菜单查询
     */
    @RequestMapping("/menu/user")
    public Result user() {
        List<MenuDTO> menuDTOList = service.getMenuListByUserId(getUserId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("menuDTOList", menuDTOList);
        return ResultGenerator.genSuccessResult(map);
    }
}
