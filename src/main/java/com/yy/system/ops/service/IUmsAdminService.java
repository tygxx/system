package com.yy.system.ops.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.system.api.CommonResult;
import com.yy.system.ops.dto.UmsAdminCondition;
import com.yy.system.ops.dto.UmsAdminParam;
import com.yy.system.ops.dto.UpdateAdminPasswordParam;
import com.yy.system.ops.dto.UserDto;
import com.yy.system.ops.entity.UmsAdmin;
import com.yy.system.ops.entity.UmsPermission;
import com.yy.system.ops.entity.UmsResource;
import com.yy.system.ops.entity.UmsRole;

import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author tengyong
 * @since 2021-01-14
 */
public interface IUmsAdminService extends IService<UmsAdmin> {

    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    UmsAdmin registerOrUpdate(UmsAdminParam umsAdminParam);

    /**
     * 修改状态
     */
    void setStatus(Long id, Integer status);

    /**
     * 登录功能
     * 
     * @param username 用户名
     * @param password 密码
     * @return 调用认证中心返回结果
     */
    CommonResult login(String username, String password);

    /**
     * 根据用户id获取用户
     */
    UmsAdmin getItem(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<UmsAdmin> list(UmsAdminCondition condition);

    /**
     * 删除指定用户
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * 获取指定用户的可访问资源
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 修改用户的+-权限
     */
    @Transactional
    int updatePermission(Long adminId, List<Long> permissionIds);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Long adminId);

    /**
     * 修改密码
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDto loadUserByUsername(String username);

    /**
     * 获取当前登录后台用户
     */
    UmsAdmin getCurrentAdmin();

}
