package com.obeast.chat.business.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wxl
 * Date 2022/12/27 14:16
 * @version 1.0
 * Description: 封装的前端数据 + 时间
 */
@Data
public class ChatMsgVo implements Serializable {
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
    private String content;

    /**
     * 消息类型
     * */
    private Integer sendType;

    /**
     * 发送时间
     */
    private Date sendTime;
}
