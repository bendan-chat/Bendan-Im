package com.obeast.chat.handler;

import cn.hutool.core.util.ObjectUtil;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.business.domain.CodeStrategyContext;
import com.obeast.chat.business.domain.HeardMsg;
import com.obeast.chat.business.domain.NewFriendMsg;
import com.obeast.chat.business.service.ChatRecordService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;


/**
 * @author wxl
 * Date 2022/12/27 12:41
 * @version 1.0
 * Description: 心跳消息处理类
 */
@RequiredArgsConstructor
@Slf4j
public class NewFriendMsgHandler extends SimpleChannelInboundHandler<NewFriendMsg> {

    private final ChatRecordService chatRecordService;

    private final ChatChannelGroup chatChannelGroup;

    private final RabbitTemplate rabbitTemplate;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NewFriendMsg msg) throws Exception {
        log.debug("-------------------->收到客户端的新好友:" + msg.getCode());
        //响应心跳
        ctx.writeAndFlush(new TextWebSocketFrame(CodeStrategyContext.NEW_FRIEND.toString()));
        log.debug("-------------------->响应新好友");


        Long fromId = msg.getFromId();
        Long toId = msg.getToId();
        //      同步消息入DB库 方便展示
        ChatRecordEntity chatRecordEntity = new ChatRecordEntity();
        chatRecordEntity.setFromId(fromId);
        chatRecordEntity.setToId(toId);
        chatRecordEntity.setSendContent("我通过了你的朋友验证请求，现在我们可以开始聊天了");
        chatRecordEntity.setSendType(msg.getSendType());
        chatRecordEntity.setSendTime(new Date());
        chatRecordService.save(chatRecordEntity);

        //查询toId是否存在
        Channel channel = chatChannelGroup.getChannel(toId);
        log.debug("-------------------------------->消息已经入DB库");
        // TODO: 2023/1/15  对方在线直接发送；不在线丢到mq做微标
        try {
            if (channel.isOpen()) {
                //对方在线，才做rabbitMq转发
                log.debug("channel is open is ok");
                log.debug("对方在线通过RabbitMq发送");
                //通过rabbitmq转发出去
                rabbitTemplate.convertAndSend("ws_exchange", "", chatRecordEntity);
            }
        } catch (Exception e) {
            log.debug("对方不在线----------------->do nothing");
        }
    }
}
