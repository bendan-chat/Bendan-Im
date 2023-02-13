package com.obeast.chat.utils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;

import java.io.IOException;


/**
 * @author wxl
 * Date 2022/12/27 14:07
 * @version 1.0
 * Description: mq工具类
 */
public class RabbitMQUtils {

    /**
     * 队列名字
     * */
    public static final String QUEUE_NAME = "ws_queue_";

    /**
     * 交换机名
     * */
    public static final String EXCHANGE_NAME = "ws_exchange";

    public static void askMessage(Channel channel, long tag, final Logger logger) {
        askMessage(channel, tag, logger, false);
    }

    public static void askMessage(Channel channel, long tag, final Logger logger, boolean multiple) {
        try {
            channel.basicAck(tag, multiple);
        } catch (IOException e) {
            logger.error("RabbitMQ，IO异常，异常原因为：{}", e.getMessage());
        }
    }

    public static void rejectMessage(Channel channel, long tag, final Logger logger) {
        rejectMessage(channel, tag, logger, false, false);
    }

    public static void rejectAndBackMQ(Channel channel, long tag, final Logger logger) {
        rejectMessage(channel, tag, logger, false, true);
    }

    public static void rejectMessage(Channel channel, long tag, final Logger logger, boolean multiple, boolean request) {
        try {
            channel.basicNack(tag, multiple, request);
        } catch (IOException e) {
            logger.error("RabbitMQ，IO异常，异常原因为：{}", e.getMessage());
        }
    }
}
