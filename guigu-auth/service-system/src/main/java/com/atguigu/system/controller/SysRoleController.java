package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.AssginRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    //查询所有
    //访问路径： http://localhost:8800/admin/system/sysRole/findAll
    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("/findAll")
    public Result findAll(){
        try{
            String len =null;
            len.equals("1");
        }catch (Exception e){
            throw new GuiguException(2001,"出现自定义异常");
        }
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    //逻辑删除
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("remove/{id}")
    public Result deleteById(@PathVariable String id){
        System.out.println("111"+id);
        boolean isSuccess = sysRoleService.removeById(id);
        if(isSuccess){
            return Result.ok();
        }
        else{
            return Result.fail();
        }
    }

    //分页查询

    /**
     *
     * @param page  当前页
     * @param limit 每页记录数
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("{page}/{limit}")
    public Result findPageQueryRole(@PathVariable Long page,
                                    @PathVariable Long limit,
                                    SysRoleQueryVo sysRoleQueryVo){
        //创建page对象
        Page<SysRole> pageParam =new Page<>(page,limit);
        //调用service方法
        IPage<SysRole> pageModel= sysRoleService.selectPage(pageParam,sysRoleQueryVo);
        //返回
        return Result.ok(pageModel);
    }

    //@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
    // 而最常用的使用请求体传参的无疑是POST请求了，所以使用@RequestBody接收数据时，一般都用POST方式进行提交。
    // 在后端的同一个接收方法里，@RequestBody与@RequestParam()可以同时使用，@RequestBody最多只能有一个，
    // 而@RequestParam()可以有多个。

    //添加用户
    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result addSysRole(@RequestBody SysRole sysRole){
        boolean isSave = sysRoleService.save(sysRole);
        if(isSave){
            return Result.ok(sysRole);
        }
        else {
            return Result.fail();
        }
    }
    //根据id查询用户
    @ApiOperation("id查询")
    @PostMapping("findSysRoleById/{id}")
    public Result findSysRoleById(@PathVariable String id){
        System.out.println("可能会出现的问题"+id);
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    //修改用户
    @ApiOperation("修改用户")
    @PostMapping("update")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        System.out.println("可能会出现的问题"+sysRole);
        boolean isUpdate = sysRoleService.updateById(sysRole);
        if(isUpdate){
            return Result.ok(sysRole);
        }
        else {
            return Result.fail();
        }
    }

    //批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("BatchRemove")
    public Result BatchRemoveSysRole(@RequestBody List<Long> ids){
        boolean isSuccess = sysRoleService.removeByIds(ids);
        if(isSuccess){
            return Result.ok();
        }
        else{
            return Result.fail();
        }
    }

    //先获取用户id
    //再
    @ApiOperation("根据用户获取角色数据")
    @GetMapping("toAssign/{userId}")
    public Result toAssign(@PathVariable String userId){
        Map<String,Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }

    //重新分配角色，保存新分配角色
    @ApiOperation("用户重新分配角色")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }


}
