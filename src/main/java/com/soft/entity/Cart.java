package com.soft.entity;

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
@TableName("sys_cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 无意义编号，编号
     */
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Integer cartId;

    /**
     * 商品编号-sys_goods主键goods_id
     */
    @TableField("goods_id")
    private Integer goodsId;

    /**
     * 所属商家编号-sys_business主键businessId	
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 所属用户编号sys_account表account_id
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 同一类型商品的购买数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 创建时间
     */
    @TableField("created")
    private LocalDateTime created;

    /**
     * 更新时间
     */
    @TableField("updated")
    private LocalDateTime updated;

    /**
     * 状态0禁用1启动
     */
    @TableField("statu")
    private Integer statu;

    //添加商品详细信息对象
    @TableField(exist =  false)
    private Goods goods;

    //添加商家详细信息对象
    @TableField( exist = false)
    private Business business;
}
