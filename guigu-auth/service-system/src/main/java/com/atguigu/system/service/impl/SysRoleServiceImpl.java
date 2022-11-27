package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.model.vo.AssginRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.mapper.SysRoleMapper;
import com.atguigu.system.mapper.SysUserRoleMapper;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    //条件分页查询
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel =baseMapper.selectPage(pageParam,sysRoleQueryVo);
        return pageModel;
    }
    //获取用户的角色数据
    @Override
    public Map<String, Object> getRolesByUserId(String userId) {
        List<SysRole> roleList = baseMapper.selectList(null);
        QueryWrapper<SysUserRole> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<SysUserRole> userRoleList = sysUserRoleMapper.selectList(queryWrapper);

        List<String> longs = new ArrayList<>();
        for (SysUserRole sysUserRole: userRoleList) {
            String roleId = sysUserRole.getRoleId();
            longs.add(roleId);
        }
        HashMap<String, Object> hp = new HashMap<>();
        hp.put("roleList",roleList);//所有角色
        hp.put("longs",longs);//用户分配角色id集合
        return hp;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除之前分配角色
        QueryWrapper<SysUserRole> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("user_id",assginRoleVo.getUserId());

        sysUserRoleMapper.delete(queryWrapper);

        //获取所有角色id,添加角色用户关系表
        //角色id列表
        List<String> roleIdList = assginRoleVo.getRoleIdList();
        for(String roleId:roleIdList){
            //添加角色用户表
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(assginRoleVo.getUserId());
            userRole.setRoleId(roleId);
            sysUserRoleMapper.insert(userRole);
        }

    }
}
