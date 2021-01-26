package com.yy.system.ops.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.yy.common.api.CommonResult;
import com.yy.common.api.ResultCode;
import com.yy.common.constant.AuthConstant;
import com.yy.common.exception.Asserts;
import com.yy.system.feign.AuthService;
import com.yy.system.ops.dto.UmsAdminCondition;
import com.yy.system.ops.dto.UmsAdminParam;
import com.yy.system.ops.dto.UpdateAdminPasswordParam;
import com.yy.system.ops.dto.UserDto;
import com.yy.system.ops.entity.UmsAdmin;
import com.yy.system.ops.entity.UmsAdminLoginLog;
import com.yy.system.ops.entity.UmsAdminPermissionRelation;
import com.yy.system.ops.entity.UmsAdminRoleRelation;
import com.yy.system.ops.entity.UmsPermission;
import com.yy.system.ops.entity.UmsResource;
import com.yy.system.ops.entity.UmsRole;
import com.yy.system.ops.mapper.UmsAdminLoginLogMapper;
import com.yy.system.ops.mapper.UmsAdminMapper;
import com.yy.system.ops.mapper.UmsAdminPermissionRelationMapper;
import com.yy.system.ops.mapper.UmsAdminRoleRelationMapper;
import com.yy.system.ops.service.IUmsAdminPermissionRelationService;
import com.yy.system.ops.service.IUmsAdminRoleRelationService;
import com.yy.system.ops.service.IUmsAdminService;
import com.yy.system.ops.service.UmsAdminCacheService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author tengyong
 * @since 2021-01-14
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService {

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private UmsAdminPermissionRelationMapper adminPermissionRelationMapper;

    @Autowired
    private IUmsAdminRoleRelationService adminRoleRelationService;

    @Autowired
    private IUmsAdminPermissionRelationService adminPermissionRelationsService;

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Autowired
    private AuthService authService;

    @Autowired
    private HttpServletRequest request;

    private Wrapper<UmsAdmin> getQueryWrapper(UmsAdminCondition condition) {
        LambdaQueryWrapper<UmsAdmin> query = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(condition.getUsername())) {
            query.eq(UmsAdmin::getUsername, condition.getUsername());
        }
        if (StringUtils.isNotEmpty(condition.getNickName())) {
            query.like(UmsAdmin::getNickName, condition.getNickName());
        }
        return query;
    }

    public Integer getCount(UmsAdminCondition condition) {
        return adminMapper.selectCount(getQueryWrapper(condition));
    }

    @Override
    public UmsAdmin registerOrUpdate(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        // 是否判断有相同用户名的用户
        Boolean judgeUsername = true;
        if (umsAdmin.getId() != null) {
            UmsAdmin oldUmsAdmin = getById(umsAdminParam.getId());
            if (oldUmsAdmin.getUsername().equals(umsAdmin.getUsername())) {
                judgeUsername = false;
            }
        }
        if (judgeUsername) {
            UmsAdmin oldUmsAdmin = getAdminByUsername(umsAdminParam.getUsername());
            // 添加
            if (oldUmsAdmin != null) {
                Asserts.fail("该用户名已经存在");
            }
        }
        // 将密码进行加密操作
        String encodePassword = BCrypt.hashpw(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        saveOrUpdate(umsAdmin);
        return umsAdmin;
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(UmsAdmin.class).eq(UmsAdmin::getUsername, username), false);
    }

    @Override
    public CommonResult login(String username, String password) {
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            Asserts.fail("用户名或密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret", "123456");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        CommonResult restResult = authService.getAccessToken(params);
        if (ResultCode.SUCCESS.getCode() == restResult.getCode() && restResult.getData() != null) {
            insertLoginLog(username);
        }
        return restResult;
    }

    /**
     * 添加登录记录
     * 
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin == null) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }

    @Override
    public UmsAdmin getItem(Long id) {
        return adminMapper.selectById(id);
    }

    @Override
    public List<UmsAdmin> list(UmsAdminCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        return adminMapper.selectList(getQueryWrapper(condition));
    }

    @Override
    public int delete(Long id) {
        return adminMapper.deleteById(id);
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        // 先删除原来的关系
        Map<String, Object> map = new HashMap<>();
        map.put("admin_id", adminId);
        adminRoleRelationMapper.deleteByMap(map);

        // 建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationService.saveBatch(list);
        }
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return adminRoleRelationMapper.getRoleListByAdminId(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        return adminRoleRelationMapper.getResourceByAdminId(adminId);
    }

    @Override
    public int updatePermission(Long adminId, List<Long> permissionIds) {
        // 删除原所有权限关系
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("admin_id", adminId);
        adminPermissionRelationMapper.deleteByMap(columnMap);
        // 获取用户所有角色权限
        List<UmsPermission> permissionList = adminRoleRelationMapper.getPermissionListByAdminId(adminId);
        List<Long> rolePermissionList = permissionList.stream().map(UmsPermission::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<UmsAdminPermissionRelation> relationList = new ArrayList<>();
            // 筛选出+权限
            List<Long> addPermissionIdList = permissionIds.stream()
                    .filter(permissionId -> !rolePermissionList.contains(permissionId)).collect(Collectors.toList());
            // 筛选出-权限
            List<Long> subPermissionIdList = rolePermissionList.stream()
                    .filter(permissionId -> !permissionIds.contains(permissionId)).collect(Collectors.toList());
            // 插入+-权限关系
            relationList.addAll(convert(adminId, 1, addPermissionIdList));
            relationList.addAll(convert(adminId, -1, subPermissionIdList));
            adminPermissionRelationsService.saveBatch(relationList);
            return relationList.size();
        }
        return 0;
    }

    /**
     * 将+-权限关系转化为对象
     */
    private List<UmsAdminPermissionRelation> convert(Long adminId, Integer type, List<Long> permissionIdList) {
        List<UmsAdminPermissionRelation> relationList = permissionIdList.stream().map(permissionId -> {
            UmsAdminPermissionRelation relation = new UmsAdminPermissionRelation();
            relation.setAdminId(adminId);
            relation.setType(type);
            relation.setPermissionId(permissionId);
            return relation;
        }).collect(Collectors.toList());
        return relationList;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationMapper.getPermissionListByAdminId(adminId);
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if (StrUtil.isEmpty(param.getUsername()) || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }
        List<UmsAdmin> adminList = adminMapper
                .selectList(Wrappers.lambdaQuery(UmsAdmin.class).eq(UmsAdmin::getUsername, param.getUsername()));
        if (CollUtil.isEmpty(adminList)) {
            return -2;
        }
        UmsAdmin umsAdmin = adminList.get(0);
        if (!BCrypt.checkpw(param.getOldPassword(), umsAdmin.getPassword())) {
            return -3;
        }
        umsAdmin.setPassword(BCrypt.hashpw(param.getNewPassword()));
        adminMapper.updateById(umsAdmin);
        return 1;
    }

    @Override
    public UserDto loadUserByUsername(String username) {
        // 获取用户信息
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsRole> roleList = getRoleList(admin.getId());
            UserDto userDTO = new UserDto();
            BeanUtils.copyProperties(admin, userDTO);
            if (CollUtil.isNotEmpty(roleList)) {
                List<String> roleStrList = roleList.stream().map(item -> item.getId() + "_" + item.getName())
                        .collect(Collectors.toList());
                userDTO.setRoles(roleStrList);
            }
            return userDTO;
        }
        return null;
    }

    @Override
    public UmsAdmin getCurrentAdmin() {
        String userStr = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
        if (StrUtil.isEmpty(userStr)) {
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        UserDto userDto = JSONObject.parseObject(userStr, UserDto.class);
        // UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
        UmsAdmin admin = adminCacheService.getAdmin(userDto.getId());
        if (admin != null) {
            return admin;
        } else {
            admin = adminMapper.selectById(userDto.getId());
            adminCacheService.setAdmin(admin);
            return admin;
        }

    }

    @Override
    public void setStatus(Long id, Integer status) {
        LambdaUpdateWrapper<UmsAdmin> updateWrapper = Wrappers.lambdaUpdate(UmsAdmin.class).eq(UmsAdmin::getId, id)
                .set(UmsAdmin::getStatus, status);
        adminMapper.update(null, updateWrapper);
    }

}
