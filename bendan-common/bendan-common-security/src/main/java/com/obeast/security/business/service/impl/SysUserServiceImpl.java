package com.obeast.security.business.service.impl;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.business.dto.SysUserDTO;
import com.obeast.business.entity.SysMenuEntity;
import com.obeast.business.entity.SysRoleEntity;
import com.obeast.business.entity.SysUserEntity;
import com.obeast.business.vo.OAuth2PasswordVo;
import com.obeast.business.vo.UserInfoVo;
import com.obeast.core.domain.PageParams;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.*;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.exception.BendanException;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.security.business.dao.SysUserDao;
import com.obeast.security.business.service.SysMenuService;
import com.obeast.security.business.service.SysRoleService;
import com.obeast.security.business.service.SysUserService;
import com.obeast.security.business.service.remote.OAuth2TokenEndpoint;
import com.obeast.security.resource.BendanUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author wxl
 * Date 2022/11/30 9:21
 * @version 1.0
 * Description: 针对表【bendan_sys_user】的数据库操作Service实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity>
        implements SysUserService {
    private final SysRoleService sysRoleService;

    private final OAuth2TokenEndpoint OAuth2TokenEndpoint;

    private final SysMenuService sysMenuService;
    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostConstruct
    private void initUser() {
        List<String> usernames = this.queryAllUsernames();
        JSONArray jsonArray = JSONUtil.parseArray(usernames);
        redisTemplate.opsForValue().set(CacheConstant.USERNAME_LIST, jsonArray);
    }

    @Async
    @Override
    public void delUserCache(String username) {
        redisTemplate.delete(UserConstant.formatKey(username));
    }

    @Async
    @Override
    public void delUserCache(Long userId) {
        String username = this.getUsernameById(userId);
        redisTemplate.delete(UserConstant.formatKey(username));
    }


    @Override
    public CommonResult<?> login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws BendanException {
        Assert.notNull(username, UserConstant.USERNAME_IS_NULL);
        Assert.notNull(password, UserConstant.PASSWORD_IS_NULL);
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        Assert.notNull(header, "Authentication header is null, please check your http headers");
        OAuth2PasswordVo oAuth2Params = new OAuth2PasswordVo();
        oAuth2Params.setGrant_type(AuthorizationGrantType.PASSWORD.getValue());
        oAuth2Params.setScope(OAuth2Constant.Scope.ALL.getName());
        oAuth2Params.setUsername(username);
        oAuth2Params.setPassword(password);
        try {
            CommonResult<?> res = OAuth2TokenEndpoint.authPassword(header, oAuth2Params);
            if (!res.getSuccess()) {
                return res;
            }
            Object commonResult = CommonResult.getOptionalData(res).orElseThrow(() -> {
                throw new BendanException(SysConstant.LOGIN_FAILED);
            });
            SysUserEntity sysUser = this.findByUsername(username);
            JSONObject data = JSONUtil.parseObj(commonResult);
            data.putOpt("userId", sysUser.getId());
            data.putOpt("avatar", sysUser.getAvatar());
            data.putOpt("nickName", sysUser.getNickName());
            data.putOpt("email", sysUser.getEmail());
            data.putOpt("gender", sysUser.getGender());
            return CommonResult.success(data);

        } catch (Exception e) {
            log.error(SysConstant.LOGIN_FAILED + "  ", e);
            return CommonResult.error(WebResultEnum.FAILURE);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<?> register(SysUserDTO sysUserDto) {
        Boolean addUser = this.addUser(sysUserDto);
        Long userId = sysUserDto.getId();
        if (addUser) {
            List<Long> roleIds = sysUserDto.getRoleIds();
            Boolean addRelation = sysRoleService.addRoleUserRels(userId, roleIds);
            Assert.isTrue(addRelation, () -> {
                throw new BendanException("新增用户角色关系失败");
            });
            return CommonResult.success();
        }
        return CommonResult.error("注册失败");
    }

    @Override
    public List<String> queryAllUsernames() {
        Object o = redisTemplate.opsForValue().get(CacheConstant.USERNAME_LIST);
        if (o == null) {
            List<String> usernames = this.queryAll().stream().map(SysUserEntity::getUsername).toList();
            JSONArray jsonArray = JSONUtil.parseArray(usernames);
            redisTemplate.opsForValue().set(CacheConstant.USERNAME_LIST, jsonArray);
            return usernames;
        } else {
            return (List<String>) o;
        }
    }

    @Override
    public Long getIdByEmail(String email) {
        Assert.notNull(email, "email cannot be null");
        LambdaQueryWrapper<SysUserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUserEntity::getEmail, email);
        SysUserEntity sysUserEntity = this.getOne(wrapper);
        if (sysUserEntity == null) {
            return null;
        } else {
            return sysUserEntity.getId();
        }
    }

    @Override
    public String getUsernameById(Long userId) {
        Assert.notNull(userId, "userId cannot be null");
        SysUserEntity sysUser = this.getById(userId);
        return sysUser.getUsername();
    }

    @Override
    public Boolean updateUserPassword(Long userId, String password) {
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(password, "password cannot be null");
        String encodePassword = this.encryptionPassword(password);
        LambdaUpdateWrapper<SysUserEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysUserEntity::getId, userId);
        wrapper.set(SysUserEntity::getPassword, encodePassword);
        this.delUserCache(userId);
        return this.update(wrapper);
    }

    @Override
    public UserInfoVo getUserinfo(String username) {
        SysUserEntity sysUser = this.findByUsername(username);
        if (sysUser == null) {
            return null;
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(sysUser, userInfoVo);
        return userInfoVo;
    }

    @Override
    public CommonResult<?> logout(HttpServletRequest request) {
        String authorization = request.getHeader(OAuth2Constant.AUTHORIZATION);
        if (authorization == null) {
            return CommonResult.success();
        }
        String token = authorization.replace(OAuth2AccessToken.TokenType.BEARER.getValue(), StrUtil.EMPTY).trim();
        if (StrUtil.isBlank(token)) {
            return CommonResult.success();
        }
        OAuth2Authorization oAuth2Authorization = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (oAuth2Authorization != null) {
            oAuth2AuthorizationService.remove(oAuth2Authorization);
        }
        return CommonResult.success();
    }

    @Override
    public PageObjects<SysUserEntity> queryPage(PageParams pageParams) {
        QueryWrapper<SysUserEntity> queryWrapper = Wrappers.query();
        IPage<SysUserEntity> page = this.page(
                new PageQueryUtils<SysUserEntity>().getPage(pageParams),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, SysUserEntity.class);
    }


    @Override
    public List<SysUserEntity> queryAll() {
        return this.list();
    }

    @Override
    public SysUserEntity queryById(Long userId) {
        Assert.notNull(userId, "userId cannot be null");
        return this.getById(userId);
    }


    @Override
    public UserInfo findUserInfo(String username) {
        UserInfo userInfo = new UserInfo();
        SysUserEntity sysUserEntity = this.findByUsername(username);
        if (ObjectUtil.isNull(sysUserEntity)) {
            throw new BadCredentialsException("用户不存在");
        }
        /*获取角色列表*/
        List<SysRoleEntity> sysRoleEntities = sysRoleService.listRolesByUserId(sysUserEntity.getId());
        Assert.isTrue(ArrayUtil.isNotEmpty(sysRoleEntities), () -> {
            throw new BendanException(RoleConstant.USER_HAS_NOT_SET_ROLES);
        });
        /*获取角色id列表*/
        List<Long> roleIds = sysRoleEntities.stream().map(SysRoleEntity::getId).toList();
        Set<String> purviewNames = roleIds
                .stream()
                .map(sysMenuService::getMenusByRoleId)
                .flatMap(Collection::stream)
                .filter(path -> StrUtil.isEmpty(path.getPath()))
                .map(SysMenuEntity::getPurviewName)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
        if (ArrayUtil.isEmpty(purviewNames)) {
            purviewNames = new HashSet<>();
        }
        userInfo
                .setSysUserEntity(sysUserEntity)
                .setPurviewNames(ArrayUtil.toArray(purviewNames, String.class))
                .setSysRoleEntities(sysRoleEntities)
                .setRoleIds(roleIds);
        return userInfo;
    }


    @Override
    public Boolean addUser(SysUserEntity sysUserEntity) {
        String username = sysUserEntity.getUsername();
        Assert.isTrue(!this.isUserExistByName(username), UserConstant.USER_EXIST);
        String encodePassword = this.encryptionPassword(sysUserEntity.getPassword());
        sysUserEntity.setPassword(encodePassword);
        return this.save(sysUserEntity);
    }

    /**
     * Description: 前台密码后台加密
     *
     * @param password password
     * @return java.lang.String
     * @author wxl
     * Date: 2023/2/8 10:00
     */
    private String encryptionPassword(String password) {
        return ENCODER.encode(password);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateUser(SysUserDTO sysUserDto) {
        sysUserDto.setUpdateId(sysUserDto.getUpdateId());
        /*修改密码*/
        sysUserDto.setPassword(ENCODER.encode(sysUserDto.getPassword()));
        /*判断用户存在*/
        Assert.isTrue(this.isUserExistById(sysUserDto.getId()), () -> {
            throw new BendanException(UserConstant.USER_NOT_FOUND);
        });
        List<Long> roleIds = sysUserDto.getRoleIds();
        /*判断角色是否存在 更新用户角色*/
        Assert.isTrue(sysRoleService.updateRolesUserRel(roleIds, sysUserDto.getId()), RoleConstant.UPDATE_RELS_USER_FAIL);
        return this.updateById(sysUserDto);
    }

    @Override
    public Long getIdByUsername(String username) {
        Assert.notNull(username, "username cannot be null");
        SysUserEntity userEntity = this.findByUsername(username);
        Assert.notNull(userEntity, "用户不存在");
        return userEntity.getId();
    }

    @Override
    public SysUserEntity findByUsername(String username) {
        Assert.notNull(username, "username cannot be null");
        return this.getOne(Wrappers.<SysUserEntity>lambdaQuery()
                .eq(SysUserEntity::getUsername, username));
    }


    @Override
    public Boolean isUserExistByName(String username) {
        Assert.notNull(username, "username cannot be null");
        SysUserEntity sysUserEntity = this.findByUsername(username);
        return sysUserEntity != null;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean delUser(Long userId) {
        Assert.notNull(userId, "userId cannot be null");
        boolean removeById = this.logicDel(userId);
        /*删除用户角色关系*/
        if (removeById) {
            sysRoleService.delRelsByUserId(userId);
        }
        return removeById;
    }

    @Override
    public Boolean logicDel(Long userId) {
        return this.update(
                Wrappers.<SysUserEntity>lambdaUpdate()
                        .eq(SysUserEntity::getId, userId)
                        .set(SysUserEntity::getStatus, 1)
        );
    }


    @Override
    public Boolean isUserExistById(Long id) {
        Assert.notNull(id, "id cannot be null");
        SysUserEntity sysUserEntity = this.getById(id);
        return sysUserEntity != null;
    }


}


