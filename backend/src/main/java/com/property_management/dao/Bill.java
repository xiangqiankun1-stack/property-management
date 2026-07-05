package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bill")
public class Bill extends BaseEntity {

    /**
     * 账单编号
     */
    @NotBlank(message = "账单编号不能为空")
    private String billNo;

    /**
     * 房屋ID
     */
    @NotNull(message = "房屋ID不能为空")
    private Long houseId;

    /**
     * 业主ID
     */
    @NotNull(message = "业主ID不能为空")
    private Long ownerId;

    /**
     * 费用项目ID
     */
    @NotNull(message = "费用项目ID不能为空")
    private Long feeItemId;

    /**
     * 账期开始日期
     */
    @NotNull(message = "账期开始日期不能为空")
    private LocalDate periodStart;

    /**
     * 账期结束日期
     */
    @NotNull(message = "账期结束日期不能为空")
    private LocalDate periodEnd;

    /**
     * 应收金额(元)
     */
    @NotNull(message = "应收金额不能为空")
    private BigDecimal amount;

    /**
     * 已收金额(元)
     */
    @NotNull(message = "已收金额不能为空")
    private BigDecimal paidAmount;

    /**
     * 缴费截止日期
     */
    private LocalDate dueDate;

    /**
     * 账单生成时间
     */
    @NotNull(message = "账单生成时间不能为空")
    private LocalDateTime generateTime;

    /**
     * 状态：0.未缴纳 1.部分缴纳 2.已缴清 3.已作废
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
