package com.obeast.security.business.dao;


import com.obeast.core.base.BaseDao;
import com.obeast.business.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wxl
 * Date 2022/11/30 9:20
 * @version 1.0
 * Description: 针对表【bendan_sys_user】的数据库操作Mapper
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {
}




