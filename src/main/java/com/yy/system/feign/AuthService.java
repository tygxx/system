package com.yy.system.feign;

import java.util.Map;

import com.yy.common.api.CommonResult;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 *@Description: 认证服务远程调用
 *@ClassAuthor: tengYong
 *@Date: 2021-01-26 18:36:10
*/
@FeignClient("auth")
public interface AuthService {

    @PostMapping(value = "/oauth/token")
    CommonResult getAccessToken(@RequestParam Map<String, String> parameters);

}
