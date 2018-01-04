package com.yonyou.iviewadmin.common.utils;

/**
 * Created by shidl on 2017/7/12.
 */
public class SqlUtil {

    /**
     * 解决批量删除where条件拼接问题
     *
     * @param params
     * @return
     */
    public static String getInSql(String[] params) {
        StringBuffer stringBuffer = new StringBuffer();
        if (params != null && params.length > 0) {
            for (String param :
                    params) {
                stringBuffer.append("\'");
                stringBuffer.append(param);
                stringBuffer.append("\'");
                if (!param.equals(params[(params.length - 1)])) {
                    stringBuffer.append(",");
                }

            }
        }
        return stringBuffer.toString();
    }
}
