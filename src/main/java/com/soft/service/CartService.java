package com.soft.service;

import com.soft.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.entity.dto.AccountCartDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Byterain
 * @since 2024-01-12
 */
public interface CartService extends IService<Cart> {
    public List<AccountCartDto> getCartTree();

    public List<AccountCartDto> getCartTree(List<Cart> cartList);
}
