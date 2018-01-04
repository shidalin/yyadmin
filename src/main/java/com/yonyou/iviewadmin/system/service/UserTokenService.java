package com.yonyou.iviewadmin.system.service;

import com.yonyou.iviewadmin.core.AbstractService;
import com.yonyou.iviewadmin.system.entity.UserToken;

/**
 * <p>
 * 用户TOKEN 服务类
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */
public interface UserTokenService extends AbstractService<UserToken> {
    UserToken createToken(String id);
}
