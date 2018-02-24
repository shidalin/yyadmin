package com.yonyou.yyadmin.system.entity;

import java.util.List;

/**
 * Created by shidl on 2017/7/8.
 * DTO=data2object
 */
public class MenuDTO  {
    private Menu menu;
    private List<MenuDTO> childList;
    private String menuPName;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MenuDTO> getChildList() {
        return childList;
    }

    public void setChildList(List<MenuDTO> childList) {
        this.childList = childList;
    }

    public String getMenuPName() {
        return menuPName;
    }

    public void setMenuPName(String menuPName) {
        this.menuPName = menuPName;
    }
}
