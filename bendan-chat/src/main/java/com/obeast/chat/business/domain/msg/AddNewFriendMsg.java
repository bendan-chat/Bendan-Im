package com.obeast.chat.business.domain.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxl
 * Date 2022/12/27 12:42
 * @version 1.0
 * Description: 心跳消息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddNewFriendMsg extends BaseMsg{

    /**
     * fromId 来自谁的ID
     * */
    private Long fromId;

    /**
     * toId 去向谁的的ID
     * */
    private Long toId;

}

