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
@TableName("sys_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食品编号
     */
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;

    /**
     * 食品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 食品介绍
     */
    @TableField("goods_explain")
    private String goodsExplain;

    /**
     * 食品图片
     */
    @TableField("goods_img")
    private String goodsImg;

    /**
     * 食品价格
     */
    @TableField("goods_price")
    private BigDecimal goodsPrice;

    /**
     * 所属商家编号
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 销售数量
     */
    @TableField("sold_num")
    private Integer soldNum;

    /**
     * 更新时间
     */
    @TableField("updated")
    private LocalDateTime updated;

    /**
     * 状态
     */
    @TableField("statu")
    private Integer statu;

    /**
     * 创建时间
     */
    @TableField("created")
    private LocalDateTime created;


}
