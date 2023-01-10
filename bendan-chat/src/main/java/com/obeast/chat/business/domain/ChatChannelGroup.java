package com.obeast.chat.business.domain;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 * Date 2022/12/27 13:07
 * @version 1.0
 * Description: 保存连接对象的实体 （该类保存的是线程不能持久化）
 */
@Slf4j
@Component
public class ChatChannelGroup {
    //保存连接对象的集合
    /**
     * key 设备id
     * channel 连接通道
     * */
    public final Map<Long, Channel> connects = new ConcurrentHashMap<>();

    /**
     * Description: 添加
     * @author wxl
     * Date: 2022/12/27 13:06
     * @param userId  userId
     * @param channel channel
     */
    public void addChannel(Long userId, Channel channel) {
        log.debug("{}用户建立通道", userId);
        connects.put(userId, channel);
    }

    /**
     * Description: 查
     * @author wxl
     * Date: 2022/12/28 22:53
     * @param userId userId
     * @return io.netty.channel.Channel
     */
    public Channel getChannel(Long userId) {
        return connects.get(userId);
    }

    /**
     * Description: 删除
     * @author wxl
     * Date: 2022/12/28 22:53
     * @param userId userId
     * @return boolean
     */
    public boolean removeChannel(Long userId) {
        return connects.remove(userId) != null;
    }


}
