package com.obeast.chat.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;

import java.util.List;

/**
 * @author wxl
 * Date 2022/12/27 12:37
 * @version 1.0
 * Description:
 */
public interface ChatRecordService extends IService<ChatRecordEntity> {

    /**
     * Description: 分页查询 所有聊天记录
     *
     * @param pageParams parameters
     * @param userId     userId
     * @param toId       toId
     * @return com.obeast.core.domain.PageObjects<com.worldintek.chat.entity.ChatRecordEntity>
     * @author wxl
     * Date: 2022/12/27 12:36
     */
    PageObjects<ChatRecordEntity> queryPage(PageParams pageParams, Long userId, Long toId);


    /**
     * Description: 查询聊天列表
     *
     * @param userId userId
     * @return Object
     * @author wxl
     * Date: 2023/1/13 23:21
     */
    List<Long> chatList(Long userId);

    /**
     * Description: 删除好友聊天记录
     *
     * @param curUserId curUserId
     * @param addUserId addUserId
     * @return java.lang.Boolean
     * @author wxl
     * Date: 2023/2/3 9:34
     */
    Boolean delChatRecord(Long curUserId, Long addUserId);

    /**
     * Description: 新增消息
     *
     * @param chatRecordEntity chatRecordEntity
     * @return java.lang.Boolean
     * @author wxl
     * Date: 2023/2/11 17:08
     */
    Boolean addMsg(ChatRecordEntity chatRecordEntity);

    /**
     * Description: 获取用户未读消息列表
     * @author wxl
     * Date: 2023/2/11 19:18
     * @param curId curId
     * @return java.util.List<com.obeast.business.entity.ChatRecordEntity>
     */
    List<ChatRecordEntity> getUnreadChatList(Long curId);

    /**
     * Description: 获取用户 与 指定 用户  未读消息列表
     * @author wxl
     * Date: 2023/2/11 19:18
     * @param curId curId
     * @return java.util.List<com.obeast.business.entity.ChatRecordEntity>
     */
    List<ChatRecordEntity> getUnreadChatList(Long curId, Long fromId);

    /**
     * Description: 清除未读消息状态
     * @author wxl
     * Date: 2023/2/11 19:18
     * @param curId curId
     * @param fromId fromId
     * @return java.util.List<com.obeast.business.entity.ChatRecordEntity>
     */
    CommonResult<?> clearUnreadChatMsg(Long curId, Long fromId);
}
