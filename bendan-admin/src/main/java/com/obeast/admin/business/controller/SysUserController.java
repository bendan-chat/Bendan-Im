package com.obeast.admin.business.controller;


import cn.hutool.json.JSONObject;
import com.obeast.security.business.service.SysUserService;
import com.obeast.business.dto.SysUserDTO;
import com.obeast.business.entity.SysUserEntity;
import com.obeast.business.vo.SysUserLoginParam;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.PageConstant;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wxl
 * Date 2022/11/30 9:47
 * @version 1.0
 * Description: BendanSysUser Controller
 */
@RestController
@RequestMapping("/sysUser")
@Tag(name = "用户管理接口")
@RequiredArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;

    @Operation(summary = "登录", hidden = true)
    @PostMapping("/login")
    public CommonResult<?> login(SysUserLoginParam userInfoLoginParam, HttpServletRequest request, HttpServletResponse response) throws LoginException {
        return sysUserService.login(userInfoLoginParam.getUsername(), userInfoLoginParam.getPassword(), request, response);
    }

    @Operation(summary = "登出", hidden = true)
    @DeleteMapping("/logout")
    public Boolean logout(HttpServletRequest request) {
        return sysUserService.logout(request);
    }

    @Operation(summary = "根据用户名查询用户详情")
    @GetMapping("/getUserinfo")
    public CommonResult<UserInfo> getUserinfo(@RequestParam("username") String username) throws LoginException {
        UserInfo userInfo = sysUserService.findUserInfo(username);
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
    public PageObjects<SysUserEntity> page(@RequestParam JSONObject params) {
        return sysUserService.queryPage(params);
    }


    /**
     * 查询所有
     */
    @Operation(summary = "查询所有")
    @PreAuthorize("@pvs.hasPurview('sys_user_query')")
    @GetMapping("/listAll")
    public List<SysUserEntity> listAll() {
        return sysUserService.queryAll();
    }


    /**
     * 根据id查询
     */
    @GetMapping("/getOneById/{userId}")
    @Operation(summary = "根据id查询")
    @Parameter(name = "userId", description = "id of the entity", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Long.class))
    public CommonResult<SysUserEntity> getOneById(@PathVariable("userId") Long userId) {
        SysUserEntity userInfo = sysUserService.queryById(userId);
        return CommonResult.success(userInfo, "userInfo");
    }



    @Operation(summary = "创建用户")
    @PostMapping("/create")
    public Boolean createUser (@Validated({AddGroup.class}) @RequestBody SysUserDTO sysUserDto) throws LoginException {
        return sysUserService.createUser(sysUserDto);
    }

    @Operation(summary = "修改用户详情")
    @PostMapping("/update")
    public Boolean updateUser (@Validated({UpdateGroup.class}) @RequestBody SysUserDTO sysUserDto) {
        return sysUserService.updateUser(sysUserDto);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/delete")
    public Boolean deleteUser (@RequestBody JSONObject params)  {
        Long userId = params.getLong("userId");
        return sysUserService.delUser(userId);
    }
}
