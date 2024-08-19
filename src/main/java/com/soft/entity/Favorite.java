package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
@TableName("sys_favorite")
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收藏id
     */
    @TableId(value = "favorite_id", type = IdType.AUTO)
    private Long favoriteId;

    /**
     * 收藏的用户
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 被收藏商家
     */
    @TableField("business_id")
    private Long businessId;


}
