package com.atguigu.system.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //查询所有记录
    @Test
    public void findAll(){
        List<SysRole> list = sysRoleMapper.selectList(null);
        for (SysRole sysRole: list) {
            System.out.println(sysRole);
        }
    }
    //添加操作
    @Test
    public void addSysRole(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色");
        sysRole.setRoleCode("测试");
        int insert = sysRoleMapper.insert(sysRole);
        System.out.println(insert>0?"插入成功":"插入失败");
    }

    //修改操作
    @Test
    public void updateSysRole(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色改");
        sysRole.setId("1590766229920972801");
        int i = sysRoleMapper.updateById(sysRole);
        System.out.println(i>0?"修改成功":"修改失败");
    }

    @Test
    //删除操作
    public void deleteSysRole(){
        int i = sysRoleMapper.deleteById(9);
        System.out.println(i>0?"删除成功":"删除失败");
    }

    @Test
    //批量删除
    public void deleteBatch(){
        int i = sysRoleMapper.deleteBatchIds(Arrays.asList(8,9));
        System.out.println(i>0?"删除"+i+"条记录成功":"删除失败");
    }


}
