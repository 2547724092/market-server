package com.soft.service;

import com.soft.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Byterain
 * @since 2024-01-12
 */
public interface OrdersService extends IService<Orders> {
    public Long saveOrder(Orders orders);
}
