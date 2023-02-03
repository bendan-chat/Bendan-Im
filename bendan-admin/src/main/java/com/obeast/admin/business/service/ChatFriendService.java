package com.obeast.admin.business.service;

import com.obeast.business.vo.ChatUserVo;
import com.obeast.business.vo.FriendVo;

import java.util.List;

/**
 * @author wxl
 * Date 2023/1/13 9:12
 * @version 1.0
 * Description:
 */
public interface ChatFriendService {

    /**
     * Description: 获取好友详情
     * @author wxl
     * Date: 2022/12/27 23:48
     * @param username username
     * @return java.util.List<com.obeast.business.entity.SysUserEntity>
     */
    List<ChatUserVo> getFriendList(String username);


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

    /**
     * Description: 查询好友详情
     * @author wxl
     * Date: 2023/2/1 10:05
     * @param userId userId
     * @return com.obeast.business.vo.ChatUserVo
     */
    FriendVo getFriendInfo(Long userId);
}
