package com.property_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.property_management.dao.SysPermissionInfo;
import com.property_management.dao.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<SysRolePermission> {

    @Select("SELECT p.* FROM sys_role_permission rp " +
            "INNER JOIN sys_permission_info p ON rp.permission_info_id = p.id " +
            "WHERE rp.role_info_id = #{roleId}")
    List<SysPermissionInfo> selectByRoleId(Long roleId);
}
