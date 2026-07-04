package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("owner")
public class Owner extends BaseEntity {

    /**
     * 业主姓名
     */
    @NotBlank(message = "业主姓名不能为空")
    private String ownerName;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phoneNumber;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 性别：1.男 2.女
     */
    private Integer gender;

    /**
     * 关联房屋ID
     */
    private Long houseId;

    /**
     * 与房屋关系(业主/家属/租户)
     */
    private String relationship;

    /**
     * 状态：1.正常 0.停用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
