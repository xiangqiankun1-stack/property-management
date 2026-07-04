package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("repair_order")
public class RepairOrder extends BaseEntity {

    /**
     * 报修单ID
     */
    @NotNull(message = "报修单ID不能为空")
    private Long repairId;

    /**
     * 维修人员ID(关联sys_user_info)
     */
    @NotNull(message = "维修人员ID不能为空")
    private Long workerId;

    /**
     * 派单时间
     */
    @NotNull(message = "派单时间不能为空")
    private LocalDateTime assignTime;

    /**
     * 接单时间
     */
    private LocalDateTime acceptTime;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 维修结果描述
     */
    private String resultDesc;

    /**
     * 状态：0.待接单 1.已接单 2.维修中 3.已完成 4.已取消
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
