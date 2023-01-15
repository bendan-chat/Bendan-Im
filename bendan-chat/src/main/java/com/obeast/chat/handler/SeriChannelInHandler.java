package com.obeast.chat.handler;


import cn.hutool.json.JSONUtil;
import com.obeast.chat.business.domain.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wxl
 * Date 2022/12/27 12:45
 * @version 1.0
 * Description: 拆分WebSocket的帧
 */
@Slf4j
public class SeriChannelInHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Transactional
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();

        //json字符串转为bean
        BaseMsg baseMsg = JSONUtil.toBean(text, BaseMsg.class);
        //新连接
        if (baseMsg.getCode().equals(CodeStrategyContext.NEW_CONNECTION)){
            log.debug("新连接handle------------->NEW_CONNECTION");
            baseMsg = JSONUtil.toBean(text, ConnectMsg.class);
        }
        //心跳
        else if (baseMsg.getCode().equals(CodeStrategyContext.HEARD)){
            log.debug("心跳handle---------------->HEARD");
            baseMsg = JSONUtil.toBean(text, HeardMsg.class);
        }
        //消息
        else if (baseMsg.getCode().equals(CodeStrategyContext.SEND_MSG)) {
            log.debug("文本handle---------------->SEND_MESSAGE");
            baseMsg = JSONUtil.toBean(text, ChatStrMsg.class);
        }
        log.debug("------------------->往下传递");
        ctx.fireChannelRead(baseMsg);

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("--------------------->新客户端连接√");
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("------------------------------>客户端开始关闭连接");
        ctx.close();
        log.debug("------------------------------>客户端关闭连接成功");

    }


}
