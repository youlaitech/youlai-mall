package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.model.KeyValue;
import com.youlai.system.enums.MenuTypeEnum;
import com.youlai.common.enums.StatusEnum;
import com.youlai.system.converter.MenuConverter;
import com.youlai.system.mapper.SysMenuMapper;
import com.youlai.system.model.bo.RouteBO;
import com.youlai.system.model.entity.SysMenu;
import com.youlai.system.model.form.MenuForm;
import com.youlai.system.model.query.MenuQuery;
import com.youlai.system.model.vo.MenuVO;
import com.youlai.common.web.model.Option;
import com.youlai.system.model.vo.RouteVO;
import com.youlai.system.service.SysMenuService;
import com.youlai.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单业务实现类
 *
 * @author Ray
 * @since 2020/11/06
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final MenuConverter menuConverter;
    private final SysRoleMenuService roleMenuService;

    /**
     * 菜单列表
     *
     * @param queryParams {@link MenuQuery}
     */
    @Override
    public List<MenuVO> listMenus(MenuQuery queryParams) {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotBlank(queryParams.getKeywords()), SysMenu::getName, queryParams.getKeywords())
                .orderByAsc(SysMenu::getSort)
        );

        Set<Long> parentIds = menus.stream()
                .map(SysMenu::getParentId)
                .collect(Collectors.toSet());
        Set<Long> menuIds = menus.stream()
                .map(SysMenu::getId)
                .collect(Collectors.toSet());

        // 获取根节点ID
        List<Long> rootIds = parentIds.stream()
                .filter(id -> !menuIds.contains(id))
                .toList();

        // 使用递归函数来构建菜单树

        return rootIds.stream()
                .flatMap(rootId -> buildMenuTree(rootId, menus).stream())
                .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private List<MenuVO> buildMenuTree(Long parentId, List<SysMenu> menuList) {
        return CollectionUtil.emptyIfNull(menuList)
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(entity -> {
                    MenuVO menuVO = menuConverter.entity2Vo(entity);
                    List<MenuVO> children = buildMenuTree(entity.getId(), menuList);
                    menuVO.setChildren(children);
                    return menuVO;
                }).toList();
    }

    /**
     * 菜单下拉数据
     */
    @Override
    public List<Option> listMenuOptions() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        return buildMenuOptions(GlobalConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return 菜单下拉列表
     */
    private List<Option> buildMenuOptions(Long parentId, List<SysMenu> menuList) {
        List<Option> menuOptions = new ArrayList<>();

        for (SysMenu menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                Option option = new Option(menu.getId(), menu.getName());
                List<Option> subMenuOptions = buildMenuOptions(menu.getId(), menuList);
                if (!subMenuOptions.isEmpty()) {
                    option.setChildren(subMenuOptions);
                }
                menuOptions.add(option);
            }
        }

        return menuOptions;
    }

    /**
     * 路由列表
     */
    @Override
    // @Cacheable(cacheNames = "menu", key = "'routes'")
    public List<RouteVO> listRoutes() {
        List<RouteBO> menuList = this.baseMapper.listRoutes();
        return buildRoutes(GlobalConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return 路由层级列表
     */
    private List<RouteVO> buildRoutes(Long parentId, List<RouteBO> menuList) {
        List<RouteVO> routeList = new ArrayList<>();

        for (RouteBO menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                RouteVO routeVO = toRouteVo(menu);
                List<RouteVO> children = buildRoutes(menu.getId(), menuList);
                if (!children.isEmpty()) {
                    routeVO.setChildren(children);
                }
                routeList.add(routeVO);
            }
        }

        return routeList;
    }

    /**
     * 根据RouteBO创建RouteVO
     */
    private RouteVO toRouteVo(RouteBO routeBO) {
        RouteVO routeVO = new RouteVO();
        // 路由名称 将path转换为驼峰命名  user => User
        String routeName = StringUtils.capitalize(StrUtil.toCamelCase(routeBO.getPath(), '-'));
        // 根据name路由跳转 this.$router.push({name:xxx})
        routeVO.setName(routeName);
        // 根据path路由跳转 this.$router.push({path:xxx})
        routeVO.setPath(routeBO.getPath());
        routeVO.setRedirect(routeBO.getRedirect());
        routeVO.setComponent(routeBO.getComponent());

        RouteVO.Meta meta = new RouteVO.Meta();
        meta.setTitle(routeBO.getName());
        meta.setIcon(routeBO.getIcon());
        meta.setRoles(routeBO.getRoles());
        meta.setHidden(StatusEnum.DISABLE.getValue().equals(routeBO.getVisible()));
        // 【菜单】是否开启页面缓存
        if (MenuTypeEnum.MENU.equals(routeBO.getType())
                && ObjectUtil.equals(routeBO.getKeepAlive(), 1)) {
            meta.setKeepAlive(true);
        }
        meta.setAlwaysShow(ObjectUtil.equals(routeBO.getAlwaysShow(), 1));

        String paramsJson = routeBO.getParams();
        // 将 JSON 字符串转换为 Map<String, String>
        if (StrUtil.isNotBlank(paramsJson)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, String> paramMap = objectMapper.readValue(paramsJson, new TypeReference<>() {});
                meta.setParams(paramMap);
            } catch (Exception e) {
                throw new RuntimeException("解析参数失败", e);
            }
        }

        routeVO.setMeta(meta);
        return routeVO;
    }

    /**
     * 保存菜单
     */
    @Override
    public boolean saveMenu(MenuForm menuForm) {
        MenuTypeEnum menuType = menuForm.getType();

        if (menuType == MenuTypeEnum.CATALOG) {  // 如果是外链
            String path = menuForm.getPath();
            if (menuForm.getParentId() == 0 && !path.startsWith("/")) {
                menuForm.setPath("/" + path); // 一级目录需以 / 开头
            }
            menuForm.setComponent("Layout");
        } else if (menuType == MenuTypeEnum.EXTLINK) {   // 如果是目录
            menuForm.setComponent(null);
        }

        SysMenu entity = menuConverter.toEntity(menuForm);
        String treePath = generateMenuTreePath(menuForm.getParentId());
        entity.setTreePath(treePath);

        List<KeyValue> params = menuForm.getParams();
        // 路由参数 [{key:"id",value:"1"}，{key:"name",value:"张三"}] 转换为 [{"id":"1"},{"name":"张三"}]
        if (CollectionUtil.isNotEmpty(params)) {
            entity.setParams(JSONUtil.toJsonStr(params.stream()
                    .collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue))));
        }else{
            entity.setParams(null);
        }

        boolean result = this.saveOrUpdate(entity);
        if (result) {
            // 编辑刷新角色权限缓存
            if (menuForm.getId() != null) {
                roleMenuService.refreshRolePermsCache();
            }
        }
        return result;
    }

    /**
     * 部门路径生成
     *
     * @param parentId 父ID
     * @return 父节点路径以英文逗号(, )分割，eg: 1,2,3
     */
    private String generateMenuTreePath(Long parentId) {
        if (GlobalConstants.ROOT_NODE_ID.equals(parentId)) {
            return String.valueOf(parentId);
        } else {
            SysMenu parent = this.getById(parentId);
            return parent != null ? parent.getTreePath() + "," + parent.getId() : null;
        }
    }


    /**
     * 修改菜单显示状态
     *
     * @param menuId  菜单ID
     * @param visible 是否显示(1->显示；2->隐藏)
     * @return 是否成功
     */
    @Override
    public boolean updateMenuVisible(Long menuId, Integer visible) {
        return this.update(new LambdaUpdateWrapper<SysMenu>()
                .eq(SysMenu::getId, menuId)
                .set(SysMenu::getVisible, visible)
        );
    }

    /**
     * 获取菜单表单数据
     *
     * @param id 菜单ID
     * @return {@link MenuForm}
     */
    @Override
    public MenuForm getMenuForm(Long id) {
        SysMenu entity = this.getById(id);
        Assert.isTrue(entity != null, "菜单不存在");
        MenuForm formData = menuConverter.convertToForm(entity);
        // 路由参数字符串 {"id":"1","name":"张三"} 转换为 [{key:"id", value:"1"}, {key:"name", value:"张三"}]
        String params = entity.getParams();
        if (StrUtil.isNotBlank(params)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 解析 JSON 字符串为 Map<String, String>
                Map<String, String> paramMap = objectMapper.readValue(params, new TypeReference<>() {});

                // 转换为 List<KeyValue> 格式 [{key:"id", value:"1"}, {key:"name", value:"张三"}]
                List<KeyValue> transformedList = paramMap.entrySet().stream()
                        .map(entry -> new KeyValue(entry.getKey(), entry.getValue()))
                        .toList();

                // 将转换后的列表存入 MenuForm
                formData.setParams(transformedList);
            } catch (Exception e) {
                throw new RuntimeException("解析参数失败", e);
            }
        }

        return formData;
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return 是否成功
     */
    @Override
    public boolean deleteMenu(Long id) {
        boolean result = this.remove(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getId, id)
                .or()
                .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", id));

        // 刷新角色权限缓存
        if (result) {
            roleMenuService.refreshRolePermsCache();
        }
        return result;
    }


}
