package com.obeast.admin.business.service;

import com.obeast.business.vo.ChatUserVo;

import java.util.List;

/**
 * @author wxl
 * Date 2023/1/13 9:12
 * @version 1.0
 * Description:
 */
public interface ChatFriendService {

    /**
     * Description: 新增好友
     * @author wxl
     * Date: 2023/1/13 9:11
     * @param friendId friendId
     * @param userId userId
     * @return java.lang.Boolean
     */
    Boolean addFriend(Long friendId, Long userId);

    /**
     * Description: 删除好友
     * @author wxl
     * Date: 2023/1/13 9:10
     * @param friendId friendId
     * @param userId userId
     * @return java.lang.Boolean
     */
    Boolean delFriend(Long friendId, Long userId);

    /**
     * Description: 获取好友详情
     * @author wxl
     * Date: 2022/12/27 23:48
     * @param username username
     * @return java.util.List<com.obeast.business.entity.SysUserEntity>
     */
    List<ChatUserVo> getFriendInfos(String username);


    /**
     * Description: 根据好友ids 获取好友详情
     * @author wxl
     * Date: 2023/1/14 0:24
     * @param friendIds friendIds
     * @param userId  userId
     * @return java.util.List<com.obeast.business.vo.ChatUserVo>
     */
    List<ChatUserVo> getChatUserByChatList(List<Long> friendIds, Long userId, Boolean isChatList);

    /**
     * Description: 查询聊天列表详情
     * @author wxl
     * Date: 2023/1/14 14:47
     * @param username  username
     * @return java.util.List<com.obeast.business.vo.ChatUserVo>
     */
    List<ChatUserVo> getChatList(String username);
}
