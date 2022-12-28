package com.obeast.chat.controller;

import com.obeast.chat.entity.ChatRecordEntity;
import com.obeast.chat.service.ChatRecordService;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * @author wxl
 * Date 2022/12/27 12:37
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("/chatRecord")
@Tag(name = "聊天记录接口")
@RequiredArgsConstructor
public class ChatRecordController {

    private final ChatRecordService chatRecordService;

    /**
     * 查询聊天记录
     */
    @Operation(summary = "查询聊天记录")
    @GetMapping("/listRecord")
    public PageObjects<ChatRecordEntity> page(PageParams pageParams, Long userId ) {
        return chatRecordService.queryPage(pageParams, userId);
    }

}
