package com.obeast.chat.business.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author wxl
 * Date 2022/12/27 12:40
 * @version 1.0
 * Description: 基础消息实体
 */
@Data
public class BaseMsg implements Serializable{
    /**
     * 指令
     * 固定值，标识的是一个协议类型，不同协议对应不同的值
     * 1->新的连接
     * 2->心跳
     * 3->聊天文本消息
     * 4->聊天二进制消息（音频）
     * */
    public Integer code;


    /**
     * 当前用户fromUuid
     * */
    private Long fromId;


}
