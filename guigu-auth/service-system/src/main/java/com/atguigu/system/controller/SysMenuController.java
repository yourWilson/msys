package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-11-17
 */
@Api(tags="菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //待测试
    //根据角色分配菜单
    //1.根据角色id获得 菜单列表和 角色-菜单列表(查找)
    //2.获得()
    @ApiOperation("待测试根据角色分配菜单")
    @GetMapping("toAssignMenuByRoleId/{roleId}")
    public Result toAssignMenuByRoleId(@PathVariable  String roleId){

        HashMap<String, Object> hm = new HashMap<>();
        hm = sysMenuService.getRoleMenuMap(roleId);

        return  Result.ok(hm);
    }

    //该方法将前端传来的 角色修改菜单信息 进行保存

    @ApiOperation("根据角色修改分配菜单")
    @PostMapping("doAssignMenuByRoleId")
    public Result doAssignMenuByRoleId(@RequestBody AssginMenuVo vo){

        sysMenuService.doAssignMenu(vo);
        return Result.ok();
    }

    @ApiOperation("根据角色分配菜单")
    @GetMapping("toAssignMenuRoleId/{roleId}")
    public Result toAssignMenuRoleId(@PathVariable  String roleId){
        List<SysMenu> list = new ArrayList<>();
        list = sysMenuService.findRoleMenuList(roleId);
        System.out.println("=======list=========");
        System.out.println(list);
        return Result.ok(list);
    }

    //菜单列表(树形)
    @ApiOperation("菜单列表")
    @GetMapping("findNodes")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    //添加菜单
    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    //根据id查询
    @ApiOperation("根据id查询")
    @GetMapping("findNode/{id}")
    public Result findNode(@PathVariable String id){
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    //修改菜单
    @ApiOperation("修改菜单")
    @PostMapping("update")
    public Result update(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    //删除菜单
    @ApiOperation("删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

}
