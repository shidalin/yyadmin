package com.yonyou.yyadmin.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yonyou.yyadmin.core.AbstractModel;

import java.util.Date;

/**
 * <p>
 * 用户TOKEN
 * </p>
 *
 * @author shidalin
 * @since 2017-09-26
 */
@TableName("user_token")
public class UserToken extends AbstractModel<UserToken> {

    private static final long serialVersionUID = 1L;

    /**
     * 关联用户
     */
	@TableField("user_id")
	private String userId;
    /**
     * TOKEN
     */
	private String token;
    /**
     * 过期时间
     */
	@TableField("expire_time")
	private Date expireTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	@Override
	public String toString() {
		return "UserToken{" +
			", userId=" + userId +
			", token=" + token +
			", expireTime=" + expireTime +
			", updateTime=" + updateTime +
			"}";
	}
}
