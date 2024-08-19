package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author group4
 * @since 2024-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "coId", type = IdType.AUTO)
    private Integer coid;

    /**
     * 用户编号
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 商家id
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 评论图片
     */
    @TableField("co_img")
    private String coImg;

    @TableField("created")
    private LocalDateTime created;

    /**
     * 评分
     */
    @TableField("rate")
    private Double rate;

    /**
     * 评价内容
     */
    @TableField("co_text")
    private String coText;

    /**
     * 订单编号
     */
    @TableField("order_id")
    private Long orderId;

    @TableField(exist = false)
    private Account account;
}
