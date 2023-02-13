package com.obeast.chat.handler;

import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.business.domain.msg.ChatStrMsg;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.service.ChatRecordService;
import com.obeast.chat.utils.ChatRecordUtil;
import com.obeast.chat.utils.RabbitMQUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

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

        ChatRecordEntity chatRecordEntity = ChatRecordUtil.chatStrMsgToEntity(msg);
        //查询toId是否存在
        Channel channel = chatChannelGroup.getChannel(msg.getToId());
        if (channel != null) {
            log.debug("对方在线 通过rabbitMq ----->{}" , chatRecordEntity);
            ChatRecordUtil.setOnline(chatRecordEntity);
            // 持久化
            chatRecordService.addMsg(chatRecordEntity);
            rabbitTemplate.convertAndSend(RabbitMQUtils.EXCHANGE_NAME, "", chatRecordEntity);
        }else {
            log.debug("对方不在线 ----->{}" , chatRecordEntity);
            ChatRecordUtil.setNotOnline(chatRecordEntity);
            // 持久化
            chatRecordService.addMsg(chatRecordEntity);
        }
    }

}
