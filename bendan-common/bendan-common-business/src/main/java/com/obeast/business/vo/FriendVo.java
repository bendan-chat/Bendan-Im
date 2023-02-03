package com.obeast.business.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author wxl
 * Date 2022/12/27 23:26
 * @version 1.0
 * Description:
 */
@Data
public class FriendVo {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull(groups = {UpdateGroup.class})
    @Schema(description = "用户Id")
    private Long id;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 用户名称
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 性别   (-1 未知 0 女性  1 男性)
     */
    @Range(min = -1, max = 1, groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "性别   (-1 未知 0 女性  1 男性)")
    private Integer gender;
}
