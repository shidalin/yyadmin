package com.yonyou.yyadmin.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.yonyou.yyadmin.base.AbstractModel;

/**
 * <p>
 * 组织
 * </p>
 *
 * @author shidalin
 * @since 2017-09-26
 */
public class Organization extends AbstractModel<Organization> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织名称
     */
	@TableField("org_name")
	private String orgName;
    /**
     * 组织编码
     */
	@TableField("org_code")
	private String orgCode;
    /**
     * 上级组织
     */
	@TableField("org_pid")
	private String orgPid;
    /**
     * 组织序列
     */
	@TableField("org_seq")
	private Integer orgSeq;
    /**
     * 组织说明
     */
	@TableField("org_content")
	private String orgContent;


	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgPid() {
		return orgPid;
	}

	public void setOrgPid(String orgPid) {
		this.orgPid = orgPid;
	}

	public Integer getOrgSeq() {
		return orgSeq;
	}

	public void setOrgSeq(Integer orgSeq) {
		this.orgSeq = orgSeq;
	}

	public String getOrgContent() {
		return orgContent;
	}

	public void setOrgContent(String orgContent) {
		this.orgContent = orgContent;
	}


	@Override
	public String toString() {
		return "Organization{" +
			", orgName=" + orgName +
			", orgCode=" + orgCode +
			", orgPid=" + orgPid +
			", orgSeq=" + orgSeq +
			", orgContent=" + orgContent +
			"}";
	}
}
