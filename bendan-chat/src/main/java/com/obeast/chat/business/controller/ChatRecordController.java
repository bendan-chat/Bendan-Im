package com.obeast.chat.business.controller;

import cn.hutool.json.JSONObject;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.service.ChatRecordService;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wxl
 * Date 2022/12/27 12:37
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("/record")
@Tag(name = "聊天记录接口")
@RequiredArgsConstructor
public class ChatRecordController {

    private final ChatRecordService chatRecordService;

    /**
     * 查询聊天记录
     */
    @Operation(summary = "查询聊天记录")
    @GetMapping("/listRecord")
    public CommonResult<PageObjects<ChatRecordEntity>> pageRecord(PageParams pageParams, @RequestParam("userId") Long userId, @RequestParam("toId") Long toId) {
        PageObjects<ChatRecordEntity> page = chatRecordService.queryPage(pageParams, userId, toId);
        return CommonResult.success(page);
    }

    /**
     * 查询未读聊天记录
     */
    @Operation(summary = "查询未读聊天记录")
    @GetMapping("/getUnreadChatList")
    public CommonResult<?> getUnreadChatList(@RequestParam("curId") Long curId) {
        List<ChatRecordEntity> recordEntities = chatRecordService.getUnreadChatList(curId);
        return CommonResult.success(recordEntities);
    }

    /**
     * 清除未读消息状态
     */
    @Operation(summary = "清除未读消息状态")
    @PostMapping("/clearUnreadChatMsg")
    public CommonResult<?> clearUnreadChatMsg(@RequestBody JSONObject jsonObject) {
        Long curId = jsonObject.getLong("curId");
        Long fromId = jsonObject.getLong("fromId");
        return chatRecordService.clearUnreadChatMsg(curId, fromId);
    }


    /**
     * 查询聊天好友id集合
     */
    @Operation(summary = "查询聊天好友id集合")
    @GetMapping("/listChat")
    public List<Long> chatList(@RequestParam("userId") Long userId) {
        return chatRecordService.chatList(userId);
    }


    @Operation(summary = "删除好友聊天记录")
    @DeleteMapping("/delChatRecord")
    public Boolean delChatRecord(@RequestParam("curUserId") Long curUserId, @RequestParam("addUserId") Long addUserId) {
        return chatRecordService.delChatRecord(curUserId, addUserId);
    }
}
