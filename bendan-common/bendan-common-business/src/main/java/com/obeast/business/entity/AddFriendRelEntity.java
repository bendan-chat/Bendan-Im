package com.obeast.business.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author wxl
 * Date 2023/2/2 11:16
 * @version 1.0
 * Description: 新增好友 关系列表
 */
@TableName(value ="add_friend_rel")
@Data
public class AddFriendRelEntity implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 当前 用户id
     */
    @NotNull
    private Long curUserId;

    /**
     * 主动添加 的 用户id 
     */
    @NotNull
    private Long addUserId;

    /**
     * 主动添加 的 用户 昵称
     */
    @NotNull
    private String nickname;

    /**
     * 主动添加 的 用户 头像
     */
    @NotNull
    private String avatar;

    /**
     * 主动添加 的 用户 描述
     */
    @NotNull
    private String description;

    /**
     * 主动添加 的 用户 添加状态 [1 = 单方添加(默认)； 2 = 对方同意]
     */
    private Integer status;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}