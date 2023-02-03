package com.obeast.chat.business.domain;


/**
 * @author wxl
 * Date 2022/12/27 12:41
 * @version 1.0
 * Description: 对外暴露的策略   的类
 */
public interface CodeStrategyContext {


    /**
     * code
     * 固定值协议，标识的是一个协议类型，不同协议对应不同的值
     * 1->新的连接
     * 2->心跳
     * 3->聊天消息
     * 4->关闭聊天
     * 5-> 新好友添加
     * @since 0.0.4 -> 2022.7.25
     */
    Integer NEW_CONNECTION = 1;

     Integer HEARD = 2;

    Integer SEND_MSG = 3;

    Integer SEND_SHUTDOWN = 4;

    Integer NEW_FRIEND = 5;


}
