package com.obeast.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * @author wxl
 * Date 2022/11/30 9:20
 * @version 1.0
 * Description: bendan_sys_user
 */
@TableName(value ="bendan_sys_user")
@Data
public class SysUserEntity implements Serializable {




    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull(groups = {UpdateGroup.class})
    @Schema(description = "用户Id")
    private Long id;

    /**
     * 用户名称
     */
    @NotNull(groups = {AddGroup.class})
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Length(min = 8, max = 16, message = "密码长度必须在8~16之间", groups = {AddGroup.class})
    @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
    @Schema(description = "密码")
    private String password;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 个人描述
     */
    @Schema(description = "个人描述")
    private String selfDescription;

    /**
     * 手机号
     */
    @Length(min = 11, max = 11, message = "手机号必须为11位", groups = {AddGroup.class})
    @Schema(description = "手机号")
    private String phoneNumber;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 邮箱
     */
    @NotNull(groups = {AddGroup.class})
    @Email(groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "邮箱")
    private String email;


    /**
     * 状态（0-正常  1-锁定  2-删除）
     */
    @Range(min = 0, max = 2)
    @Schema(description = "状态（0-正常 1-锁定 2-删除）")
    private Integer status;


    /**
     * 性别   (-1 未知 0 女性  1 男性)
     */
    @Range(min = -1, max = 1, groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "性别   (-1 未知 0 女性  1 男性)")
    private Integer gender;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createId;

    /**
     * 修改人ID
     */
    @NotNull(groups = {UpdateGroup.class})
    @Schema(description = "修改人ID")
    private Long updateId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", hidden = true)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", hidden = true)
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}