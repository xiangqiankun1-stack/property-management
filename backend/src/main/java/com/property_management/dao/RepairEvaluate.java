package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("repair_evaluate")
public class RepairEvaluate extends BaseEntity {

    /**
     * 报修单ID
     */
    @NotNull(message = "报修单ID不能为空")
    private Long repairId;

    /**
     * 评价业主ID
     */
    @NotNull(message = "评价业主ID不能为空")
    private Long ownerId;

    /**
     * 评分(1-5分)
     */
    @NotNull(message = "评分不能为空")
    private Integer score;

    /**
     * 评价内容
     */
    private String evaluateContent;

    /**
     * 评价图片(多张逗号分隔)
     */
    private String evaluateImages;

    /**
     * 服务态度评分(1-5)
     */
    private Integer serviceAttitude;

    /**
     * 维修质量评分(1-5)
     */
    private Integer repairQuality;

    /**
     * 响应速度评分(1-5)
     */
    private Integer responseSpeed;
}
