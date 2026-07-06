package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("repair")
public class Repair extends BaseEntity {

    /**
     * 报修单号(后端自动生成)
     */
    private String repairNo;

    /**
     * 报修业主ID
     */
    @NotNull(message = "报修业主ID不能为空")
    private Long ownerId;

    /**
     * 房屋ID
     */
    @NotNull(message = "房屋ID不能为空")
    private Long houseId;

    /**
     * 报修类型(水电/门窗/管道/公共设施等)
     */
    @NotBlank(message = "报修类型不能为空")
    private String repairType;

    /**
     * 报修内容
     */
    @NotBlank(message = "报修内容不能为空")
    private String repairContent;

    /**
     * 报修图片(多张逗号分隔)
     */
    private String repairImages;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 期望维修时间
     */
    private LocalDateTime expectedTime;

    /**
     * 状态：0.待处理 1.已派单 2.维修中 3.已完成 4.已评价 5.已关闭
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
