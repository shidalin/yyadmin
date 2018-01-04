package com.yonyou.iviewadmin.common.validator;

import com.yonyou.iviewadmin.core.ServiceException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new ServiceException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }
}
