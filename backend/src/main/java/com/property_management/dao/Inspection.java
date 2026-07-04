package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inspection")
public class Inspection extends BaseEntity {

    /**
     * 巡检任务编号
     */
    @NotBlank(message = "巡检任务编号不能为空")
    private String inspectionNo;

    /**
     * 巡检任务名称
     */
    @NotBlank(message = "巡检任务名称不能为空")
    private String inspectionName;

    /**
     * 所属小区ID
     */
    @NotNull(message = "所属小区ID不能为空")
    private Long communityId;

    /**
     * 巡检区域
     */
    private String inspectionArea;

    /**
     * 巡检人员ID(关联sys_user_info)
     */
    @NotNull(message = "巡检人员ID不能为空")
    private Long inspectorId;

    /**
     * 计划开始时间
     */
    @NotNull(message = "计划开始时间不能为空")
    private LocalDateTime planStartTime;

    /**
     * 计划结束时间
     */
    @NotNull(message = "计划结束时间不能为空")
    private LocalDateTime planEndTime;

    /**
     * 实际开始时间
     */
    private LocalDateTime actualStartTime;

    /**
     * 实际结束时间
     */
    private LocalDateTime actualEndTime;

    /**
     * 巡检项目(如：消防设施/电梯/楼道/停车场等)
     */
    private String inspectionItems;

    /**
     * 状态：0.待执行 1.巡检中 2.已完成 3.已取消
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
