package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.common.utils.JwtHelper;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags="用户登录接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService ;
    //login
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){

        System.out.println(loginVo.getPassword());
        System.out.println(loginVo.getUsername());
        //根据username查询数据
//        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",loginVo.getUsername());
//
//        SysUser sysUser = sysUserService.getOne(queryWrapper);

        SysUser sysUser = sysUserService.findSysUser(loginVo.getUsername());
        System.out.println(sysUser);
        //如果查询为空
        if(sysUser==null){
            throw new GuiguException(20001,"用户不存在，请输入正确的用户名！");
        }
        //获得用户密码，并判断密码是否一致

        String encrypt = MD5.encrypt(loginVo.getPassword());
        System.out.println(encrypt);

        if(!sysUser.getPassword().equals(encrypt)){
            throw new GuiguException(20001,"密码错误！");

        }

        //判断用户是否可用
        if(sysUser.getStatus().intValue()==0){
            throw new GuiguException(20001,"用户当前不可用！");
        }

        //根据userid 和 username生成 token字符串，通过map返回

        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    @GetMapping("info")
    public Result info(HttpServletRequest request){

        //获取请求头token字符串
        String token =request.getHeader("token");

        //从token字符串获取用户id
        String username =JwtHelper.getUsername(token);

        //根据用户名称获取用户信息(基本信息和菜单权限 和按钮权限数据)
        Map<String,Object> map = sysUserService.getUserInfo(username);

        return Result.ok(map);
    }





//    @PostMapping("login/{username}/{password}")
//    public Result login(@PathVariable String username,@PathVariable String password){
//        System.out.println(username+password);
//        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",username);
//        queryWrapper.eq("password",password);
//        List list  = sysUserService.list(queryWrapper);
//        System.out.println(list);
//        if(!list.isEmpty()){
//            System.out.println("1");
//            return Result.ok().message("登录成功！");
//        }
//        else{
//            System.out.println("2");
//            return Result.fail().message("密码错误！");
//        }
//
//    }

}
