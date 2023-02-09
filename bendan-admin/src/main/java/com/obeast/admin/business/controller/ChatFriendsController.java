package com.obeast.admin.business.controller;

import com.obeast.admin.business.service.AddFriendRelService;
import com.obeast.admin.business.service.ChatFriendService;
import com.obeast.business.entity.AddFriendRelEntity;
import com.obeast.business.vo.ChatUserVo;
import com.obeast.business.vo.FriendVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wxl
 * Date 2022/12/27 23:09
 * @version 1.0
 * Description:
 */
@Tag(name = "聊天接口")
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatFriendsController {

    private final ChatFriendService chatFriendService;

    private final AddFriendRelService addFriendRelService;

    @Operation(summary = "查询好友列表")
    @GetMapping("/getFriends")
    public List<ChatUserVo> getFriends(@RequestParam("username") String username) {
        return chatFriendService.getFriendList(username);
    }

    @Operation(summary = "查询聊天列表")
    @GetMapping("/getChatList")
    public List<ChatUserVo> getChatList(@RequestParam("userId") Long userId) {
        return chatFriendService.getChatList(userId);
    }

    @Operation(summary = "查询新的朋友列表")
    @GetMapping("/getNewFriends")
    public List<AddFriendRelEntity> getNewFriends(@RequestParam("userId") Long userId) {
        return addFriendRelService.queryNewFriends(userId);
    }

    @Operation(summary = "查询好友详情")
    @GetMapping("/getFriend")
    public FriendVo getFriend(@RequestParam("userId") Long userId) {
        return chatFriendService.getFriendInfo(userId);
    }

    @Operation(summary = "单方面 新增好友")
    @PostMapping("/addFriend")
    public Boolean addNewFriend(@RequestBody AddFriendRelEntity addFriendRelEntity) {
        return addFriendRelService.addNewFriend(addFriendRelEntity);
    }

    @Operation(summary = "同意 新增好友")
    @PostMapping("/agreeAddFriend")
    public void agreeAddNewFriend(Long addFriendRelId) {
        addFriendRelService.agreeAddNewFriend(addFriendRelId);
    }


    @Operation(summary = "删除好友和聊天记录")
    @DeleteMapping("/delFriendAndChatRecord")
    public Boolean delFriendAndChatRecord(@RequestParam("curUserId") Long curUserId, @RequestParam("addUserId") Long addUserId) {
        return addFriendRelService.delFriendAndChatRecord(curUserId, addUserId);
    }
}
