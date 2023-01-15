package com.obeast.admin.business.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.obeast.admin.business.dao.OssEntityDao;
import com.obeast.admin.business.service.OssMinioService;
import com.obeast.business.entity.OssEntity;
import com.obeast.common.oss.domain.FlyweightRes;
import com.obeast.common.oss.enumration.FileType;
import com.obeast.common.oss.enumration.FileUploadConstant;
import com.obeast.common.oss.template.TencentOssTemplate;
import com.obeast.common.oss.utils.FileUploadUtil;
import com.obeast.common.stt.constant.BaiduSttConstant;
import com.obeast.core.base.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


/**
 * @author obeast-dragon
 * Date 2022-09-21 15:19:09
 * @version 1.0
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/oss")
@Tag(name = "Oss 接口")
@RequiredArgsConstructor
public class OssController {

    private final OssMinioService ossMinioService;

    private final TencentOssTemplate tencentOssTemplate;

    private final FlyweightRes res;

    private final OssEntityDao ossEntityDao;


    /**
     * Description: 文件上传
     *
     * @param file   file
     * @param userId userId
     * @return com.obeast.oss.base.R<com.obeast.business.entity.OssEntity>
     * @author wxl
     * Date: 2022/9/21 16:04
     */
    @Operation(summary = "腾讯上传文件")
    @PostMapping(value = "/uploadTencentFile")
    public String uploadTencentFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId, @RequestParam("type") int type) throws IOException, ExecutionException, InterruptedException {
        String fileName;
        if (type == FileType.Audio.getCode()) {
            fileName = UUID.randomUUID() + FileUploadConstant.POINT + BaiduSttConstant.FileAsrSuffix.WAV.getName();
        }else {
            fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : file.getName();
        }
        String uploadUrl = tencentOssTemplate.upload(file, userId, fileName);
        ossEntityDao.insertAsync(uploadUrl, userId, tencentOssTemplate.getObjectUrlKey(userId, fileName));
        return uploadUrl;
    }

    /**
     * Description: 文件上传
     *
     * @param file   file
     * @param userId userId
     * @return com.obeast.oss.base.R<com.obeast.business.entity.OssEntity>
     * @author wxl
     * Date: 2022/9/21 16:04
     */
    @Operation(summary = "minio上传文件")
    @SneakyThrows
    @PostMapping(value = "/uploadMinioFile")
    public CommonResult<OssEntity> uploadMinioFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return ossMinioService.uploadFile(file, userId);
    }


    /**
     * Description: file stop status
     *
     * @param flag   the flag
     * @param userId userId
     * @return com.obeast.core.base.CommonResult<?>
     * @author wxl
     * Date: 2022/12/26 21:05
     */
    @Operation(summary = "minio暂停上传文件")
    @GetMapping(value = "/stopStatus")
    public CommonResult<?> setStopStatus(@RequestParam("flag") Integer flag, @RequestParam("userId") Long userId) {
        /*
          1 -> true 上传
          0 -> false 暂停
          */
        boolean stopStatus = flag.equals(1);
        if (res.get(userId) != null) {
            res.get(userId).put("stopStatus", stopStatus);
            if (!stopStatus) {
                log.info("{}>>>>>>>>>>>>>>>上传暂停", stopStatus);
            } else {
                log.info("{}>>>>>>>>>>>>>>>上传开始", stopStatus);
            }
            return CommonResult.success(stopStatus);
        }
        return CommonResult.error("请先上传");

    }


    /**
     * Description: 获取实现上传进度
     *
     * @param userId userId
     * @return com.obeast.core.base.CommonResult<cn.hutool.json.JSONObject>
     * @author wxl
     * Date: 2022/12/14 17:53
     */
    @Operation(summary = "minio获取实现上传进度")
    @GetMapping(value = "/percent")
    public CommonResult<JSONObject> getUploadPercent(@RequestParam("userId") Long userId) {

        if (res.get(userId) != null) {
            String uploadPercent = res.get(userId).get("uploadPercent") == null ? "0" : String.valueOf(res.get(userId).get("uploadPercent"));
            String uploadSize = res.get(userId).get("uploadSize") == null ? "0" : String.valueOf(res.get(userId).get("uploadSize"));
            String fileSize = res.get(userId).get("fileSize") == null ? "0" : String.valueOf(res.get(userId).get("fileSize"));
            JSONObject data = new JSONObject();
            try {
                data.append("uploadPercent", uploadPercent);
                data.append("uploadSize", uploadSize);
                data.append("fileSize", fileSize);
                data.append("fileName", res.get(userId).get("fileName"));
            } catch (JSONException e) {
                log.error("上传进度查询失败{}", e.getMessage());
            }
            return CommonResult.success(data, "获取进度百分比成功");
        }
        return CommonResult.error("请先上传");

    }


    /**
     * Description: 重置上传进度
     *
     * @param userId userId
     * @return com.obeast.core.base.CommonResult
     * @author wxl
     * Date: 2022/12/31 13:19
     */
    @Operation(summary = "minio重置上传进度")
    @GetMapping("/resetPercent")
    @ResponseBody
    public CommonResult<?> resetPercent(@RequestParam("userId") Long userId) {
        if (res.get(userId) != null) {
            res.get(userId).put("uploadPercent", 0);
            return CommonResult.success();
        }
        return CommonResult.error("请先上传");
    }


}
