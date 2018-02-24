package com.yonyou.yyadmin.system.service;

import com.yonyou.yyadmin.core.AbstractService;
import com.yonyou.yyadmin.system.entity.Menu;
import com.yonyou.yyadmin.system.entity.MenuDTO;
import org.apache.shiro.authc.AuthenticationException;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */
public interface MenuService extends AbstractService<Menu> {
    List<MenuDTO> getMenuListByUserId(String userId) throws AuthenticationException;
}
