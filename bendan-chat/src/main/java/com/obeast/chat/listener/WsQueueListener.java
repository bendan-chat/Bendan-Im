package com.obeast.chat.listener;

import cn.hutool.json.JSONUtil;

import com.obeast.chat.domain.ChatChannelGroup;
import com.obeast.chat.entity.ChatRecordEntity;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;



/**
 * @author wxl
 * Date 2022/12/27 12:39
 * @version 1.0
 * Description: 队列监听器
 */
@Component
@RabbitListener(queues = "ws_queue_${netty.port}")
@RequiredArgsConstructor
public class WsQueueListener {
    private static final Logger log = LoggerFactory.getLogger(WsQueueListener.class);

    private final ChatChannelGroup channelGroup;

//    @RabbitHandler
//    public void wsShutDownMsg(ShutDownMsg shutDownMsg){
//
//        //查看当前设备Id是否在本机
//        String userUuid = shutDownMsg.getuserUuid();
//        Channel channel = ChannelGroup.getChannel(userUuid);
//        System.out.println("监听器获得的数据为：" + shutDownMsg);
//        if (channel != null) {
//            System.out.println("监听器发送消息给客户端完成。。。。");
//
//            TextWebSocketFrame resp = new TextWebSocketFrame(JSONUtil.toJsonStr(shutDownMsg));
//            channel.writeAndFlush(resp);
//        }
//
//    }

    @RabbitHandler
    public void wsChatMsg(ChatRecordEntity chatMsg, com.rabbitmq.client.Channel channelMq,
                          @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("RabbitHandler is listening");
        //根据toId查询userUuid
        Long toUuid = chatMsg.getToId();
        io.netty.channel.Channel channel = channelGroup.getChannel(toUuid);
        if (channel != null) {
            log.debug("转发的数据为："+ chatMsg);
            TextWebSocketFrame resp = new TextWebSocketFrame(JSONUtil.toJsonStr(chatMsg));
            channel.writeAndFlush(resp);
            RabbitMQUtils.askMessage(channelMq, tag, log);
        }else {
            log.error("消息转发出现异常请检测");
        }

    }
}
