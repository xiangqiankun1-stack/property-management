package com.property_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.property_management.dao.SysRoleInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<SysRoleInfo> {
}
