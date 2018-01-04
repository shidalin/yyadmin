package com.yonyou.iviewadmin.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yonyou.iviewadmin.core.AbstractModel;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author shidalin
 * @since 2017-09-26
 */
@TableName("user_role")
public class UserRole extends AbstractModel<UserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 关联用户
     */
	@TableField("user_id")
	private String userId;
    /**
     * 关联角色
     */
	@TableField("role_id")
	private String roleId;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	@Override
	public String toString() {
		return "UserRole{" +
			", userId=" + userId +
			", roleId=" + roleId +
			"}";
	}
}
