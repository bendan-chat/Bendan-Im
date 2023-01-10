package com.obeast.chat.business.service.feign;

import com.obeast.business.entity.SysUserEntity;
import com.obeast.core.base.CommonResult;
import com.obeast.core.config.fegin.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author wxl
 * Date 2023/1/9 19:15
 * @version 1.0
 * Description:
 */
@FeignClient(name = "bendan-admin", configuration = FeignConfig.class)
public interface TestService {

    @GetMapping("/sysUser/listAll")
    CommonResult<List<SysUserEntity>> listAll();
}
