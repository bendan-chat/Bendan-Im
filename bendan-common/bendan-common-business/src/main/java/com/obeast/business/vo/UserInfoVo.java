package com.obeast.business.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxl
 * Date 2023/1/8 13:53
 * @version 1.0
 * Description:
 */
@Data
public class UserInfoVo implements Serializable {


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
    @NotNull(groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "用户名")
    private String username;


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
    @NotNull(groups = {AddGroup.class, UpdateGroup.class})
    @Email(groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "邮箱")
    private String email;


    /**
     * 状态（0-正常  1-锁定  2-删除）
     */
    @NotNull(groups = {UpdateGroup.class})
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
    @NotNull(groups = {AddGroup.class})
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
