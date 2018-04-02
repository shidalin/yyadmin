package com.yonyou.yyadmin.modules.order.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.yonyou.yyadmin.base.AbstractModel;

/**
 * <p>
 * 经销商
 * </p>
 *
 * @author shidalin
 * @since 2018-04-02
 */
public class Dealer extends AbstractModel<Dealer> {

    private static final long serialVersionUID = 1L;

    /**
     * 经销商名称
     */
	@TableField("dealer_name")
	private String dealerName;
    /**
     * 经销商编码
     */
	@TableField("dealer_code")
	private String dealerCode;


	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}


	@Override
	public String toString() {
		return "Dealer{" +
			", dealerName=" + dealerName +
			", dealerCode=" + dealerCode +
			"}";
	}
}
