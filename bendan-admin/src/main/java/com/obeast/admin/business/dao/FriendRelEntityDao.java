package com.obeast.admin.business.dao;

import com.obeast.business.entity.FriendRelEntity;
import com.obeast.core.base.BaseDao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wxl
 * Date 2023/1/22 16:57
 * @version 1.0
 * Description: dao方法
 */
public interface FriendRelEntityDao extends BaseDao<FriendRelEntity> {

    /**
     * Description: 获取好友id
     * @author wxl
     * Date: 2023/1/22 16:58
     * @param userId user id
     * @return java.util.List<com.obeast.business.vo.FriendRelsVo>
     */
    @Select("select user_a, user_b from friend_rel where user_a = #{userId} or user_b = #{userId}")
    List<FriendRelEntity> getFriends(@Param("userId") Long userId);


    /**
     * Description: 新增好友
     * @author wxl
     * Date: 2023/1/22 17:11
     * @param userA user id
     * @param userB user id
     * @return boolean
     */
    @Insert("insert into friend_rel (user_a, user_b) value (#{userA}, #{userB})")
    boolean addFriend (@Param("userA") Long userA, @Param("userB") Long userB);


    /**
     * Description: 删除好友
     * @author wxl
     * Date: 2023/1/22 17:13
     * @param userId user id
     * @param delUserId user id
     * @return boolean
     */
    @Delete("delete from friend_rel where (user_a = #{userId} and user_b = #{delUserId}) or (user_a = #{delUserId} and user_b = #{userId})")
    boolean delFriend (@Param("userId") Long userId, @Param("delUserId") Long delUserId);
}
