package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("complaint")
public class Complaint extends BaseEntity {

    /**
     * 投诉编号(后端自动生成)
     */
    private String complaintNo;

    /**
     * 投诉业主ID
     */
    @NotNull(message = "投诉业主ID不能为空")
    private Long ownerId;

    /**
     * 类型：1.投诉 2.建议
     */
    @NotNull(message = "投诉类型不能为空")
    private Integer complaintType;

    /**
     * 分类(物业服务/环境卫生/安全隐患/设施损坏/其他)
     */
    @NotBlank(message = "投诉分类不能为空")
    private String complaintCategory;

    /**
     * 标题
     */
    @NotBlank(message = "投诉标题不能为空")
    private String complaintTitle;

    /**
     * 详细内容
     */
    @NotBlank(message = "投诉内容不能为空")
    private String complaintContent;

    /**
     * 图片(多张逗号分隔)
     */
    private String complaintImages;

    /**
     * 联系电话
     */
    private String contactPhone;

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
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 满意度评分(1-5)
     */
    private Integer satisfaction;
}
