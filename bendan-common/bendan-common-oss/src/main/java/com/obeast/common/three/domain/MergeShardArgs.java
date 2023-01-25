package com.obeast.common.three.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxl
 * @version 1.0
 * description: 文件合并参数
 * date 2022/7/6 18:38
 */
@Data
@AllArgsConstructor
public class MergeShardArgs {

    /**
     * 分片总数
     * */
    private Integer shardCount;

    /**
     *文件名
     * */
    private String fileName;

    /**
     *文件的md5
     * */
    private String md5;

    /**
     *文件类型
     * */
    private String fileType;

    /**
     *文件大小
     * */
    private Long fileSize;

}
