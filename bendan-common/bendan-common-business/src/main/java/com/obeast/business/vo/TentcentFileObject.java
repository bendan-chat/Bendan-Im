package com.obeast.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author wxl
 * Date 2023/2/15 17:45
 * @version 1.0
 * Description: 腾讯文件对象
 */
@Data
public class TentcentFileObject {


    /**
     * 文件名
     * */
    private String fileName;

    /**
     * 文件大小
     * */
    private Long fileSize;

    /**
     * 最后一次修改的时间
     * */
    private Date lastModified;
}
