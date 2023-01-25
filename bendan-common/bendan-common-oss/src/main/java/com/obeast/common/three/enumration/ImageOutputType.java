package com.obeast.common.three.enumration;

public enum ImageOutputType {
    PNG(1, "png"),
    JPEG(2, "jpeg"),
    ICO(3, "ico");

    private final Integer code;
    private final String desc;

    ImageOutputType(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByCode(Integer code, String defaultDesc) {
        ImageOutputType[] values = ImageOutputType.values();
        for (ImageOutputType type : values){
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return defaultDesc;
    }
}
