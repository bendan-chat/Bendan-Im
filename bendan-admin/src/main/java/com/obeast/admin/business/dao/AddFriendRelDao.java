package com.obeast.admin.business.dao;

import com.obeast.core.base.BaseDao;
import com.obeast.business.entity.AddFriendRelEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author wxl
 * Date 2023/2/2 9:38
 * @version 1.0
 * Description: 针对表【add_friend_rel(新增好友 关系列表)】的数据库操作Mapper
 */
public interface AddFriendRelDao extends BaseDao<AddFriendRelEntity> {
    /**
     * Description: 获取好友id
     * @author wxl
     * Date: 2023/1/22 16:58
     * @param userId user id
     * @return java.util.List<com.obeast.business.vo.FriendRelsVo>
     */
    @Select("select * from add_friend_rel where (cur_user_id = #{userId} or add_user_id = #{userId}) and status = 2")
    List<AddFriendRelEntity> getFriends(@Param("userId") Long userId);

//    /**
//     * Description: 删除好友
//     * @author wxl
//     * Date: 2023/1/22 17:13
//     * @param userId user id
//     * @param delUserId user id
//     * @return boolean
//     */
//    @Delete("delete from friend_rel where (user_a = #{userId} and user_b = #{delUserId}) or (user_a = #{delUserId} and user_b = #{userId})")
//    boolean delFriend (@Param("userId") Long userId, @Param("delUserId") Long delUserId);
}




