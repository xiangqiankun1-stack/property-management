package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("building")
public class Building extends BaseEntity {

    /**
     * 所属小区ID
     */
    @NotNull(message = "所属小区ID不能为空")
    private Long communityId;

    /**
     * 楼栋名称
     */
    @NotBlank(message = "楼栋名称不能为空")
    private String buildingName;

    /**
     * 总层数
     */
    private Integer totalFloors;

    /**
     * 每层户数
     */
    private Integer unitsPerFloor;

    /**
     * 状态：1.正常 0.停用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
