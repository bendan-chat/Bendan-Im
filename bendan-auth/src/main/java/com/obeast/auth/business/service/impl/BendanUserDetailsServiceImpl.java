package com.obeast.auth.business.service.impl;

import cn.hutool.core.stream.CollectorUtil;
import com.obeast.auth.business.BendanSecurityUser;
import com.obeast.business.entity.BendanSysUser;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.UserLoginConstant;
import com.obeast.auth.business.service.BendanUserDetailsService;
import com.obeast.auth.business.service.remote.BendanSysUserRemote;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BendanUserDetailsServiceImpl implements BendanUserDetailsService {

    private final BendanSysUserRemote bendanSysUserRemote;

    /**
     * 二级缓存
     * */
//    private final CacheManager cacheManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Cache cache = cacheManager.getCache(UserLoginConstant.USERINFO);
//        if (cache != null && cache.get(username) != null) {
//            return (BendanSecurityUser) cache.get(username).get();
//        }
        CommonResult<UserInfo> result = bendanSysUserRemote.getUserinfo(username);
        UserDetails userDetails = getUserDetails(result);
//        if (cache != null){
//            cache.put(username, userDetails);
//        }
        return userDetails;
    }

    private UserDetails getUserDetails(CommonResult<UserInfo> result){
        UserInfo userInfo = CommonResult.getOptionalData(result).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        // TODO: 2022/11/30 资源控制
        BendanSysUser sysUser = userInfo.getBendanSysUser();
        String[] roles = userInfo.getBendanSysRoles().stream().map(item -> UserLoginConstant.ROLE + item.getName()).toArray(String[]::new);
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList(roles));
        return new BendanSecurityUser(
                sysUser.getId(),
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getEmail(),
                true,
                true,
                true,
                sysUser.getLockStatus().equals(UserLoginConstant.NORMAL_STATUS),
                authorities
        );
    }
}
