package com.yy.system.ops.service.impl;

import com.yy.system.ops.entity.UmsAdminPermissionRelation;
import com.yy.system.ops.mapper.UmsAdminPermissionRelationMapper;
import com.yy.system.ops.service.IUmsAdminPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) 服务实现类
 * </p>
 *
 * @author tengyong
 * @since 2021-01-16
 */
@Service
public class UmsAdminPermissionRelationServiceImpl extends ServiceImpl<UmsAdminPermissionRelationMapper, UmsAdminPermissionRelation> implements IUmsAdminPermissionRelationService {

}
