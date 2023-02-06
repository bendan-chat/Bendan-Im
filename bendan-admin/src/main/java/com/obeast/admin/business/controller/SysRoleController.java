package com.obeast.admin.business.controller;

import cn.hutool.json.JSONObject;
import com.obeast.security.business.service.SysRoleService;
import com.obeast.business.dto.SysRoleDTO;
import com.obeast.business.entity.SysRoleEntity;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.PageConstant;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.DefaultGroup;
import com.obeast.core.validation.group.DeleteGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-12-04 22:35:16
 * @version 1.0
 * Description: 角色表
 */
@RestController
@RequestMapping("/sysRole")
@Tag(name = "角色管理接口")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

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
    public CommonResult<PageObjects<SysRoleEntity>> list(@RequestParam JSONObject params) {
        if (params.size() == 0) {
            return CommonResult.error("params is null");
        }
        PageObjects<SysRoleEntity> page = sysRoleService.queryPage(params);
        return CommonResult.success(page, "page");
    }


    /**
     * 查询所有
     * */
    @GetMapping("/listAll")
    @Operation(summary = "查询所有")
    public CommonResult<List<SysRoleEntity>> listAll() {
        List<SysRoleEntity> data = sysRoleService.queryAll();
        return CommonResult.success(data, "list");
    }

    /**
     * 根据id查询
     */
    @GetMapping("/getOneById/{id}")
    @Operation(summary = "根据id查询")
    @Parameter(name = "id", description = "id of the entity", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Long.class))
    public CommonResult<SysRoleEntity> getOneById(@PathVariable("id") Long id){
		SysRoleEntity sysRoleEntity = sysRoleService.queryById(id);
        return CommonResult.success(sysRoleEntity, "bendanSysRole");
    }


    /**
     * 新增
     */
    @PostMapping("/save")
    @Operation(summary = "新增")
    public Boolean save(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody SysRoleDTO sysRoleDto){
        return sysRoleService.add(sysRoleDto);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改")
    public Boolean update(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody SysRoleDTO sysRoleDto){
        return sysRoleService.replace(sysRoleDto);
    }


    /**
     * 删除
     */
    @Operation(summary = "删除")
    @Parameter(name = "id", description = "id数组", required = true, in = ParameterIn.QUERY)
    @DeleteMapping("/delete")
    public Boolean delete(@Validated({DeleteGroup.class, DefaultGroup.class}) @RequestParam("id") Long id) {
        return sysRoleService.deleteById(id);
    }

}
