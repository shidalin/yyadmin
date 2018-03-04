package com.yonyou.yyadmin.common.utils;

/**
 * 常量
 * @author shidalin
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final String SUPER_ADMIN = "1";

    /**
     * 密码加密盐
     */
    public static final String PASSWORD_SALT = "yonyou";

    /**
     * token过期时间
     * 12小时后过期
     */
    public final static Long EXPIRES = 3600L * 12;

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(1),
        /**
         * 菜单
         */
        MENU(2),
        /**
         * 按钮
         */
        BUTTON(3);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum BusiStatus {
        /**
         * 正常
         */
        NORMAL(1),
        /**
         * 暂停
         */
        PAUSE(0);

        private int value;

        private BusiStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
