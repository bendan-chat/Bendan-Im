package com.obeast.admin.business.controller;

import com.obeast.business.vo.ChatUserVo;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;
import com.obeast.security.business.service.SysUserService;
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
public class ChatController {

    private final SysUserService sysUserService;


    @Operation(summary = "查询好友列表")
    @GetMapping("/getFriends")
    public List<ChatUserVo> getFriends(@RequestParam("username") String username) {
        return sysUserService.getFriendInfos(username);
    }

    @Operation(summary = "新增好友")
    @PostMapping("/addFriend")
    public void addFriends() {
    }


    @Operation(summary = "删除好友")
    @PostMapping("/delFriend")
    public void delFriends() {
    }
}
