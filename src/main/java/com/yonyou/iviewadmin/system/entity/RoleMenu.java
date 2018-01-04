package com.yonyou.iviewadmin.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yonyou.iviewadmin.core.AbstractModel;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author shidalin
 * @since 2017-09-26
 */
@TableName("role_menu")
public class RoleMenu extends AbstractModel<RoleMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 关联角色
     */
	@TableField("role_id")
	private String roleId;
    /**
     * 关联菜单
     */
	@TableField("menu_id")
	private String menuId;


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


	@Override
	public String toString() {
		return "RoleMenu{" +
			", roleId=" + roleId +
			", menuId=" + menuId +
			"}";
	}
}
