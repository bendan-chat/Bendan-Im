package com.obeast.chat.handler;

import com.obeast.chat.domain.ChatChannelGroup;
import com.obeast.chat.domain.ConnectMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


/**
 * @author wxl
 * Date 2022/12/27 14:19
 * @version 1.0
 * Description: 新建连接消息处理类
 */
@Slf4j
public class ConnectMsgHandler extends SimpleChannelInboundHandler<ConnectMsg> {

    private final ChatChannelGroup chatChannelGroup;

    public ConnectMsgHandler(ChatChannelGroup chatChannelGroup) {
        this.chatChannelGroup = chatChannelGroup;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ConnectMsg msg) throws Exception {
        if (msg.getFromUuid() == null) {
            ctx.close();
            log.error("-------------->uuid为空请检查");
            return;
        }
        if (!ctx.channel().isOpen()){
            log.info("-------------->用户通道不存在请检查");
            throw new Exception("用户通道不存在请检查");
        }
        log.info("用户{}建立新的连接------>{}" , msg.getFromUuid(), ctx.channel());
        //记录当前连接的用户id以及线程对象
        chatChannelGroup.addChannel(msg.getFromUuid(), ctx.channel());
        log.info("-------------->用户{}保存到线程组了" , msg.getFromUuid());
    }
}
