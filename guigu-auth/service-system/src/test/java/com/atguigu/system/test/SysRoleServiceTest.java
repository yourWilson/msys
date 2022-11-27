package com.atguigu.system.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.service.impl.SysRoleServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Test
    public void findAll(){
        List<SysRole> list = sysRoleService.list();
        for (SysRole sysRole:list) {
            System.out.println(sysRole);
        }
    }

    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试1");
        sysRole.setRoleCode("test1");
        boolean b = sysRoleService.save(sysRole);
        System.out.println(b==true?"插入成功":"插入失败");
    }

    @Test
    public void update(){
        SysRole sysRole = new SysRole();
        sysRole.setId("10");
        sysRole.setRoleName("测试2");
        sysRole.setRoleCode("test2");
        boolean b = sysRoleService.updateById(sysRole);
        System.out.println(b==true?"修改成功":"修改失败");
    }

    @Test
    public void remove(){
        boolean b = sysRoleService.removeById(10);
        System.out.println(b==true?"删除成功":"删除失败");
    }

    @Test
    public void select(){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name", "系统管理员");
        List<SysRole> list =sysRoleService.list(queryWrapper);
        for (SysRole sysRole : list) {
            System.out.println(sysRole);
        }

    }


}
