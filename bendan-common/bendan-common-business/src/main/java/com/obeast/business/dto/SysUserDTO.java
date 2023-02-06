package com.obeast.business.dto;

import com.obeast.business.entity.SysUserEntity;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wxl
 * Date 2022/12/10 15:49
 * @version 1.0
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserDTO extends SysUserEntity {
    /**
     * 角色ids
     */
    @NotNull(groups = {AddGroup.class})
    @Schema(description = "角色ids")
    private List<Long> roleIds;
}
