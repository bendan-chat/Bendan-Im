package com.obeast.chat.handler;

import com.obeast.chat.business.domain.CodeStrategyContext;
import com.obeast.chat.business.domain.HeardMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


/**
 * @author wxl
 * Date 2022/12/27 12:41
 * @version 1.0
 * Description: 心跳消息处理类
 */
@Slf4j
public class HeardMsgHandler extends SimpleChannelInboundHandler<HeardMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeardMsg msg) throws Exception {
        log.debug("-------------------->收到客户端的心跳:" + msg.getCode());
        //响应心跳
        ctx.writeAndFlush(new TextWebSocketFrame(CodeStrategyContext.HEARD.toString()));
        log.debug("-------------------->响应心跳");
    }
}
