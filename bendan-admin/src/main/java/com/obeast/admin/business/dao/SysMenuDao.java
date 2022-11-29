package com.obeast.admin.business.dao;

import com.obeast.core.base.BaseDao;
import com.obeast.admin.business.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author obeast-dragon
 * Date 2022-10-12 20:10:00
 * @version 1.0
 * Description: 菜单表
 */
@Mapper
public interface SysMenuDao extends BaseDao<SysMenuEntity> {

}
