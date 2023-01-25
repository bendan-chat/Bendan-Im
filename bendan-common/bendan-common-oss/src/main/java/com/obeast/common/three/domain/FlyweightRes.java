package com.obeast.common.three.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 * Date 2022/12/26 20:56
 * @version 1.0
 * Description: 分片文件全局共享信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlyweightRes extends HashMap<Long, Map<String, Object>> implements Serializable {

}
