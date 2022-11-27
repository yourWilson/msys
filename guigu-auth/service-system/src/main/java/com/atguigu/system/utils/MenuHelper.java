package com.atguigu.system.utils;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    //构建树形
    public static List<SysMenu> bulidTree(List<SysMenu> sysMenuList) {
        ArrayList<SysMenu> trees = new ArrayList<>();
        //遍历所有菜单集合
        for (SysMenu sysMenu:sysMenuList){
            //找到递归入口,parentId=0
            if(sysMenu.getParentId().longValue()==0){
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    //从根节点进行递归查询，查询字节点
    //判断 id=parentId 是否相同。如果相同是子节点，进行数据封装
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        //数据初始化
        sysMenu.setChildren(new ArrayList<SysMenu>());

        //遍历递归查找
        for(SysMenu it:treeNodes){
            //获得当前菜单id
//            String id = sysMenu.getId();
//            long cid = Long.parseLong(id);
//            //获取所有菜单parentid
//            Long parentId = it.getParentId();
            //比对
            if(Long.parseLong(sysMenu.getId())==it.getParentId().longValue()){
                if(sysMenu.getChildren()==null){
                    sysMenu.setChildren(new ArrayList<SysMenu>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;
    }
}
