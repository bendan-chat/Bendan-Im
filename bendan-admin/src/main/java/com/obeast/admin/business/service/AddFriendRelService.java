package com.obeast.admin.business.service;

import com.obeast.business.entity.AddFriendRelEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wxl
 * Date 2023/2/2 9:38
 * @version 1.0
 * Description: 针对表【add_friend_rel(新增好友 关系列表)】的数据库操作Service
 */
public interface AddFriendRelService extends IService<AddFriendRelEntity> {

    /**
     * Description: 根据用户id 查询【新的好友】 列表
     * @author wxl
     * Date: 2023/2/2 13:43
     * @param userId userId
     * @return java.util.List<com.obeast.business.entity.AddFriendRel>
     */
    List<AddFriendRelEntity> queryNewFriends(Long userId);

    /**
     * Description: 新增好友
     * @author wxl
     * Date: 2023/2/2 16:22
     * @param addFriendRelEntity addFriendRel
     * @return java.lang.Boolean
     */
    Boolean addNewFriend(AddFriendRelEntity addFriendRelEntity);


    /**
     * Description: 删除好友
     * @author wxl
     * Date: 2023/2/3 9:25
     * @param curUserId curUserId
     * @param addUserId addUserId
     * @return java.lang.Boolean
     */
    Boolean delFriend(Long curUserId, Long addUserId);

    /**
     * Description: 同意新增好友
     * @author wxl
     * Date: 2023/2/2 17:20
     * @param addFriendRelId addFriendRelId
     */
    void agreeAddNewFriend(Long addFriendRelId);

    /**
     * Description: 删除好友 和聊天记录
     * @author wxl
     * Date: 2023/2/3 9:25
     * @param curUserId curUserId
     * @param addUserId addUserId
     * @return java.lang.Boolean
     */
    Boolean delFriendAndChatRecord(Long curUserId, Long addUserId);
}
