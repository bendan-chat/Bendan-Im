package com.obeast.security.business.service.impl;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.business.dto.SysUserDTO;
import com.obeast.business.entity.SysMenuEntity;
import com.obeast.business.entity.SysRoleEntity;
import com.obeast.business.entity.SysUserEntity;
import com.obeast.business.vo.OAuth2PasswordVo;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.*;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.exception.BendanException;
import com.obeast.core.utils.CookieUtil;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.security.business.dao.SysUserDao;
import com.obeast.security.business.service.SysMenuService;
import com.obeast.security.business.service.SysRoleService;
import com.obeast.security.business.service.SysUserService;
import com.obeast.security.business.service.remote.OAuth2TokenEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

import javax.servlet.http.Cookie;
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

    private static final PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();


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
            Map<String, String> mapCookie = CookieUtil.getMapCookie(request.getCookies());
            String token = mapCookie.get(SysConstant.TOKEN);
            if (StrUtil.isNotEmpty(token)) {
                OAuth2Authorization oAuth2Authorization = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
                if (ObjectUtil.isNotEmpty(oAuth2Authorization)){
                    return CommonResult.success(SysConstant.LOGIN_SUCCESS);
                }
            }
            CommonResult<?> res = OAuth2TokenEndpoint.authPassword(header, oAuth2Params);
            JSONObject data = CommonResult.getOptionalData(res).map(JSONObject::new).orElseThrow(() -> {
                throw new BendanException(SysConstant.LOGIN_FAILED);
            });
            JSONObject oAuth2AccessTokenResponse = data.getJSONObject("oauth2AccessTokenResponse");
            String accessToken = getAccessToken(oAuth2AccessTokenResponse);
            Long accessTokenExpiresIn = getAccessTokenExpiresIn(data);
            Cookie cookie1 = new Cookie(SysConstant.TOKEN, accessToken);
            cookie1.setPath(SysConstant.COOKIE);
            cookie1.setMaxAge(Math.toIntExact(accessTokenExpiresIn));
            // TODO: 2022/12/13 设置httpOnly如果项目是部署在一个单独的服务器上
            response.addCookie(cookie1);
            return CommonResult.success(SysConstant.LOGIN_SUCCESS);

        } catch (Exception e) {
            log.error(SysConstant.LOGIN_FAILED + "  ", e);
            return CommonResult.error(WebResultEnum.FAILURE);
        }
    }

    /**
     * Description: 获取过期时间
     * @author wxl
     * Date: 2022/12/14 10:02
     * @param data  data
     * @return java.lang.Long
     */
    private Long getAccessTokenExpiresIn(JSONObject data) {
        return data.getLong("accessTokenExpiresIn");
    }


    /**
     * Description: 获取Access token
     *
     * @param jsonObject jsonObject
     * @return java.lang.String
     * @author wxl
     * Date: 2022/12/13 14:35
     */
    private String getAccessToken(JSONObject jsonObject) {
        JSONObject accessToken = jsonObject.getJSONObject("accessToken");
        return  accessToken.getStr("tokenValue");
    }


    @Override
    public Boolean logout(HttpServletRequest request) {
        Map<String, String> mapCookie = CookieUtil.getMapCookie(request.getCookies());
        String authorization = mapCookie.get(SysConstant.TOKEN);
        if (authorization == null) {
            return Boolean.TRUE;
        }
        String token = authorization.replace(OAuth2AccessToken.TokenType.BEARER.getValue(), StrUtil.EMPTY).trim();
        OAuth2Authorization oAuth2Authorization = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (oAuth2Authorization == null) {
            return Boolean.TRUE;
        } else {
            oAuth2AuthorizationService.remove(oAuth2Authorization);
        }
        return Boolean.FALSE;
    }

    @Override
    public PageObjects<SysUserEntity> queryPage(JSONObject params) {
        String orderField = params.getStr("orderField");
        Assert.notNull(orderField, "key cannot be null");
        QueryWrapper<SysUserEntity> queryWrapper = Wrappers.query();
        IPage<SysUserEntity> page = this.page(
                new PageQueryUtils<SysUserEntity>().getPage(params, orderField, false),
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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean createUser(SysUserDTO sysUserDto) {
        sysUserDto.setUpdateId(sysUserDto.getCreateId());
        /*保存用户*/
        Boolean addUser = this.addUser(sysUserDto);
        Long userId = sysUserDto.getId();
        if (addUser) {
            List<Long> roleIds = sysUserDto.getRoleIds();
            Boolean addRelation = sysRoleService.addRoleUserRels(userId, roleIds);
            Assert.isTrue(addRelation, () -> {
                throw new BendanException("新增用户失败");
            });
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean addUser(SysUserEntity sysUserEntity) {
        String username = sysUserEntity.getUsername();
        Assert.isTrue(!this.isUserExistByName(username), UserConstant.USER_EXIST);
        String password = sysUserEntity.getPassword();
        String encodePassword = ENCODER.encode(password);
        sysUserEntity.setPassword(encodePassword);
        return this.save(sysUserEntity);
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


