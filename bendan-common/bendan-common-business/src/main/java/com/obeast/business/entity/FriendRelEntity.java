package com.obeast.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wxl
 * Date 2023/1/22 16:54
 * @version 1.0
 * Description: 好友关系表实体类
 */
@Data
@TableName(value = "friend_rel", autoResultMap = true)
public class FriendRelEntity {

    /**
     * userAIds
     * */
    @TableField("user_a")
    private Long userA;

    /**
     *userBIds
     * */
    @TableField("user_b")
    private Long userB;
}
