package com.yy.system.ops.service.impl;

import com.yy.common.service.RedisService;
import com.yy.system.ops.entity.UmsAdmin;
import com.yy.system.ops.service.UmsAdminCacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * UmsAdminCacheService实现类 Created by macro on 2020/3/13.
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    @Autowired
    private RedisService redisService;

    @Value("${redis.database}")
    private String redisDatabase;

    @Value("${redis.expire.common}")
    private Long redisExpire;

    @Value("${redis.key.admin}")
    private String redisKeyAdmin;

    @Override
    public void delAdmin(Long adminId) {
        String key = redisDatabase + ":" + redisKeyAdmin + ":" + adminId;
        redisService.del(key);
    }

    @Override
    public UmsAdmin getAdmin(Long adminId) {
        String key = redisDatabase + ":" + redisKeyAdmin + ":" + adminId;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = redisDatabase + ":" + redisKeyAdmin + ":" + admin.getId();
        redisService.set(key, admin, redisExpire);
    }
}
