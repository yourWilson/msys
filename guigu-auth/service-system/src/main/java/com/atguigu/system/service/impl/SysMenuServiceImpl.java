package com.atguigu.system.service.impl;


import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.utils.MenuHelper;
import com.atguigu.system.utils.RouterHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-17
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
       //获取所有菜单
        List<SysMenu> sysMenuList =baseMapper.selectList(null);
        //所有菜单数据转换要求数据格式
        List<SysMenu> resultList = MenuHelper.bulidTree(sysMenuList);
        return resultList;
    }

    @Override
    public void removeMenuById(String id) {
        QueryWrapper<SysMenu> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);

        int count = baseMapper.selectCount(queryWrapper);

        if(count>0)
           throw new GuiguException(2001,"不能直接删除父节点！");

       //调用删除方法
        baseMapper.deleteById(id);
    }

    //根据角色分配菜单
    //1.根据角色id获得 菜单列表和 角色-菜单列表(查找)
    @Override
    public HashMap<String, Object> getRoleMenuMap(String roleId) {
        HashMap<String, Object> roleMenuHashMap = new HashMap<>();

        //获得菜单的树形列表

        //获得 roleId对应的菜单 id list
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);

        ArrayList<Object> MenuIdList = new ArrayList<>();
        List<SysRoleMenu> lists = sysRoleMenuMapper.selectList(queryWrapper);

        for(SysRoleMenu sysRoleMenu:lists){
            String menuId = sysRoleMenu.getMenuId();
            MenuIdList.add(menuId);
        }
        roleMenuHashMap.put("MenuIdList",MenuIdList);

        return  roleMenuHashMap;

    }

    //保存分配好的菜单数据

    @Override
    public void doAssignMenu(AssginMenuVo vo) {
        //删除先前的role_id 在role-menu表中 的菜单信息
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",vo.getRoleId());
        sysRoleMenuMapper.delete(queryWrapper);
        int i=1;
        //给该角色 添加分配好的菜单信息
        //传来的vo有 分配好的菜单id list信息
        for(String menuId:vo.getMenuIdList()){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(vo.getRoleId());
            System.out.println("====================");
            System.out.println((i++)+":  "+sysRoleMenu);
            sysRoleMenuMapper.insert(sysRoleMenu);
        }

    }

    @Override
    public List<SysMenu> findRoleMenuList(String roleId) {

        //获取可用的menuList
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        List<SysMenu> sysMenuList = baseMapper.selectList(queryWrapper);

        QueryWrapper<SysRoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(roleMenuQueryWrapper);
        System.out.println("=================sysRoleMenuList=================");
        System.out.println(sysRoleMenuList);

        ArrayList<String> strings = new ArrayList<>();
        for(SysRoleMenu sysRoleMenu:sysRoleMenuList){
            String roleId1 = sysRoleMenu.getMenuId();
            strings.add(roleId1);
        }
        System.out.println("=================strings=================");
        System.out.println(strings);

        //设置 sysMenuList 的每个菜单元素 选择状态
        for (SysMenu sysMenu:sysMenuList){
            if(strings.contains(sysMenu.getId())){
                sysMenu.setSelect(true);
            }else{
                sysMenu.setSelect(false);
            }
        }
        System.out.println("=================sysMenuList=================");
        System.out.println(sysMenuList);
        List<SysMenu> sysMenus =MenuHelper.bulidTree(sysMenuList);
        return sysMenus;
    }

    @Override
    public List<RouterVo> getUserMenuList(String id) {
        //admin 是超级管理员，操作所有内容
        List<SysMenu> sysMenuList =null;
        //判断userid值是1代表 超级管理员，查询所有权限数据
        if("1".equals(id)){
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1);
            wrapper.orderByAsc("sort_value");

            sysMenuList = baseMapper.selectList(wrapper);
        }else{
            //如果不是1,其他类型用户，查询这个用户权限
            sysMenuList = baseMapper.findMenuListUserId(id);
        }
        //构建是树形结构
        List<SysMenu> sysMenuTreeList = MenuHelper.bulidTree(sysMenuList);

        //转换成前端路由要求 格式数据
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);

        return routerVoList;
    }

    @Override
    public List<String> getUserButtonList(String userId) {
        List<SysMenu> sysMenuList =null;
        //判断是否管理员
        if("1".equals(userId)){
            sysMenuList =
                    baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status",1));
        }else{
            sysMenuList =baseMapper.findMenuListUserId(userId);
        }
        //遍历sysMenuList
        List<String> permissionList =new ArrayList<>();
        for(SysMenu sysMenu:sysMenuList){
            if(sysMenu.getType()==2){
                String perms = sysMenu.getPerms();
                permissionList.add(perms);
            }
        }

        return permissionList;
    }
}
