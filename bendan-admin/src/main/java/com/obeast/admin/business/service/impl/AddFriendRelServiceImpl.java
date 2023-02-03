package com.obeast.admin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.admin.business.service.remote.ChatListService;
import com.obeast.business.entity.AddFriendRelEntity;
import com.obeast.admin.business.service.AddFriendRelService;
import com.obeast.admin.business.dao.AddFriendRelDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author wxl
 * Date 2023/2/2 9:39
 * @version 1.0
 * Description: 针对表【add_friend_rel(新增好友 关系列表)】的数据库操作Service实现
 */
@RequiredArgsConstructor
@Service
public class AddFriendRelServiceImpl extends ServiceImpl<AddFriendRelDao, AddFriendRelEntity>
        implements AddFriendRelService {

    private final ChatListService chatListService;

    @Override
    public Boolean delFriendAndChatRecord(Long curUserId, Long addUserId) {
//        删除关系
        Boolean delFriend = this.delFriend(curUserId, addUserId);
//        删除聊天记录 异步
        Boolean delChatRecord = chatListService.delChatRecord(curUserId, addUserId);
        return delFriend && delChatRecord;
    }

    @Override
    public Boolean delFriend(Long curUserId, Long addUserId) {
        LambdaQueryWrapper<AddFriendRelEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AddFriendRelEntity::getCurUserId, curUserId).and(i -> {
            i.eq(AddFriendRelEntity::getAddUserId, addUserId);
        }).or().eq(AddFriendRelEntity::getCurUserId, addUserId).and(i -> {
            i.eq(AddFriendRelEntity::getAddUserId, curUserId);
        });
//        删除关系
        return this.remove(wrapper);
    }

    @Override
    public void agreeAddNewFriend(Long addFriendRelId) {
        Assert.notNull(addFriendRelId, "addFriendRelId cannot be null");
        LambdaUpdateWrapper<AddFriendRelEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(AddFriendRelEntity::getStatus, 2);
        wrapper.eq(AddFriendRelEntity::getId, addFriendRelId);
        this.baseMapper.update(null, wrapper);
    }

    @Override
    public Boolean addNewFriend(AddFriendRelEntity addFriendRelEntity) {
        Assert.notNull(addFriendRelEntity, "AddFriendRelEntity cannot be null");
        return this.save(addFriendRelEntity);
    }

    @Override
    public List<AddFriendRelEntity> queryNewFriends(Long userId) {
        LambdaQueryWrapper<AddFriendRelEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AddFriendRelEntity::getCurUserId, userId);
        return this.list(wrapper);
    }
}




