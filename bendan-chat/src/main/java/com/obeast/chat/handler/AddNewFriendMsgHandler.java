package com.obeast.chat.handler;

import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.domain.msg.AddNewFriendMsg;
import com.obeast.chat.business.domain.msg.AgreeNewFriendMsg;
import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.business.domain.CodeStrategyContext;
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
public class AddNewFriendMsgHandler extends SimpleChannelInboundHandler<AddNewFriendMsg> {
    private final ChatChannelGroup chatChannelGroup;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddNewFriendMsg msg) throws Exception {
        log.debug("--------> 新增好友");
        Long toId = msg.getToId();
        io.netty.channel.Channel channel = chatChannelGroup.getChannel(toId);
        if (channel != null) {
            //      同步消息入DB库 方便展示
            TextWebSocketFrame resp = new TextWebSocketFrame(CodeStrategyContext.ADD_NEW_FRIEND.toString());
            channel.writeAndFlush(resp);
            log.debug("-------------------->响应新好友");
        }
    }
}
