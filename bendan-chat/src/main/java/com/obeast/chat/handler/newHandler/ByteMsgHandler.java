package com.obeast.chat.handler.newHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxl
 * Date 2023/1/11 10:13
 * @version 1.0
 * Description: 字节消息处理
 */
@Slf4j
public class ByteMsgHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {
        log.error("收到服务器byte为： " + binaryWebSocketFrame.content());
        ByteBuf content = binaryWebSocketFrame.content();
        String s = content.toString();
        log.error("收到服务器消息为：" + s);
    }
}
