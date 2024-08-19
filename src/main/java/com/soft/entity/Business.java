package com.soft.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Byterain
 * @since 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_business")
public class Business implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商家编号
     */
    @TableId(value = "business_id", type = IdType.AUTO)
    private Long businessId;

    /**
     * 商家名称
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 商家地址
     */
    @TableField("business_address")
    private String businessAddress;

    /**
     * 商家介绍
     */
    @TableField("business_explain")
    private String businessExplain;

    /**
     * 商家图片（base64）
     */
    @TableField("business_img")
    private String businessImg;

    /**
     * 起送费
     */
    @TableField("star_price")
    private BigDecimal starPrice;

    /**
     * 配送费
     */
    @TableField("delivery_price")
    private BigDecimal deliveryPrice;

    /**
     * 逻辑删除
     */
    @TableField("del_tag")
    private Integer delTag;

    /**
     * 创建时间
     */
    @TableField("created")
    private LocalDateTime created;

    /**
     * 修改时间
     */
    @TableField("updated")
    private LocalDateTime updated;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    @TableField("statu")
    private Integer statu;

    /**
     * 销量
     */
    @TableField(exist = false)
    private Integer soldNum;

    /**
     * 评分
     */
    @TableField(exist = false)
    private Double rate;
}
