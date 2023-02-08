package com.obeast.security.resource;

import cn.hutool.core.util.ArrayUtil;
import com.obeast.business.entity.SysUserEntity;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.constant.CacheConstant;
import com.obeast.core.constant.SysConstant;
import com.obeast.core.constant.UserConstant;
import com.obeast.security.business.domain.BendanSecurityUser;
import com.obeast.security.business.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class BendanUserDetailsServiceImpl implements BendanUserDetailsService {

    private final SysUserService sysUserService;

    private final RedisTemplate<String, Object> redisTemplate;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        redisTemplate.setValueSerializer(RedisSerializer.java());
        UserDetails detailsCache = (UserDetails) redisTemplate.opsForValue().get(UserConstant.formatKey(username));
        if (detailsCache != null) {
            return detailsCache;
        }
        UserDetails userDetails = getUserDetails(username);
//        设置缓存
        redisTemplate.opsForValue().set(
                UserConstant.formatKey(username),
                userDetails,
                24,
                SysConstant.REDIS_UNIT
        );
        return userDetails;
    }

    /**
     * Description: 获取用户详情
     * @author wxl
     * Date: 2023/1/10 9:54
     * @param username  username
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    private UserDetails getUserDetails(String username) throws LoginException {
        UserInfo userInfo = Optional.ofNullable(sysUserService.findUserInfo(username)).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        SysUserEntity sysUserEntity = userInfo.getSysUserEntity();
        HashSet<String> auth = new HashSet<>();
        if (ArrayUtil.isNotEmpty(userInfo.getSysRoleEntities())){
            /*角色*/
            userInfo.getSysRoleEntities().forEach(role -> auth.add(UserConstant.ROLE + role.getName()));

            /*菜单资源*/
            auth.addAll(Arrays.asList(userInfo.getPurviewNames()));
        }
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList(auth.toArray(new String[0])));
        return new BendanSecurityUser(
                sysUserEntity.getId(),
                sysUserEntity.getUsername(),
                sysUserEntity.getPassword(),
                sysUserEntity.getEmail(),
                Boolean.TRUE,
                Boolean.TRUE,
                Boolean.TRUE,
                !sysUserEntity.getStatus().equals(UserConstant.NORMAL_STATUS),
                authorities
        );
    }
}
