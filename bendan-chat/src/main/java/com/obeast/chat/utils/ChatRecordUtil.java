package com.obeast.chat.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.chat.business.domain.msg.ChatStrMsg;
import com.obeast.chat.business.service.ChatRecordService;
import com.obeast.core.exception.BendanException;
import com.obeast.core.utils.SpringBeanUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 * @author wxl
 * Date 2023/2/11 15:24
 * @version 1.0
 * Description: 消息工具类
 */
@Slf4j
@UtilityClass
public class ChatRecordUtil {

    /**
     * Description: 对方不在线
     * @author wxl
     * Date: 2023/2/11 16:35
     * @param chatRecordEntity chatRecordEntity
     */
    public void setNotOnline (ChatRecordEntity chatRecordEntity) {
        chatRecordEntity.setStatus(0);
    }

    /**
     * Description: 对方在线
     * @author wxl
     * Date: 2023/2/11 16:35
     * @param chatRecordEntity chatRecordEntity
     */
    public void setOnline (ChatRecordEntity chatRecordEntity) {
        chatRecordEntity.setStatus(0);
    }


    /**
     * Description: ChatStrMsg  ->  ChatRecordEntity
     * @author wxl
     * Date: 2023/2/11 15:30
     * @param msg ChatStrMsg
     * @return com.obeast.business.entity.ChatRecordEntity
     */
    public ChatRecordEntity chatStrMsgToEntity (ChatStrMsg msg) {
        Long fromId = msg.getFromId();
        Long toId = msg.getToId();
        String sendMsg = handlerMsg(msg);
        //      同步消息入DB库 方便展示
        ChatRecordEntity chatRecordEntity = new ChatRecordEntity();
        chatRecordEntity.setFromId(fromId);
        chatRecordEntity.setToId(toId);
        chatRecordEntity.setSendContent(sendMsg);
        chatRecordEntity.setSendType(msg.getSendType());
        chatRecordEntity.setSendTime(new Date());
        Long sendTimeLength = msg.getLength();
        if (ObjectUtil.isNotNull(sendTimeLength)) {
            chatRecordEntity.setLength(sendTimeLength);
        }
        return chatRecordEntity;
    }

    /**
     * Description: 处理语音消息和文本消息
     *
     * @param msg msg
     * @return byte[]
     * @author wxl
     * Date: 2023/1/3 15:39
     */
    private String handlerMsg(ChatStrMsg msg) {
        String sendContent = msg.getSendContent();
        if (StrUtil.isNotBlank(sendContent)) {
            log.debug("客户端发送文本的聊天消息:  " + sendContent);
            return sendContent;
        }
        log.warn("语音消息和文字消息不能同时发送或者同时为空");
        throw new BendanException("发送消息为空");
    }
}
