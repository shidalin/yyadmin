package com.yonyou.yyadmin.common.utils;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * requst流对象工具类
 *
 * @author
 */
public class RequestStreamUtil {

    /**
     * 将request流中的数据转换成String
     *
     * @param
     * @return
     */
    public static String getString() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String valueStr = "";
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            valueStr = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            valueStr = "";
        }
        return valueStr;
    }

    /**
     * 将request流中的数据转换成JSON对象
     *
     * @param
     * @return
     */
    public static JSONObject getJson() {
        String valueStr = getString();
        return JSONObject.parseObject(valueStr);
    }


}
