package com.obeast.admin.business.dao;


import com.obeast.business.entity.SysUserEntity;
import com.obeast.business.vo.FriendRelsVo;
import com.obeast.core.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wxl
 * Date 2022/11/30 9:20
 * @version 1.0
 * Description: 针对表【bendan_sys_user】的数据库操作Mapper
 */
@Mapper
public interface ChatFriendSDao extends BaseDao<SysUserEntity> {

    @Select("select user_a, user_b from friend_rel where user_a = #{userId} or user_b = #{userId}")
    List<FriendRelsVo> getFriends(@Param("userId") Long userId);
}




