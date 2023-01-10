package com.obeast.chat.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.service.ChatRecordService;
import com.obeast.chat.business.dao.ChatRecordDao;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;
import com.obeast.core.utils.PageQueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * @author wxl
 * Date 2022/12/27 13:19
 * @version 1.0
 * Description: 聊天记录模拟表
 */
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordDao, ChatRecordEntity> implements ChatRecordService {

    @Override
    public PageObjects<ChatRecordEntity> queryPage(PageParams pageParams, Long userId, Long toId) {
        Assert.notNull(userId, "userId cannot be null");
        LambdaQueryWrapper<ChatRecordEntity> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(ChatRecordEntity::getFromId, userId)
                            .and(j -> j.eq(ChatRecordEntity::getToId, toId))
                .or(i -> {
                    i .eq(ChatRecordEntity::getFromId, toId)
                            .and(j -> j.eq(ChatRecordEntity::getToId, userId));
                })
        ;
        IPage<ChatRecordEntity> page = this.page(
                new PageQueryUtils<ChatRecordEntity>().getPage(pageParams),
                wrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, ChatRecordEntity.class);
    }
}
