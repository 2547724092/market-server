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
@TableName("sys_deliveryaddress")
public class Deliveryaddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 送货地址编号
     */
    @TableId(value = "da_id", type = IdType.AUTO)
    private Integer daId;

    /**
     * 联系人姓名
     */
    @TableField("contact_name")
        private String contactName;

    /**
     * 联系人性别
     */
    @TableField("contact_sex")
    private Integer contactSex;

    /**
     * 联系人电话
     */
    @TableField("contact_tel")
    private String contactTel;

    /**
     * 送货地址
     */
    @TableField("address")
    private String address;

    /**
     * 下单用户编号--sys_account表account_id
     */
    @TableField("account_id")
    private String accountId;

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
     * 状态
     */
    @TableField("statu")
    private Integer statu;


}
