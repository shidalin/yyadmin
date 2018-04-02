package com.yonyou.yyadmin.modules.order.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yonyou.yyadmin.base.AbstractModel;

/**
 * <p>
 * 订单_商品信息
 * </p>
 *
 * @author shidalin
 * @since 2018-04-02
 */
@TableName("po_order_item")
public class PoOrderItem extends AbstractModel<PoOrderItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 主表主键
     */
	private String pid;
    /**
     * 商品编码
     */
	@TableField("material_id")
	private String materialId;
    /**
     * 商品名称
     */
	@TableField("material_name")
	private String materialName;
    /**
     * 数量
     */
	private Double nnum;
    /**
     * 重量
     */
	private Double nweight;
    /**
     * 单位
     */
	private String castunitid;
    /**
     * 含税单价
     */
	private Double nqtorigtaxprice;
    /**
     * 价税合计
     */
	private Double norigtaxmny;
    /**
     * 分类
     */
	private String marbasclass;
    /**
     * 规格
     */
	private String materialspec;
    /**
     * 备注
     */
	private String vmemo;


	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Double getNnum() {
		return nnum;
	}

	public void setNnum(Double nnum) {
		this.nnum = nnum;
	}

	public Double getNweight() {
		return nweight;
	}

	public void setNweight(Double nweight) {
		this.nweight = nweight;
	}

	public String getCastunitid() {
		return castunitid;
	}

	public void setCastunitid(String castunitid) {
		this.castunitid = castunitid;
	}

	public Double getNqtorigtaxprice() {
		return nqtorigtaxprice;
	}

	public void setNqtorigtaxprice(Double nqtorigtaxprice) {
		this.nqtorigtaxprice = nqtorigtaxprice;
	}

	public Double getNorigtaxmny() {
		return norigtaxmny;
	}

	public void setNorigtaxmny(Double norigtaxmny) {
		this.norigtaxmny = norigtaxmny;
	}

	public String getMarbasclass() {
		return marbasclass;
	}

	public void setMarbasclass(String marbasclass) {
		this.marbasclass = marbasclass;
	}

	public String getMaterialspec() {
		return materialspec;
	}

	public void setMaterialspec(String materialspec) {
		this.materialspec = materialspec;
	}

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}


	@Override
	public String toString() {
		return "PoOrderItem{" +
			", pid=" + pid +
			", materialId=" + materialId +
			", materialName=" + materialName +
			", nnum=" + nnum +
			", nweight=" + nweight +
			", castunitid=" + castunitid +
			", nqtorigtaxprice=" + nqtorigtaxprice +
			", norigtaxmny=" + norigtaxmny +
			", marbasclass=" + marbasclass +
			", materialspec=" + materialspec +
			", vmemo=" + vmemo +
			"}";
	}
}
