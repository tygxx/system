package com.yy.system.ops.mapper;

import com.yy.system.ops.entity.UmsAdminRoleRelation;
import com.yy.system.ops.entity.UmsPermission;
import com.yy.system.ops.entity.UmsResource;
import com.yy.system.ops.entity.UmsRole;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 后台用户和角色关系表 Mapper 接口
 * </p>
 *
 * @author tengyong
 * @since 2021-01-16
 */
@Mapper
public interface UmsAdminRoleRelationMapper extends BaseMapper<UmsAdminRoleRelation> {

    @Select("<script>" 
            + " SELECT r.* from ums_admin_role_relation ar "
            + " LEFT JOIN ums_role r on ar.role_id = r.id where ar.admin_id = #{adminId}" 
            + " </script>")
    List<UmsRole> getRoleListByAdminId(Long adminId);

    @Select("<script>" 
            + " SELECT ur.id id, ur.create_time createTime, ur.`name` `name`,"
            + " ur.url url, ur.description description, ur.category_id categoryId"
            + " FROM ums_admin_role_relation ar LEFT JOIN ums_role r ON ar.role_id = r.id "
            + " LEFT JOIN ums_role_resource_relation rrr ON r.id = rrr.role_id"
            + " LEFT JOIN ums_resource ur ON ur.id = rrr.resource_id"
            + " WHERE ar.admin_id = #{adminId} AND ur.id IS NOT NULL" 
            + " GROUP BY ur.id " 
            + " </script>")
    List<UmsResource> getResourceByAdminId(Long adminId);

    @Select("<script>" 
            + " select p.*" 
            + " from ums_admin_role_relation ar left join ums_role r on ar.role_id = r.id"
            + " left join ums_role_permission_relation rp on r.id = rp.role_id "
            + " left join ums_permission p on rp.permission_id=p.id"
            + " where ar.admin_id = #{adminId} and p.id is not null" 
            + " </script>")
    List<UmsPermission> getPermissionListByAdminId(Long adminId);

}
