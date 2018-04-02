package com.yonyou.yyadmin.modules.order.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.yonyou.yyadmin.base.AbstractModel;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author shidalin
 * @since 2018-04-02
 */
public class Material extends AbstractModel<Material> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
	@TableField("material_name")
	private String materialName;
    /**
     * 商品编码
     */
	@TableField("material_code")
	private String materialCode;
    /**
     * 单位
     */
	private String measdoc;
    /**
     * 单位重量
     */
	private Double unitweight;
    /**
     * 商品分类
     */
	private String marbasclass;
    /**
     * 规格
     */
	private String materialspec;
    /**
     * 型号
     */
	private String materialtype;
    /**
     * 备注
     */
	private String vmemo;


	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMeasdoc() {
		return measdoc;
	}

	public void setMeasdoc(String measdoc) {
		this.measdoc = measdoc;
	}

	public Double getUnitweight() {
		return unitweight;
	}

	public void setUnitweight(Double unitweight) {
		this.unitweight = unitweight;
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

	public String getMaterialtype() {
		return materialtype;
	}

	public void setMaterialtype(String materialtype) {
		this.materialtype = materialtype;
	}

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}


	@Override
	public String toString() {
		return "Material{" +
			", materialName=" + materialName +
			", materialCode=" + materialCode +
			", measdoc=" + measdoc +
			", unitweight=" + unitweight +
			", marbasclass=" + marbasclass +
			", materialspec=" + materialspec +
			", materialtype=" + materialtype +
			", vmemo=" + vmemo +
			"}";
	}
}
