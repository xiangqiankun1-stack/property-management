package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission_info")
public class SysPermissionInfo extends BaseEntity {

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    private String permissionName;

    /**
     * 权限类型
     * 1：目录
     * 2：菜单
     * 3：按钮
     */
    private Integer permissionType;

    /**
     * 父节点ID
     */
    private Long parentId;

    /**
     * 图标
     */
    private String permissionIcon;

    /**
     * 权限标识
     */
    private String permissionStr;

    /**
     * 前端组件地址
     */
    private String permissionComponent;

    /**
     * 排序
     */
    private Integer sort;

}
