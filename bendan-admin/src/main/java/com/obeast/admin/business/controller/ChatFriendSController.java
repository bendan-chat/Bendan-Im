package com.obeast.admin.business.controller;

import com.obeast.admin.business.service.ChatFriendService;
import com.obeast.business.vo.ChatUserVo;
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
public class ChatFriendSController {

    private final ChatFriendService chatFriendService;

    @Operation(summary = "查询好友列表")
    @GetMapping("/getFriends")
    public List<ChatUserVo> getFriends(@RequestParam("username") String username) {
        return chatFriendService.getFriendInfos(username);
    }

    @Operation(summary = "查询聊天列表")
    @GetMapping("/getChatList")
    public List<ChatUserVo> getChatList(@RequestParam("username") String username) {
        return chatFriendService.getChatList(username);
    }

    @Operation(summary = "新增好友")
    @PostMapping("/addFriend")
    public Boolean addFriend(@RequestParam("friendId") Long friendId,@RequestParam("userId") Long userId) {
        return chatFriendService.addFriend(friendId, userId);
    }


    @Operation(summary = "删除好友")
    @PostMapping("/delFriend")
    public Boolean delFriend(@RequestParam("friendId") Long friendId,@RequestParam("userId") Long userId) {
        return chatFriendService.delFriend(friendId, userId);
    }
}
