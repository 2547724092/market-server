package com.soft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.Cart;
import com.soft.entity.Orders;
import com.soft.entity.Ordersdetailet;
import com.soft.mapper.OrdersMapper;
import com.soft.service.CartService;
import com.soft.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.service.OrdersdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Byterain
 * @since 2024-01-12
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrdersdetailetService ordersdetailetService;
    @Override
    public Long saveOrder(Orders orders) {
        //查询当前用户 购物车中的详情 商品数据。
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.in("cart_id",orders.getCartIds());

        List<Cart> cartList = cartService.list(qw);

        //保存订单
        orders.setCreated(LocalDateTime.now());
        orders.setUpdated(LocalDateTime.now());
        orders.setState(0); //1已支付，0未支付
        this.save(orders);  //订单表主键order_id字段是自增的。获取生成自增order_id字段值，因为保存订单明细需要订单编号的。
        Long orderId = orders.getOrderId();  //只有save()方法保存成功，Orders实体类的orderId属性会接受到数据库自增主键值。

        //保存订单明细。
        List<Ordersdetailet> odList = new ArrayList<>();  //定义一个存储订单明细的空 集合
        cartList.forEach(cart->{
            //从购物车中取出商品信息，创建为订单明细对象。
            Ordersdetailet od = new Ordersdetailet();
            od.setGoodsId(cart.getGoodsId());
            od.setOrderId(orderId);
            od.setQuantity(cart.getQuantity());
            //将创建的订单明细对象，依次保存到订单明细的集合中
            odList.add(od);
        });

        ordersdetailetService.saveBatch(odList);  //saveBatch()批量保存，将集合中的订单明细对象批量保存。

        //删除购物车中已经生成订单的数据
        cartService.remove(qw);

        //订单和订单明细保存成功，将生成订单号返回给前端VUe
        return orderId;
    }
}
