package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("community")
public class Community extends BaseEntity {

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 小区地址
     */
    private String communityAddress;

    /**
     * 小区面积(平方米)
     */
    private BigDecimal area;

    /**
     * 总楼栋数
     */
    private Integer totalBuildings;

    /**
     * 开发商
     */
    private String developer;

    /**
     * 物业公司
     */
    private String propertyCompany;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 状态：1.正常 0.停用
     */
    private Integer status;
}
