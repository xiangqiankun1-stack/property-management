package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("building")
public class Building extends BaseEntity {

    /**
     * 所属小区ID
     */
    private Long communityId;

    /**
     * 楼栋名称
     */
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
    private Integer status;
}
