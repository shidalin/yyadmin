package com.yonyou.iviewadmin.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yonyou.iviewadmin.core.AbstractModel;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author shidalin
 * @since 2017-09-26
 */
@TableName("system_log")
public class SystemLog extends AbstractModel<SystemLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作人员
     */
	@TableField("user_id")
	private String userId;
    /**
     * 操作人员名称
     */
	@TableField("user_name")
	private String userName;
    /**
     * 动作
     */
	private String opration;
    /**
     * 动作方法
     */
	private String method;
    /**
     * 动作方法参数
     */
	private String params;
    /**
     * 动作目标链接
     */
	private String ip;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOpration() {
		return opration;
	}

	public void setOpration(String opration) {
		this.opration = opration;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	@Override
	public String toString() {
		return "SystemLog{" +
			", userId=" + userId +
			", userName=" + userName +
			", opration=" + opration +
			", method=" + method +
			", params=" + params +
			", ip=" + ip +
			"}";
	}
}
