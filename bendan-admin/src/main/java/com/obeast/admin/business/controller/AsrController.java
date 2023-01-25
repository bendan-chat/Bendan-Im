package com.obeast.admin.business.controller;

import com.obeast.common.three.enumration.AsrConstant;
import com.obeast.common.three.template.TencentAsrTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2023/1/3 11:40
 * @version 1.0
 * Description: stt
 */
@Tag(name = "asr 接口")
@RequestMapping("/asr")
@RestController
@RequiredArgsConstructor
public class AsrController {

    private final TencentAsrTemplate tencentAsrTemplate;

    @Operation(summary = "翻译")
    @GetMapping("/turnText")
    public String asr(@RequestParam("url") String url) {
        return tencentAsrTemplate.asr(url, AsrConstant.VoiceSuffix.WAV.getName());
    }

}
