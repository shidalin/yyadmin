package com.yonyou.yyadmin.base;

/**
 * Created by shidalin on 2017/9/22.
 * 业务状态枚举类
 **/
public enum StatusCode {
    NORMAL(0),//正常状态
    SUSPENDED(1),//暂停状态
    COMMITTED(3),//提交状态
    APPROVED(8)//审批状态
    ;

    /**
     * 状态码
     */
    public int code;

    StatusCode(Integer code) {
        this.code = code;
    }
}
