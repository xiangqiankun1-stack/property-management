package com.property_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.property_management.dao.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<SysUserInfo> {
    @Select("select * from sys_user_info where user_name = #{username}")
    SysUserInfo findByUserName(String username);
}
