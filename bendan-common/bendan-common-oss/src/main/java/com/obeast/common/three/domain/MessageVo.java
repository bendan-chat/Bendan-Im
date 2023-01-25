package com.obeast.common.three.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxl
 * Date 2023/1/4 13:42
 * @version 1.0
 * Description: SseEmitterVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {

    /**
     * 客户端id
     */
    private Long clientId;
    /**
     * 传输数据体(json)
     */
    private String data;
}
