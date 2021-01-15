package com.yy.system.ops.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.system.ops.dto.UmsAdminParam;
import com.yy.system.ops.entity.UmsAdmin;

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
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

}
