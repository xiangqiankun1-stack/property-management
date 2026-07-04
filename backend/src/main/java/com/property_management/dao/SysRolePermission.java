package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_permission")
public class SysRolePermission extends BaseEntity {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleInfoId;

    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    private Long permissionInfoId;

}