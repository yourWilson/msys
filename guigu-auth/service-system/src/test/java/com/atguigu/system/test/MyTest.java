package com.atguigu.system.test;

import com.atguigu.common.utils.JwtHelper;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MyTest {

    @Autowired
    SysUserService sysUserService;

    @Test
    public void test1(){
        long i =1;
        int is = sysUserService.updateStatus(i);
        System.out.println(is>0?"修改状态成功":"修改失败");
    }

    @Test
    public void test2(){
        Integer i = 1;
        System.out.println("1".getClass());
        System.out.println(i.getClass());
    }

    @Test
    public void test3(){

    }
}
