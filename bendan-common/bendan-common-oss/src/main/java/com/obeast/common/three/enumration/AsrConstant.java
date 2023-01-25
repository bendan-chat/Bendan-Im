package com.obeast.common.three.enumration;

/**
 * @author wxl
 * Date 2023/1/3 13:29
 * @version 1.0
 * Description: asr常量
 */
public interface AsrConstant {


    /**
     * @author wxl
     * Date 2023/1/6 14:30
     * @version 1.0
     * Description: 音频文件枚举
     */
    enum VoiceSuffix {
        PCM("pcm"),
        WAV("wav"),
        ;
        private final String name;

        VoiceSuffix(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
