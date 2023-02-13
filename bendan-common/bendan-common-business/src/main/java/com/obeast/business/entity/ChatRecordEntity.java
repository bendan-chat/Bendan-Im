package com.obeast.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxl
 * Date 2022/12/27 14:17
 * @version 1.0
 * Description: 聊天记录模拟表
 */
@TableName(value = "bendan_chat_record", autoResultMap = true)
@Data
public class ChatRecordEntity implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @JsonIgnore
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "from_id")
    private Long fromId;

    /**
     * 专家id
     */
    @TableField(value = "to_id")
    private Long toId;

    /**
     * 发送内容
     */
    @TableField(value = "send_content")
    private String sendContent;


    /**
     * 发送类型【0文本，1图片，2语言，3视频】
     */
    @TableField(value = "send_type")
    private Integer sendType;

    /**
     * 发送时长
     */
    @TableField(value = "length")
    private Long length;

    /**
     * 发送时间
     */
    @TableField(value = "send_time")
    private Date sendTime;


    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;
}
