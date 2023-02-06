package com.obeast.chat.business.domain.msg;

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
public class ChatStrMsg extends BaseMsg {

    /**
     * fromId 来自谁的ID
     * */
    private Long fromId;


    /**
     * toId 去向谁的的ID
     * */
    private Long toId;

    /**
     * content 内容
     * */
    private String sendContent;


    /**
     * 消息类型
     * */
    private Integer sendType;

    /**
     * 长度
     * */
    private Long length;

}
