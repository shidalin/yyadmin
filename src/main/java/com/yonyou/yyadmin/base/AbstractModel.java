package com.yonyou.yyadmin.base;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by shidalin on 2017/9/22.
 **/

public abstract class AbstractModel<T extends Model> extends Model<T> {

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 业务状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;

    /**
     * 创建人
     */
    @TableField("gmt_creator")
    private String gmtCreator;

    /**
     * 最后修改时间
     */
    @TableField("gmt_modified")
    private Date gmtModified;

    /**
     * 最后修改人
     */
    @TableField("gmt_modifier")
    private String gmtModifier;

    /**
     * TableLogic：表示开启逻辑删除
     * 删除标志
     * 1表示删除
     * 0表示未删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean isDeleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtCreator() {
        return gmtCreator;
    }

    public void setGmtCreator(String gmtCreator) {
        this.gmtCreator = gmtCreator;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getGmtModifier() {
        return gmtModifier;
    }

    public void setGmtModifier(String gmtModifier) {
        this.gmtModifier = gmtModifier;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractModel<?> that = (AbstractModel<?>) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(status, that.status) &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtCreator, that.gmtCreator) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(gmtModifier, that.gmtModifier) &&
                Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, status, gmtCreate, gmtCreator, gmtModified, gmtModifier, isDeleted);
    }

    @Override
    public String toString() {
        return "AbstractModel{" +
                "id=" + id +
                ", status=" + status +
                ", gmtCreate=" + gmtCreate +
                ", gmtCreator=" + gmtCreator +
                ", gmtModified=" + gmtModified +
                ", gmtModifier=" + gmtModifier +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
