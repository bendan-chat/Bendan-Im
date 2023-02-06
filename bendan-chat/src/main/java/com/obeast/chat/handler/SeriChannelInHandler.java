package com.obeast.chat.handler;


import cn.hutool.json.JSONUtil;
import com.obeast.chat.business.domain.*;
import com.obeast.chat.business.domain.msg.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wxl
 * Date 2022/12/27 12:45
 * @version 1.0
 * Description: 拆分WebSocket的帧
 */
@RequiredArgsConstructor
@Slf4j
public class SeriChannelInHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private Long userId;

    private final  ChatChannelGroup chatChannelGroup;
    @Transactional
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        log.debug("text: " + text);
        //json字符串转为bean
        BaseMsg baseMsg = JSONUtil.toBean(text, BaseMsg.class);
        //新连接
        if (baseMsg.getCode().equals(CodeStrategyContext.NEW_CONNECTION)){
            log.debug("新连接handle------------->NEW_CONNECTION");
            baseMsg = JSONUtil.toBean(text, ConnectMsg.class);
            userId = baseMsg.getFromId();
        }
        //心跳
        else if (baseMsg.getCode().equals(CodeStrategyContext.HEARD)){
            baseMsg = JSONUtil.toBean(text, HeardMsg.class);
        }
        // 新好友
        else if (baseMsg.getCode().equals(CodeStrategyContext.ADD_NEW_FRIEND)) {
            log.debug("新好友---------------->ADD_NEW_FRIEND");
            baseMsg = JSONUtil.toBean(text, AddNewFriendMsg.class);
        }
        // 新好友
        else if (baseMsg.getCode().equals(CodeStrategyContext.AGREE_NEW_FRIEND)) {
            log.debug("新好友---------------->NEW_FRIEND");
            baseMsg = JSONUtil.toBean(text, AgreeNewFriendMsg.class);
        }
        //消息
        else if (baseMsg.getCode().equals(CodeStrategyContext.SEND_MSG)) {
            baseMsg = JSONUtil.toBean(text, ChatStrMsg.class);
        }
        ctx.fireChannelRead(baseMsg);

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("--------------------->新客户端连接√");
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("------------------------------>客户端开始关闭连接");
        chatChannelGroup.removeChannel(userId);
        ctx.close();
        log.debug("------------------------------>客户端{}  关闭连接成功", userId);

    }


}
