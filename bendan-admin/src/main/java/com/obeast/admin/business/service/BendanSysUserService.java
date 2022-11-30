package com.obeast.admin.business.service;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.entity.BendanSysUser;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;

import javax.security.auth.login.LoginException;
import java.util.List;


/**
 * @author wxl
 * Date 2022/11/30 9:20
 * @version 1.0
 * Description: 针对表【bendan_sys_user】的数据库操作Service
 */
public interface BendanSysUserService extends IService<BendanSysUser> {


    /**
     * Description: 登录
     * @author wxl
     * Date: 2022/11/30 9:56
     * @param username username
     * @param password  password
     * @return com.obeast.core.base.CommonResult<?>
     */
    CommonResult<?> login(String username, String password);

    /**
     * Description: 查询用户详情
     *
     * @author wxl
     * Date: 2022/11/21 15:37
     * @param username username
     * @return UserInfo
     */
    UserInfo findUserInfo(String username) throws LoginException;

    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param params 分页参数
     * @return PageObjects<UserInfoEntity>
     */
    PageObjects<BendanSysUser> queryPage(JSONObject params);



    /**
     * Description: 查询所有
     * @author wxl
     * Date: 2022/11/30 10:26
     * @return java.util.List<com.obeast.admin.business.entity.UserInfoEntity>
     */
    List<BendanSysUser> queryAll();


    /**
     * Description: 根据Id查询
     * @author wxl
     * Date: 2022/11/30 10:27
     * @param userId   userId
     * @return com.obeast.admin.business.entity.BendanSysUser
     */
    BendanSysUser queryById(Long userId);


    /**
     * Description: 查询用户 by username
     * @author wxl
     * Date: 2022/11/30 15:06
     * @param username  username
     * @return com.obeast.business.entity.BendanSysUser
     */
    BendanSysUser findByUsername(String username);
}
