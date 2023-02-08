package com.obeast.security.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.dto.SysUserDTO;
import com.obeast.business.vo.UserInfoVo;
import com.obeast.core.domain.PageParams;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;
import com.obeast.business.entity.SysUserEntity;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author wxl
 * Date 2022/11/30 9:20
 * @version 1.0
 * Description: 针对表【bendan_sys_user】的数据库操作Service
 */
public interface SysUserService extends IService<SysUserEntity> {



    /**
     * Description: 用户详情删除缓存
     * @author wxl
     * Date: 2023/2/8 10:19
     * @param username username
     */
    void delUserCache(String username);

    /**
     * Description: 用户详情删除缓存
     * @author wxl
     * Date: 2023/2/8 10:20
     * @param userId userId
     */
    void delUserCache(Long userId);


    /**
     * Description: 登录
     *
     * @param username username
     * @param password password
     * @param request  request
     * @param response response
     * @return com.obeast.core.base.CommonResult<?>
     * @author wxl
     * Date: 2022/11/30 9:56
     */
    CommonResult<?> login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws LoginException;


    /**
     * Description: 创建用户
     * @author wxl
     * Date: 2022/12/8 15:38
     * @param sysUserDto registerSysUserParam
     * @return java.lang.Boolean
     */
    Boolean createUser(SysUserDTO sysUserDto) ;

    /**
     * Description: 新增用户不分配角色
     *
     * @param sysUser agriIotSysUser
     * @author wxl
     * Date: 2022/12/7 16:39
     */
    Boolean addUser(SysUserEntity sysUser) throws LoginException;

//    ----------------------default------------------------------------------

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
     * @param pageParams 分页参数
     * @return PageObjects<UserInfoEntity>
     */
    PageObjects<SysUserEntity> queryPage(PageParams pageParams);



    /**
     * Description: 查询所有
     * @author wxl
     * Date: 2022/11/30 10:26
     * @return java.util.List<com.obeast.admin.business.entity.UserInfoEntity>
     */
    List<SysUserEntity> queryAll();


    /**
     * Description: 根据Id查询
     * @author wxl
     * Date: 2022/11/30 10:27
     * @param userId   userId
     * @return com.obeast.admin.business.entity.BendanSysUser
     */
    SysUserEntity queryById(Long userId);


    /**
     * Description: 查询用户 by username
     * @author wxl
     * Date: 2022/11/30 15:06
     * @param username  username
     * @return com.obeast.business.entity.BendanSysUser
     */
    SysUserEntity findByUsername(String username);


    /**
     * Description: 获取id
     * @author wxl
     * Date: 2022/12/28 21:50
     * @param username username
     * @return java.lang.Long
     */
    Long getIdByUsername(String username);
    /**
     * Description: 更新用户
     * @author wxl
     * Date: 2022/12/9 15:25
     * @param sysUserDto  sysUserUpdateParam
     * @return java.lang.Boolean
     */
    Boolean updateUser(SysUserDTO sysUserDto);


    /**
     * Description: 判断用户是否存在 username
     * @author wxl
     * Date: 2022/12/10 23:16
     * @param username  username
     * @return java.lang.Boolean
     */
    Boolean isUserExistByName(String username);

    /**
     * Description: 逻辑删除
     * @author wxl
     * Date: 2022/12/12 15:53
     * @param userId    userId
     * @return java.lang.Boolean
     */
    Boolean logicDel(Long userId);

    /**
     * Description: 判断用户是否存在 id
     * @author wxl
     * Date: 2022/12/11 0:23
     * @param id  id
     * @return java.lang.Boolean
     */
    Boolean isUserExistById(Long id);

    /**
     * Description: 删除用户
     * @author wxl
     * Date: 2022/12/11 20:35
     * @param userId  userId
     * @return java.lang.Boolean
     */
    Boolean delUser(Long userId);

    /**
     * Description: 登出
     * @author wxl
     * Date: 2022/12/12 11:05
     * @param request    request
     */
    CommonResult<?> logout(HttpServletRequest request);

    /**
     * Description: 用户查询用户详情
     * @author wxl
     * Date: 2023/2/2 11:18
     * @param username  username
     * @return com.obeast.business.vo.UserInfoVo
     */
    UserInfoVo getUserinfo(String username);

    /**
     * Description: 修改用户密码
     *
     * @param userId userId
     * @param password password
     * @return java.lang.Boolean
     * @author wxl
     * Date: 2023/2/6 17:35
     */
    Boolean updateUserPassword(Long userId, String password);

    String getUsernameById(Long userId);

    /**
     * Description: 根据邮箱获取用户id
     * @author wxl
     * Date: 2023/2/8 17:26
     * @param email  email
     * @return java.lang.Long
     */
    Long getIdByEmail(String email);
}
