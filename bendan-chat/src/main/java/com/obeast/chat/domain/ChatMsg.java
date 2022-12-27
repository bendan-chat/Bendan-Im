package com.obeast.chat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxl
 * Date 2022/12/27 12:40
 * @version 1.0
 * Description: 聊天消息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMsg extends BaseMsg {

    /**
     * fromId 来自谁的ID
     * */
    private String fromUuid;


    /**
     * toId 去向谁的的ID
     * */
    private String toUuid;

    /**
     * content 内容
     * */
    private String content;

    /**
     * 消息类型
     * */
    private Integer sendType;

}
