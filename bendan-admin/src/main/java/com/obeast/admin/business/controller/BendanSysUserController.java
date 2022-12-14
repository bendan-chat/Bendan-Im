package com.obeast.admin.business.controller;


import cn.hutool.json.JSONObject;
import com.obeast.business.entity.SysUserEntity;

import com.obeast.security.business.service.BendanSysUserService;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.PageConstant;
import com.obeast.core.domain.PageObjects;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wxl
 * Date 2022/11/30 9:47
 * @version 1.0
 * Description: BendanSysUser Controller
 */
@RestController
@RequestMapping("/sysUser")
@Tag(name = "SysMenu接口")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class BendanSysUserController {

    private final BendanSysUserService bendanSysUserService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public CommonResult<?> login(SysUserLoginParam userInfoLoginParam, HttpServletRequest request) throws LoginException {
        return bendanSysUserService.login(userInfoLoginParam.getUsername(), userInfoLoginParam.getPassword(), request);
    }

    @Operation(summary = "根据用户名获取通用用户信息")
    @GetMapping("/getUserinfo")
    public CommonResult<UserInfo> getUserinfo(@RequestParam("username") String username) throws LoginException {
        UserInfo userInfo = bendanSysUserService.findUserInfo(username);
        if (userInfo == null) {
            CommonResult.error("获取失败");
        }
        return CommonResult.success(userInfo);
    }

    /**
     * 分页列表
     */
    @GetMapping("/listPage")
    @Operation(summary = "分页查询")
    @Parameters({
            @Parameter(name = PageConstant.CUR, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = PageConstant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = PageConstant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class)),
            @Parameter(name = PageConstant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = String.class))
    })
    public CommonResult<PageObjects<SysUserEntity>> list(@RequestParam JSONObject params) {
        if (params.size() == 0) {
            return CommonResult.error("params is null");
        }
        PageObjects<SysUserEntity> page = bendanSysUserService.queryPage(params);
        return CommonResult.success(page, "page");
    }


    /**
     * 查询所有
     */
    @GetMapping("/listAll")
    @Operation(summary = "查询所有")
    public CommonResult<List<SysUserEntity>> listAll() {
        List<SysUserEntity> data = bendanSysUserService.queryAll();
        return CommonResult.success(data, "list");
    }


    /**
     * 根据id查询
     */
    @GetMapping("/getOneById/{userId}")
    @Operation(summary = "根据id查询")
    @Parameter(name = "id", description = "id of the entity", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Long.class))
    public CommonResult<SysUserEntity> getOneById(@PathVariable("userId") Long userId) {
        if (userId < 0) {
            return CommonResult.error("id is null");
        }
        SysUserEntity userInfo = bendanSysUserService.queryById(userId);
        return CommonResult.success(userInfo, "userInfo");
    }

}
