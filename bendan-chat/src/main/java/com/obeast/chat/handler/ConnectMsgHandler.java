package com.obeast.chat.handler;

import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.business.domain.msg.ConnectMsg;
import com.obeast.chat.business.service.ChatRecordService;
import com.obeast.chat.utils.RabbitMQUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;


/**
 * @author wxl
 * Date 2022/12/27 14:19
 * @version 1.0
 * Description: 新建连接消息处理类
 */
@Slf4j
@RequiredArgsConstructor
public class ConnectMsgHandler extends SimpleChannelInboundHandler<ConnectMsg> {

    private final ChatChannelGroup chatChannelGroup;

    private final RabbitTemplate rabbitTemplate;

    private final ChatRecordService chatRecordService;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ConnectMsg msg) throws Exception {
        Long fromId = msg.getFromId();
        if (fromId == null) {
            ctx.close();
            log.error("-------------->id为空请检查");
            return;
        }
        if (!ctx.channel().isOpen()){
            log.error("-------------->用户通道不存在请检查");
            throw new Exception("用户通道不存在请检查");
        }
        //记录当前连接的用户id以及线程对象
        chatChannelGroup.addChannel(fromId, ctx.channel());
//        List<ChatRecordEntity> unreadChatList = chatRecordService.getUnreadChatList(fromId);
//        if (unreadChatList.size() > 0){
//            for (ChatRecordEntity unread : unreadChatList) {
//                rabbitTemplate.convertAndSend(RabbitMQUtils.EXCHANGE_NAME, "", unread);
//            }
//        }
    }
}
