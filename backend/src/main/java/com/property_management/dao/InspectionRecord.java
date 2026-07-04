package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inspection_record")
public class InspectionRecord extends BaseEntity {

    /**
     * 巡检任务ID
     */
    private Long inspectionId;

    /**
     * 整改单号
     */
    private String recordNo;

    /**
     * 问题类型(设施损坏/安全隐患/环境问题/其他)
     */
    private String issueType;

    /**
     * 问题描述
     */
    private String issueDesc;

    /**
     * 问题图片(多张逗号分隔)
     */
    private String issueImages;

    /**
     * 问题位置
     */
    private String issueLocation;

    /**
     * 严重程度：1.轻微 2.一般 3.严重 4.紧急
     */
    private Integer severity;

    /**
     * 整改负责人ID(关联sys_user_info)
     */
    private Long handleUserId;

    /**
     * 整改说明
     */
    private String handleDesc;

    /**
     * 整改后图片(多张逗号分隔)
     */
    private String handleImages;

    /**
     * 计划整改期限
     */
    private LocalDateTime planDeadline;

    /**
     * 实际完成时间
     */
    private LocalDateTime actualCompleteTime;

    /**
     * 验收人ID
     */
    private Long verifyUserId;

    /**
     * 验收时间
     */
    private LocalDateTime verifyTime;

    /**
     * 验收结果：1.合格 2.不合格
     */
    private Integer verifyResult;

    /**
     * 状态：0.待整改 1.整改中 2.待验收 3.已完成 4.已驳回
     */
    private Integer status;
}
