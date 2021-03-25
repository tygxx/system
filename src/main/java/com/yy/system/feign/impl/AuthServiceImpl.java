package com.yy.system.feign.impl;

import java.util.Map;

import com.yy.common.api.CommonResult;
import com.yy.system.feign.AuthService;

import org.springframework.stereotype.Component;

@Component
public class AuthServiceImpl implements AuthService {

    @Override
    public CommonResult getAccessToken(Map<String, String> parameters) {
        // TODO Auto-generated method stub
        return CommonResult.failed("system调用认证中心获取token失败");
    }
    
}