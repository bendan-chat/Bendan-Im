package com.obeast.common.three.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author wxl
 * Date 2022/9/21 15:30
 * @version 1.0
 * Description: MinioTemplate 模板返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinioResult {
    /**
     * OSS 存储时文件路径
     */
    private String ossFilePath;
    /**
     * 原始文件名
     */
    private String originalFileName;
}

