package com.yonyou.yyadmin.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yonyou.yyadmin.system.entity.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
  * 菜单 Mapper 接口
 * </p>
 *
 * @author shidalin
 * @since 2017-09-27
 */

@Component
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenuListByUserCode(String userCode);
}