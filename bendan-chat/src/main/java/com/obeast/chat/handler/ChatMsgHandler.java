package com.obeast.chat.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.business.domain.msg.ChatStrMsg;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.service.ChatRecordService;
import com.obeast.core.exception.BendanException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;

/**
 * @author wxl
 * Date 2022/12/27 12:45
 * @version 1.0
 * Description: 聊天消息处理器
 */
@Slf4j
public class ChatMsgHandler extends SimpleChannelInboundHandler<ChatStrMsg> {

    private final ChatChannelGroup chatChannelGroup;

    private final RabbitTemplate rabbitTemplate;

    private final ChatRecordService chatRecordService;


    public ChatMsgHandler(ChatChannelGroup chatChannelGroup, RabbitTemplate rabbitTemplate, ChatRecordService chatRecordService) {
        this.chatChannelGroup = chatChannelGroup;
        this.rabbitTemplate = rabbitTemplate;
        this.chatRecordService = chatRecordService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatStrMsg msg) throws Exception {

        Long fromId = msg.getFromId();
        Long toId = msg.getToId();
        String sendMsg = this.handlerMsg(msg);
        //      同步消息入DB库 方便展示
        ChatRecordEntity chatRecordEntity = new ChatRecordEntity();
        chatRecordEntity.setFromId(fromId);
        chatRecordEntity.setToId(toId);
        chatRecordEntity.setSendContent(sendMsg);
        chatRecordEntity.setSendType(msg.getSendType());
        chatRecordEntity.setSendTime(new Date());
        Long sendTimeLength = msg.getLength();
        if (ObjectUtil.isNotNull(sendTimeLength)) {
            chatRecordEntity.setLength(sendTimeLength);
        }
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

    /**
     * Description: 处理语音消息和文本消息
     * @author wxl
     * Date: 2023/1/3 15:39
     * @param msg  msg
     * @return byte[]
     */
    private String handlerMsg(ChatStrMsg msg) {
        String sendContent = msg.getSendContent();
        if (StrUtil.isNotBlank(sendContent)) {
            log.debug("客户端发送文本的聊天消息:  " + sendContent);
            return sendContent;
        }
        log.warn("语音消息和文字消息不能同时发送或者同时为空");
        throw new BendanException("发送消息为空");
    }
}
