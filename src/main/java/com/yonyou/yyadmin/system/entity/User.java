package com.yonyou.yyadmin.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.yonyou.yyadmin.core.AbstractModel;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author shidalin
 * @since 2017-09-26
 */
public class User extends AbstractModel<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
	@TableField("user_name")
	private String userName;
    /**
     * 用户编码
     */
	@TableField("user_code")
	private String userCode;
    /**
     * 用户密码
     */
	@TableField("user_pswd")
	private String userPswd;
    /**
     * 用户邮箱
     */
	@TableField("user_email")
	private String userEmail;
    /**
     * 用户手机
     */
	@TableField("user_phone")
	private String userPhone;
    /**
     * 用户类型
     */
	@TableField("user_type")
	private String userType;
    /**
     * 盐
     */
	private String salt;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserPswd() {
		return userPswd;
	}

	public void setUserPswd(String userPswd) {
		this.userPswd = userPswd;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}


	@Override
	public String toString() {
		return "User{" +
			", userName=" + userName +
			", userCode=" + userCode +
			", userPswd=" + userPswd +
			", userEmail=" + userEmail +
			", userPhone=" + userPhone +
			", userType=" + userType +
			", salt=" + salt +
			"}";
	}
}
