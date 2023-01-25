package com.obeast.admin.business.service;

import com.obeast.common.three.domain.FlyweightRes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author wxl
 * Date 2023/1/4 13:35
 * @version 1.0
 * Description: SseEmitter发送信息的服务类
 */
public interface SseEmitterService {
    /**
     * 创建连接
     *
     * @param clientId 客户端ID
     */
    SseEmitter createConnect(Long clientId);


    /**
     * 根据客户端id获取SseEmitter对象
     *
     * @param clientId 客户端ID
     */
    SseEmitter getSseEmitterByClientId(Long clientId);


    /**
     * 发送消息给所有客户端
     *
     * @param msg 消息内容
     */
    void sendMessageToAllClient(String msg);

    /**
     * 给指定客户端发送消息
     *
     * @param clientId 客户端ID
     * @param msg      消息内容
     */
    void sendMessageToOneClient(Long clientId, String msg);


    /**
     * Description: 往共享的map中放数据
     * @author wxl
     * Date: 2022/9/21 15:34
     * @param clientId client Id
     * @param flyweightRes response entry
     */
    void sendResMapToOneClient(Long clientId, FlyweightRes flyweightRes);

    /**
     * 关闭连接
     *
     * @param clientId 客户端ID
     */
    void closeConnect(Long clientId);



    /**
     * Description: 服务器给客户端发送消息
     * @author wxl
     * Date: 2023/1/4 13:35
     * @param userUuid userUuid
     */
    void sendMessage(Long userUuid);
}
