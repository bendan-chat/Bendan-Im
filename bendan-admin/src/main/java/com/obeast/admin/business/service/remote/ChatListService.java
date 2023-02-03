package com.obeast.admin.business.service.remote;

import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.core.base.CommonResult;
import com.obeast.core.config.fegin.FeignConfig;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wxl
 * Date 2023/1/14 0:22
 * @version 1.0
 * Description: chat 远程调用
 */
@FeignClient(name = "bendan-chat", configuration = FeignConfig.class)
public interface ChatListService {

    /**
     * Description: 查询聊天好友id集合
     * @author wxl
     * Date: 2023/1/14 17:02
     * @param userId userId
     * @return java.util.List<java.lang.Long>
     */
    @GetMapping("/record/listChat")
    List<Long> chatList(@RequestParam("userId") Long userId);



    /**
     * Description: 查询聊天记录
     * @author wxl
     * Date: 2023/1/14 17:03
     * @param pageParams pageParams
     * @param userId userId
     * @param toId  toId
     * @return com.obeast.core.base.CommonResult<com.obeast.core.domain.PageObjects<com.obeast.business.entity.ChatRecordEntity>>
     */
    @GetMapping("/record/listRecord")
    CommonResult<PageObjects<ChatRecordEntity>> pageRecord(@SpringQueryMap PageParams pageParams, @RequestParam("userId") Long userId, @RequestParam("toId") Long toId);



    /**
     * Description: 删除好友聊天记录
     * @author wxl
     * Date: 2023/2/3 9:37
     * @param curUserId curUserId
     * @param addUserId addUserId
     * @return java.lang.Boolean
     */
    @DeleteMapping("/record/delChatRecord")
    Boolean delChatRecord(@RequestParam("curUserId") Long curUserId, @RequestParam("addUserId") Long addUserId);
}
