package com.obeast.chat.handler;

import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.business.domain.msg.ConnectMsg;
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
        if (msg.getFromId() == null) {
            ctx.close();
            log.error("-------------->id为空请检查");
            return;
        }
        if (!ctx.channel().isOpen()){
            log.debug("-------------->用户通道不存在请检查");
            throw new Exception("用户通道不存在请检查");
        }
        log.debug("用户{}建立新的连接------>{}" , msg.getFromId(), ctx.channel());
        //记录当前连接的用户id以及线程对象
        chatChannelGroup.addChannel(msg.getFromId(), ctx.channel());
        log.debug("-------------->用户{}保存到线程组了" , msg.getFromId());
    }
}
