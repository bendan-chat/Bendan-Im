package com.obeast.common.three.enumration;

/**
 * @author wxl
 * Date 2022/12/26 20:58
 * @version 1.0
 * Description: FileType
 */
public enum FileType {
    Image(1, " 图片"),
    Audio(2, "音频"),

    Video(3, "视频"),
    Attachment(4, "附件"),
    Document(5, "文档"),
    Excel(6, "Excel"),
    License(7, "证书");

    private final Integer code;
    private final String desc;

    FileType(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
