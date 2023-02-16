package com.obeast.common.three.template;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.obeast.core.exception.BendanException;
import com.tencentcloudapi.asr.v20190614.AsrClient;
import com.tencentcloudapi.asr.v20190614.models.SentenceRecognitionRequest;
import com.tencentcloudapi.asr.v20190614.models.SentenceRecognitionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxl
 * Date 2023/1/24 20:25
 * @version 1.0
 * Description: 腾讯asr 模板
 */
@Slf4j
@RequiredArgsConstructor
public class TencentAsrTemplate {

    private final AsrClient asrClient;

    /**
     * Description: tencent asr
     *
     * @param url    url
     * @param format format
     * @author wxl
     * Date: 2023/1/24 20:38
     */
    public String asr(String url, String format) {
       try {
           // 实例化一个请求对象,每个接口都会对应一个request对象
           SentenceRecognitionRequest req = new SentenceRecognitionRequest();
           req.setProjectId(0L);
           req.setSubServiceType(2L);
           req.setEngSerViceType("16k");
           req.setSourceType(0L);
           req.setUrl(url);
           req.setVoiceFormat(format);
           req.setUsrAudioKey("bendan");
           // 返回的resp是一个SentenceRecognitionResponse的实例，与请求对象对应
           SentenceRecognitionResponse resp = asrClient.SentenceRecognition(req);
           // 输出json格式的字符串回包
           String jsonString = SentenceRecognitionResponse.toJsonString(resp);
           if (StrUtil.isNotBlank(jsonString)){
               return JSONUtil.parseObj(jsonString).getStr("Result");
           }else {
               throw new BendanException("asr 异常");
           }
       }catch (Exception e) {
           log.error("Tencent asr 异常",e);
       }
        return null;
    }

}
