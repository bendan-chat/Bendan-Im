package com.obeast.admin.business.service.impl;

import com.obeast.admin.business.dao.AddFriendRelDao;
import com.obeast.admin.business.service.ChatFriendService;
import com.obeast.admin.business.service.remote.ChatListService;
import com.obeast.business.entity.AddFriendRelEntity;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.business.entity.SysUserEntity;
import com.obeast.business.vo.ChatUserVo;
import com.obeast.business.vo.FriendVo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;
import com.obeast.security.business.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author wxl
 * Date 2023/1/13 9:12
 * @version 1.0
 * Description:
 */
@Service
@RequiredArgsConstructor
public class ChatFriendServiceImpl implements ChatFriendService {

    private final SysUserService sysUserService;

    private final AddFriendRelDao addFriendRelDao;

    private final ChatListService chatListService;


    @Override
    public List<ChatUserVo> getFriendList(String username) {
        Long userId = sysUserService.getIdByUsername(username);
        List<AddFriendRelEntity> friendRelsVos = addFriendRelDao.getFriends(userId);
        Stream<Long> userAs = friendRelsVos.stream().map(AddFriendRelEntity::getCurUserId);
        Stream<Long> userBs = friendRelsVos.stream().map(AddFriendRelEntity::getAddUserId);
        List<Long> friendIds = Stream.concat(userBs, userAs).toList();
        return getChatUserByChatList(friendIds, userId, Boolean.FALSE);
    }

    @Override
    public List<ChatUserVo> getChatUserByChatList(List<Long> friendIds, Long userId, Boolean isChatList) {
        return friendIds.stream().filter(item -> !item.equals(userId)).map(item -> {
            ChatUserVo chatUserVo = new ChatUserVo();
            SysUserEntity sysUser = sysUserService.getById(item);
            BeanUtils.copyProperties(sysUser, chatUserVo);
            if (isChatList) {
                String lastMsg = getLastMsg(userId, item);
                chatUserVo.setLastMsg(lastMsg);
            }
            return chatUserVo;
        }).toList();
    }

    @Override
    public FriendVo getFriendInfo(Long userId) {
        Assert.notNull(userId, "userId cannot be null");
        SysUserEntity userEntity = sysUserService.getById(userId);
        FriendVo friendVo = new FriendVo();
        BeanUtils.copyProperties(userEntity, friendVo);
        return friendVo;
    }


    @Override
    public List<ChatUserVo> getChatList(String username) {
        Long userId = sysUserService.getIdByUsername(username);
        List<Long> friendIds = chatListService.chatList(userId);
        return getChatUserByChatList(friendIds, userId, Boolean.TRUE);
    }

    /**
     * Description: 获取最后一条消息
     *
     * @param userId userId
     * @param toId   toId
     * @return String
     * @author wxl
     * Date: 2023/1/14 17:09
     */
    private String getLastMsg(Long userId, Long toId) {
        PageParams pageParams = new PageParams();
        pageParams.setCur(1L);
        pageParams.setLimit(1L);
        pageParams.setOrder(Boolean.FALSE);
        pageParams.setOrderField("send_time");
        CommonResult<PageObjects<ChatRecordEntity>> pageRecord = chatListService.pageRecord(pageParams, userId, toId);
        if (pageRecord.getSuccess()) {
            PageObjects<ChatRecordEntity> data = pageRecord.getData();
            List<ChatRecordEntity> items = data.getItems();
            return items.get(0).getSendContent();
        }
        return null;
    }
}
