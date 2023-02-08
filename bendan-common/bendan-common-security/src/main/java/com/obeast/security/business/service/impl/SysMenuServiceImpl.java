package com.obeast.security.business.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.business.dto.SysMenuDTO;
import com.obeast.core.constant.CacheConstant;
import com.obeast.core.constant.MenuConstant;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.exception.BendanException;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.business.entity.SysMenuEntity;
import com.obeast.security.business.dao.SysMenuDao;
import com.obeast.security.business.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 菜单(权限)表
 */
@Service("sysMenuService")
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity>
        implements SysMenuService {

    private final SysMenuDao sysMenuDao;


    @Override
    public Set<SysMenuEntity> getMenusByRoleIds(List<Long> roleIds) {
        if (!roleIds.isEmpty()) {
            Set<SysMenuEntity> menus = new HashSet<>();
            for (Long roleId : roleIds) {
                Set<SysMenuEntity> tempMenu = this.getMenusByRoleId(roleId);
                menus.addAll(tempMenu);
            }
            return menus;
        }
        return new HashSet<>();
    }

    @Cacheable(value = CacheConstant.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")
    @Override
    public Set<SysMenuEntity> getMenusByRoleId(Long roleId) {
        return sysMenuDao.listMenusByRoleId(roleId);
    }

    @Override
    public List<SysMenuEntity> treeByRoleId(List<Long> roleIds, Boolean isLazy) {
        Set<SysMenuEntity> menus;
        if (isLazy){
            /*加载所有*/
            menus = this.getMenusByRoleIds(roleIds);
        }else {
            /*加载路由*/
            menus = this.getMenusByRoleIds(roleIds)
                    .stream().filter(item -> item.getPath() != null).collect(Collectors.toSet());
        }
        return handlerMenusTree(menus);
    }

    /**
     * Description: 把 sysMenus 变成树
     *
     * @param sysMenuEntities sysMenus
     * @return java.util.List<com.worldintek.business.entity.SysMenu>
     * @author wxl
     * Date: 2022/12/13 10:33
     */
    private List<SysMenuEntity> handlerMenusTree(Set<SysMenuEntity> sysMenuEntities) {
        return sysMenuEntities
                .stream()
                .filter(menu ->
                        menu.getParentId().equals(MenuConstant.FATHER_ID))
                .peek(menu -> menu.setChildren(this.getChildren(menu, sysMenuEntities)))
                .sorted(Comparator.comparingInt(SysMenuEntity::getSort))
                .toList();
    }

    @Override
    public Long getIdByTitle(String title) throws NullPointerException {
        LambdaQueryWrapper<SysMenuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenuEntity::getName, title);
        SysMenuEntity sysMenuEntity = this.getOne(wrapper);
        if (sysMenuEntity != null) {
            return Optional.ofNullable(sysMenuEntity.getId()).orElseThrow(NullPointerException::new);
        }
        return null;
    }


    @Override
    public Boolean addRoleMenuRel(Long roleId, Long menuId) {
        Assert.notNull(roleId, "roleId cannot be null");
        Assert.notNull(menuId, "menuId cannot be null");
        return sysMenuDao.addRoleMenuRel(roleId, menuId);
    }

    @Override
    public Boolean isMenuExistById(Long menuId) {
        SysMenuEntity sysMenuEntity = this.getById(menuId);
        return sysMenuEntity != null;
    }

    @Override
    public Boolean addRoleMenuRels(List<Long> menuIds, Long roleId) {
        Assert.notNull(roleId, "roleId cannot be null");
        Boolean addRelation = Boolean.FALSE;
        if (ArrayUtil.isNotEmpty(menuIds)) {
            /*去重*/
            menuIds = menuIds.stream().distinct().toList();
            for (Long menuId : menuIds) {
                if (!this.isMenuExistById(menuId)) {
                    throw new BendanException(MenuConstant.NOT_FOUND);
                }
                addRelation = sysMenuDao.addRoleMenuRel(
                        roleId,
                        menuId
                );
            }
            return addRelation;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean delRelsByRoleId(Long roleId) {
        Assert.notNull(roleId, "roleId cannot be null");
        return sysMenuDao.delRelsByRoleId(roleId);
    }





    /**
     * Description: 递归拿到子孩子
     *
     * @param root menu
     * @param all  sysMenus
     * @return java.util.List<com.worldintek.business.entity.SysMenu>
     * @author wxl
     * Date: 2022/12/13 10:29
     */
    private List<SysMenuEntity> getChildren(SysMenuEntity root, Set<SysMenuEntity> all) {
        return all
                .stream()
                .filter(item -> item.getParentId().equals(root.getId()))
                .peek(child -> child.setChildren(this.getChildren(child, all)))
                .sorted(Comparator.comparingInt(SysMenuEntity::getSort))
                .toList();
    }

    @Override
    @CacheEvict(value = CacheConstant.MENU_DETAILS, allEntries = true)
    public void clearMenuCache() {

    }


    @Override
    public Boolean addMenus(List<SysMenuEntity> sysMenuEntities) {
        if (ArrayUtil.isNotEmpty(sysMenuEntities)) {
            return this.saveBatch(sysMenuEntities);
        }
        return Boolean.FALSE;
    }
//    ----------------------------default------------------------------------------

    @Override
    public PageObjects<SysMenuEntity> queryPage(JSONObject params) {
//        String key = params.getStr("orderField");
//        QueryWrapper<SysMenuEntity> queryWrapper = Wrappers.query();
//        IPage<SysMenuEntity> page = this.page(
//                new PageQueryUtils<SysMenuEntity>().getPage(params),
//                queryWrapper
//        );
//        return new PageQueryUtils<>().getPageObjects(page, SysMenuEntity.class);
        return null;
    }

    @Override
    public List<SysMenuEntity> queryByConditions() {
        QueryWrapper<SysMenuEntity> wrapper = Wrappers.query();
        return this.list(wrapper);
    }


    @Override
    public List<SysMenuEntity> queryAll() {
        return this.list();
    }


    @Override
    public SysMenuEntity queryById(Long id) {
        return this.getById(id);
    }

    @Override
    public Boolean replace(SysMenuDTO sysMenuDTO) {
        return this.updateById(sysMenuDTO);
    }

    @Override
    public Boolean replaceList(List<SysMenuEntity> data) {
        return this.updateBatchById(data);
    }

    @Override
    public Boolean deleteById(Long id) {
        List<SysMenuEntity> menuList = this.list(Wrappers.<SysMenuEntity>lambdaQuery().eq(SysMenuEntity::getParentId, id));
//        Assert.isTrue(CollUtil.isEmpty(menuList), );
        return this.removeById(id);
    }

}
