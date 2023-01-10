package com.obeast.chat.handler;

import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.business.domain.ShutDownMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxl
 * Date 2022/12/27 12:45
 * @version 1.0
 * Description: 关闭处理类
 */
@Slf4j
public class ShutDownMsgHandler extends SimpleChannelInboundHandler<ShutDownMsg> {

    private final ChatChannelGroup chatChannelGroup;

    public ShutDownMsgHandler(ChatChannelGroup chatChannelGroup) {
        this.chatChannelGroup = chatChannelGroup;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ShutDownMsg msg) throws Exception {
        log.debug("------------------------------>客户端开始关闭连接");
        chatChannelGroup.removeChannel(msg.getFromId());
//        ctx.close();
        log.debug("------------------------------>客户端关闭连接成功");
    }
}
