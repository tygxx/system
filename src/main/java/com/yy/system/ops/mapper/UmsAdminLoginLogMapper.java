package com.yy.system.ops.mapper;

import com.yy.system.ops.entity.UmsAdminLoginLog;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 后台用户登录日志表 Mapper 接口
 * </p>
 *
 * @author tengyong
 * @since 2021-01-14
 */
@Mapper
public interface UmsAdminLoginLogMapper extends BaseMapper<UmsAdminLoginLog> {

}
