package com.yonyou.yyadmin.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.yonyou.yyadmin.base.AbstractModel;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author shidalin
 * @since 2017-09-26
 */
public class Role extends AbstractModel<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
	@TableField("role_name")
	private String roleName;
    /**
     * 角色编码
     */
	@TableField("role_code")
	private String roleCode;
    /**
     * 备注
     */
	private String remark;


	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String toString() {
		return "Role{" +
			", roleName=" + roleName +
			", roleCode=" + roleCode +
			", remark=" + remark +
			"}";
	}
}
