package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fee_item")
public class FeeItem extends BaseEntity {

    /**
     * 费用编码
     */
    @NotBlank(message = "费用编码不能为空")
    private String feeCode;

    /**
     * 费用名称(如：物业费/停车费/水费)
     */
    @NotBlank(message = "费用名称不能为空")
    private String feeName;

    /**
     * 费用类型：1.物业费 2.水费 3.电费 4.停车费 5.其他
     */
    @NotNull(message = "费用类型不能为空")
    private Integer feeType;

    /**
     * 计费方式：1.按面积计费 2.固定金额 3.按用量计费
     */
    @NotNull(message = "计费方式不能为空")
    private Integer chargeMode;

    /**
     * 单价(元)
     */
    @NotNull(message = "单价不能为空")
    private BigDecimal unitPrice;

    /**
     * 计价单位(如：元/㎡/月、元/车位/月)
     */
    private String unit;

    /**
     * 计费周期：1.月度 2.季度 3.年度 4.一次性
     */
    @NotNull(message = "计费周期不能为空")
    private Integer billingCycle;

    /**
     * 状态：1.启用 0.停用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
