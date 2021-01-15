package com.yy.system.ops.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.system.ops.dto.UmsAdminCondition;
import com.yy.system.ops.dto.UmsAdminParam;
import com.yy.system.ops.entity.UmsAdmin;
import com.yy.system.ops.mapper.UmsAdminMapper;
import com.yy.system.ops.service.IUmsAdminService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        // 查询是否有相同用户名的用户
        UmsAdminCondition umsAdminCondition = new UmsAdminCondition();
        umsAdminCondition.setUsername(umsAdmin.getUsername());
        Integer count = getCount(umsAdminCondition);
        if (count > 0) {
            return null;
        }
        // 将密码进行加密操作
        String encodePassword = BCrypt.hashpw(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

}
