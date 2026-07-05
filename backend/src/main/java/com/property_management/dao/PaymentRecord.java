package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("payment_record")
public class PaymentRecord extends BaseEntity {

    /**
     * 缴费流水号
     */
    @NotBlank(message = "缴费流水号不能为空")
    private String paymentNo;

    /**
     * 账单ID
     */
    @NotNull(message = "账单ID不能为空")
    private Long billId;

    /**
     * 缴费业主ID
     */
    @NotNull(message = "缴费业主ID不能为空")
    private Long ownerId;

    /**
     * 本次缴费金额(元)
     */
    @NotNull(message = "缴费金额不能为空")
    private BigDecimal payAmount;

    /**
     * 缴费方式：1.现金 2.微信 3.支付宝 4.银行卡 5.其他
     */
    @NotNull(message = "缴费方式不能为空")
    private Integer payMethod;

    /**
     * 缴费时间
     */
    @NotNull(message = "缴费时间不能为空")
    private LocalDateTime payTime;

    /**
     * 登记人员ID(关联sys_user_info)
     */
    @NotNull(message = "登记人员ID不能为空")
    private Long operatorId;

    /**
     * 第三方支付流水号/凭证号
     */
    private String voucherNo;

    /**
     * 状态：1.有效 0.已作废(如登记有误被撤销)
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
