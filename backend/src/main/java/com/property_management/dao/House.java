package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("house")
public class House extends BaseEntity {

    /**
     * 所属楼栋ID
     */
    private Long buildingId;

    /**
     * 房屋编号
     */
    private String houseNumber;

    /**
     * 所在楼层
     */
    private Integer floor;

    /**
     * 单元号
     */
    private Integer unitNumber;

    /**
     * 房屋面积(平方米)
     */
    private BigDecimal area;

    /**
     * 户型(如：一室一厅)
     */
    private String houseType;

    /**
     * 状态：0.空置 1.已入住 2.已出租
     */
    private Integer status;
}
