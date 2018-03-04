package com.yonyou.yyadmin.system.service.impl;

import com.yonyou.yyadmin.base.AbstractServiceImpl;
import com.yonyou.yyadmin.system.entity.Menu;
import com.yonyou.yyadmin.system.dto.MenuDTO;
import com.yonyou.yyadmin.system.mapper.MenuMapper;
import com.yonyou.yyadmin.system.service.MenuService;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */
@Service
public class MenuServiceImpl extends AbstractServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getMenuListByUserCode(String userCode) throws AuthenticationException {
        List<Menu> menuList = menuMapper.getMenuListByUserCode(userCode);
        if (menuList == null || menuList.size() == 0) {
            return null;
        }
        List<MenuDTO> menuDTOList = buildMenuTree(menuList);
        return menuDTOList;
    }

    /**
     * 构建菜单树
     *
     * @param menuList
     * @return
     */
    private List<MenuDTO> buildMenuTree(List<Menu> menuList) {
        HashMap<String, List<MenuDTO>> parentMenuId2ChildMenuList = new HashMap<String, List<MenuDTO>>();
        HashMap<String, MenuDTO> menuId2menuDTO = new HashMap<>();
        List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
        for (Menu menu : menuList) {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setMenu(menu);
            menuId2menuDTO.put(menuDTO.getMenu().getId(), menuDTO);
            if (menu.getMenuPid() == null || "0".equals(menu.getMenuPid())) {
                menuDTOList.add(menuDTO);
            } else {
                List<MenuDTO> childMenuList = null;
                if (parentMenuId2ChildMenuList.keySet().contains(menu.getMenuPid())) {
                    childMenuList = parentMenuId2ChildMenuList.get(menu.getMenuPid());

                } else {
                    childMenuList = new ArrayList<MenuDTO>();
                }
                childMenuList.add(menuDTO);
                parentMenuId2ChildMenuList.put(menu.getMenuPid(), childMenuList);
            }
        }
        recursiveTreeRelation(menuDTOList, parentMenuId2ChildMenuList);
        return menuDTOList;
    }

    /**
     * 递归构建树关系
     *
     * @param menuDTOList
     * @param parentMenuId2ChildMenuList
     */
    private void recursiveTreeRelation(List<MenuDTO> menuDTOList, HashMap<String, List<MenuDTO>> parentMenuId2ChildMenuList) {
        for (MenuDTO menuDTO : menuDTOList) {
            List<MenuDTO> childMenuList = parentMenuId2ChildMenuList.get(menuDTO.getMenu().getId());
            if (childMenuList != null && childMenuList.size() > 0) {
                menuDTO.setChildList(childMenuList);
                recursiveTreeRelation(childMenuList, parentMenuId2ChildMenuList);
            }
        }
    }

    /**
     * 复写，赋值menu_PName
     *
     * @return
     */
    public List<Menu> findAll() {
        List<Menu> menus = this.selectList(null);
        if (menus != null && menus.size() > 0) {
            HashMap<String, Menu> id2menu = new HashMap<>();
            for (Menu menu : menus) {
                id2menu.put(menu.getId(), menu);
            }
            for (Menu menu : menus) {
                Menu pmenu = id2menu.get(menu.getMenuPid());
                if (pmenu != null) {
                    menu.setMenuPname(pmenu.getMenuName());
                }
            }
        }
        return menus;
    }
}
