package com.obeast.chat.listener;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author wxl
 * Date 2023/2/11 15:18
 * @version 1.0
 * Description: 用户消息队列
 */
@Component
public class UserMsgQueue {

    /**
     * 用户消息队列实例
     * */
    private final Map<Long, Queue<String>> userCache = new HashMap<>(16);

    private void init(Long userId, String msg) {
        LinkedList<String> list = new LinkedList<>();
        list.add(msg);
        userCache.put(userId, list);
    }

    /**
     * Description: 添加
     *
     * @param userId userId
     * @param msg    msg
     * @author wxl
     * Date: 2022/12/27 13:06
     */
    public void offerMsg(Long userId, String msg) {
        Queue<String> queue = getQueue(userId);
        if (queue == null) {
            init(userId, msg);
        }else {
            queue.offer(msg);
        }
    }

    /**
     * Description: 获取所有消息
     *
     * @param userId userId
     * @return io.netty.channel.Channel
     * @author wxl
     * Date: 2022/12/28 22:53
     */
    private Queue<String> getQueue(Long userId) {
        return userCache.get(userId);
    }

    /**
     * Description: 弹出一个消息
     * @author wxl
     * Date: 2023/2/11 15:17
     * @param userId userId
     * @return java.lang.String
     */
    public String pollMsg(Long userId) {
        Queue<String> queue = getQueue(userId);
        if (queue == null) {
            return null;
        }
        String poll = queue.poll();
        if (poll == null) {
            userCache.remove(userId);
            return null;
        }
        return poll;
    }

}
