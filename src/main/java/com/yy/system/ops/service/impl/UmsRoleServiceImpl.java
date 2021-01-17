package com.yy.system.ops.service.impl;

import com.yy.system.ops.entity.UmsMenu;
import com.yy.system.ops.entity.UmsPermission;
import com.yy.system.ops.entity.UmsResource;
import com.yy.system.ops.entity.UmsRole;
import com.yy.system.ops.mapper.UmsRoleMapper;
import com.yy.system.ops.service.IUmsRoleService;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author tengyong
 * @since 2021-01-16
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements IUmsRoleService {

    @Override
    public int create(UmsRole role) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(Long id, UmsRole role) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(List<Long> ids) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long roleId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int updatePermission(Long roleId, List<Long> permissionIds) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<UmsRole> list() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        // TODO Auto-generated method stub
        return 0;
    }

}
