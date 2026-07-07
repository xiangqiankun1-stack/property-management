package com.property_management.dao;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 投诉处理DTO，仅包含处理投诉时前端需要传递的字段
 */
@Data
public class ComplaintHandleDTO {

    /**
     * 处理人ID(关联sys_user_info)
     */
    private Long handleUserId;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 状态：0.待处理 1.处理中 2.已处理 3.已关闭
     */

    private Integer status;

    /**
     * 满意度评分(1-5)
     */
    private Integer satisfaction;
}
