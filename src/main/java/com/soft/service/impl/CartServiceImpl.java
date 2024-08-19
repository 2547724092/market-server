package com.soft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.Account;
import com.soft.entity.Business;
import com.soft.entity.Cart;
import com.soft.entity.Goods;
import com.soft.entity.dto.AccountCartDto;
import com.soft.entity.dto.BusinessCartDto;
import com.soft.entity.dto.CartDto;
import com.soft.entity.dto.GoodsCartDto;
import com.soft.mapper.CartMapper;
import com.soft.service.AccountService;
import com.soft.service.BusinessService;
import com.soft.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Byterain
 * @since 2024-01-12
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private GoodsService goodsService;

    @Override
    public List<AccountCartDto> getCartTree(){
        return convertAccount(this.list());
    }

    @Override
    public List<AccountCartDto> getCartTree(List<Cart> cartList){
        return convertAccount(cartList);
    }
    private List<AccountCartDto> convertAccount(List<Cart> cartList){
        Map<String, List<Cart>> accountCartMap = cartList.stream().collect(Collectors.groupingBy(Cart::getAccountId));
        List<AccountCartDto> accountCartDtos = new ArrayList<>();
        accountCartMap.forEach((accountId, carts) -> {
            AccountCartDto acDto = new AccountCartDto();
            Account account = accountService.getById(accountId);

            acDto.setAccountId(account.getAccountId());
            acDto.setAccountName(account.getAccountName());
            acDto.setAccountSex(account.getAccountSex());
            acDto.setAccountImg(account.getAccountImg());
//            acDto.setStatu(account.getStatu());

//            List<Cart> carts = this.list(new QueryWrapper<Cart>().eq("account_id", acDto.getAccountId()));
            acDto.setBusinessList(convertBusiness(carts));

            accountCartDtos.add(acDto);
        });

        return accountCartDtos;
    }

    private List<BusinessCartDto> convertBusiness(List<Cart> cartList){
        Map<Long, List<Cart>> businessCartMap = cartList.stream().collect(Collectors.groupingBy(Cart::getBusinessId));
        List<BusinessCartDto> businessCartDtos = new ArrayList<>();

        businessCartMap.forEach((businessId, carts) -> {
            BusinessCartDto bcDto = new BusinessCartDto();
            Business business = businessService.getById(businessId);

            bcDto.setBusinessId(business.getBusinessId());
            bcDto.setBusinessName(business.getBusinessName());
            bcDto.setBusinessAddress(business.getBusinessAddress());
            bcDto.setBusinessImg(business.getBusinessImg());
            bcDto.setBusinessExplain(business.getBusinessExplain());
            bcDto.setStarPrice(business.getStarPrice());
            bcDto.setDeliveryPrice(business.getDeliveryPrice());
//            bcDto.setStatu(business.getStatu());

            bcDto.setGoods(convertGoods(carts));
            businessCartDtos.add(bcDto);
        });

        return businessCartDtos;
    }

    private List<GoodsCartDto> convertGoods(List<Cart> cartList){
        Map<Integer, List<Cart>> goodsCartMap = cartList.stream().collect(Collectors.groupingBy(Cart::getGoodsId));
        List<GoodsCartDto> goodsCartDtos = new ArrayList<>();

        goodsCartMap.forEach((goodsId, carts) -> {
            GoodsCartDto gcDto = new GoodsCartDto();
            Goods goods = goodsService.getById(goodsId);

            gcDto.setGoodsId(goods.getGoodsId());
            gcDto.setGoodsName(goods.getGoodsName());
            gcDto.setGoodsExplain(goods.getGoodsExplain());
            gcDto.setGoodsImg(goods.getGoodsImg());
            gcDto.setGoodsPrice(goods.getGoodsPrice());
            gcDto.setBusinessId(goods.getBusinessId());
//            gcDto.setStatu(goods.getStatu());

            Cart cart = carts.get(0); // 每个account下每个business每个goods正好对应一个cartId
            gcDto.setCart(convertCart(cart));
            goodsCartDtos.add(gcDto);
        });

        return goodsCartDtos;
    }

    private CartDto convertCart(Cart cart){
        CartDto cDto = new CartDto();
        cDto.setCartId(cart.getCartId());
        cDto.setQuantity(cart.getQuantity());
        cDto.setCreated(cart.getCreated());
        cDto.setUpdated(cart.getUpdated());
//        cDto.setStatu(cart.getStatu());

        return cDto;
    }
}
