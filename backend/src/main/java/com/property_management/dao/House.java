package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "所属楼栋ID不能为空")
    private Long buildingId;

    /**
     * 房屋编号
     */
    @NotBlank(message = "房屋编号不能为空")
    private String houseNumber;

    /**
     * 所在楼层
     */
    @NotNull(message = "所在楼层不能为空")
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
    @NotNull(message = "状态不能为空")
    private Integer status;
}
