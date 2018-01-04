package com.yonyou.iviewadmin.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.yonyou.iviewadmin.core.AbstractModel;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author shidalin
 * @since 2017-09-26
 */
public class Menu extends AbstractModel<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 上级菜单
     */
    @TableField("menu_pid")
    private String menuPid;
    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;
    /**
     * 菜单编码
     */
    @TableField("menu_code")
    private String menuCode;
    /**
     * 菜单链接
     */
    @TableField("menu_url")
    private String menuUrl;
    /**
     * 菜单权限
     */
    @TableField("menu_permission")
    private String menuPermission;
    /**
     * 菜单图标
     */
    @TableField("menu_icon")
    private String menuIcon;
    /**
     * 菜单序列
     */
    @TableField("menu_seq")
    private Integer menuSeq;
    /**
     * 菜单类型
     */
    @TableField("menu_type")
    private Integer menuType;

    @TableField("menu_pname")
    private String menuPname;

    public String getMenuPid() {
        return menuPid;
    }

    public void setMenuPid(String menuPid) {
        this.menuPid = menuPid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuPermission() {
        return menuPermission;
    }

    public void setMenuPermission(String menuPermission) {
        this.menuPermission = menuPermission;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getMenuSeq() {
        return menuSeq;
    }

    public void setMenuSeq(Integer menuSeq) {
        this.menuSeq = menuSeq;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }


    @Override
    public String toString() {
        return "Menu{" +
                ", menuPid=" + menuPid +
                ", menuName=" + menuName +
                ", menuCode=" + menuCode +
                ", menuUrl=" + menuUrl +
                ", menuPermission=" + menuPermission +
                ", menuIcon=" + menuIcon +
                ", menuSeq=" + menuSeq +
                ", menuType=" + menuType +
                "}";
    }

    public void setMenuPname(String menuPname) {
        this.menuPname = menuPname;
    }

    public String getMenuPname() {
        return menuPname;
    }
}
