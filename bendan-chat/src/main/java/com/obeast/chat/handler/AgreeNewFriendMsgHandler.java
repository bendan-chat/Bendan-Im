package com.obeast.chat.handler;

import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.business.domain.CodeStrategyContext;
import com.obeast.chat.business.domain.msg.AgreeNewFriendMsg;
import com.obeast.chat.business.service.ChatRecordService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;


/**
 * @author wxl
 * Date 2022/12/27 12:41
 * @version 1.0
 * Description: 心跳消息处理类
 */
@RequiredArgsConstructor
@Slf4j
public class AgreeNewFriendMsgHandler extends SimpleChannelInboundHandler<AgreeNewFriendMsg> {

    private final ChatRecordService chatRecordService;

    private final ChatChannelGroup chatChannelGroup;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AgreeNewFriendMsg msg) throws Exception {
        Long fromId = msg.getFromId();
        Long toId = msg.getToId();
        io.netty.channel.Channel channel = chatChannelGroup.getChannel(toId);
        ChatRecordEntity chatRecordEntity = new ChatRecordEntity();
        chatRecordEntity.setFromId(fromId);
        chatRecordEntity.setToId(toId);
        chatRecordEntity.setSendContent("我通过了你的朋友验证请求，现在我们可以开始聊天了");
        chatRecordEntity.setSendType(0);
        chatRecordEntity.setSendTime(new Date());
        chatRecordService.save(chatRecordEntity);
        log.debug("新增好友成功插入第一条聊天记录");
        if (channel != null) {
            //      同步消息入DB库 方便展示
            TextWebSocketFrame resp = new TextWebSocketFrame(CodeStrategyContext.AGREE_NEW_FRIEND.toString());
            channel.writeAndFlush(resp);
            log.debug("-------------------->响应新好友");
        }
        log.debug("--------> 同意新增好友");
    }
}
