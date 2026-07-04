package com.property_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.property_management.dao.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("""
        SELECT r.role_name
        FROM sys_user_role ur
        INNER JOIN sys_role_info r
        ON ur.role_info_id = r.id
        WHERE ur.user_info_id = #{userId}
        """)
    List<String> getUserRole(Long userId);
}
