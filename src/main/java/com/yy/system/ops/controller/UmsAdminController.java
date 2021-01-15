package com.yy.system.ops.controller;

import javax.validation.Valid;

import com.yy.system.api.CommonResult;
import com.yy.system.ops.dto.UmsAdminParam;
import com.yy.system.ops.entity.UmsAdmin;
import com.yy.system.ops.service.IUmsAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author tengyong
 * @since 2021-01-14
 */
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

    @Autowired
    private IUmsAdminService adminService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<UmsAdmin> register(@Valid @RequestBody UmsAdminParam umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "test")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public CommonResult<UmsAdmin> test() {
        int a = 1/0;
        return CommonResult.success(null);
    }
}
