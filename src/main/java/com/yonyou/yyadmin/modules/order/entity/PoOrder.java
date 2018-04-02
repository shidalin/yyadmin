package com.yonyou.yyadmin.modules.order.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yonyou.yyadmin.base.AbstractModel;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author shidalin
 * @since 2018-04-02
 */
@TableName("po_order")
public class PoOrder extends AbstractModel<PoOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @TableField("order_code")
    private String orderCode;
    /**
     * 经销商编码
     */
    @TableField("dealer_id")
    private String dealerId;
    /**
     * 经销商名称
     */
    @TableField("dealer_name")
    private String dealerName;
    /**
     * 业务员
     */
    private String busiman;
    /**
     * 部门
     */
    private String dept;
    /**
     * 制单日期
     */
    private Date dbilldate;
    /**
     * 收货单位
     */
    private String recvunit;
    /**
     * 收货地址
     */
    private String recvadr;
    /**
     * 开票单位
     */
    private String invcunit;
    /**
     * 起运数量
     */
    private Double transnum;
    /**
     * 要求交货日期
     */
    private Date dreqdate;
    /**
     * 预定出货日期
     */
    private Date dpredate;
    /**
     * 币种
     */
    private String currency;
    /**
     * 备注
     */
    private String vmemo;


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getBusiman() {
        return busiman;
    }

    public void setBusiman(String busiman) {
        this.busiman = busiman;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Date getDbilldate() {
        return dbilldate;
    }

    public void setDbilldate(Date dbilldate) {
        this.dbilldate = dbilldate;
    }

    public String getRecvunit() {
        return recvunit;
    }

    public void setRecvunit(String recvunit) {
        this.recvunit = recvunit;
    }

    public String getRecvadr() {
        return recvadr;
    }

    public void setRecvadr(String recvadr) {
        this.recvadr = recvadr;
    }

    public String getInvcunit() {
        return invcunit;
    }

    public void setInvcunit(String invcunit) {
        this.invcunit = invcunit;
    }

    public Double getTransnum() {
        return transnum;
    }

    public void setTransnum(Double transnum) {
        this.transnum = transnum;
    }

    public Date getDreqdate() {
        return dreqdate;
    }

    public void setDreqdate(Date dreqdate) {
        this.dreqdate = dreqdate;
    }

    public Date getDpredate() {
        return dpredate;
    }

    public void setDpredate(Date dpredate) {
        this.dpredate = dpredate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getVmemo() {
        return vmemo;
    }

    public void setVmemo(String vmemo) {
        this.vmemo = vmemo;
    }


    @Override
    public String toString() {
        return "PoOrder{" +
                ", orderCode=" + orderCode +
                ", dealerId=" + dealerId +
                ", dealerName=" + dealerName +
                ", busiman=" + busiman +
                ", dept=" + dept +
                ", dbilldate=" + dbilldate +
                ", recvunit=" + recvunit +
                ", recvadr=" + recvadr +
                ", invcunit=" + invcunit +
                ", transnum=" + transnum +
                ", dreqdate=" + dreqdate +
                ", dpredate=" + dpredate +
                ", currency=" + currency +
                ", vmemo=" + vmemo +
                "}";
    }
}
