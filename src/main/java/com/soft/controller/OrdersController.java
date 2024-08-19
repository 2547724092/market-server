package com.soft.controller;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.common.Result;
import com.soft.entity.*;
import com.soft.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.soft.common.BaseController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {
    @Autowired
    OrdersService ordersService;
    @Autowired
    AccountService accountService;
    @Autowired
    BusinessService businessService;
    @Autowired
    OrdersdetailetService ordersdetailetService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    CartService cartService;

    @GetMapping("/info/{orderId}")
    public Result info(@PathVariable Long orderId){
        Orders orders = ordersService.getById(orderId);
        //使用账户编号 查询下单账户的详情数据
        Account account = accountService.getById(orders.getAccountId());
        orders.setAccount(account);

        //使用商家编号  查询订单中商家的详情数据
        Business business = businessService.getById(orders.getBusinessId());
        orders.setBusiness(business);

        //使用订单的编号，查询该订单下的订单明细数据。
        List<Ordersdetailet> odList = ordersdetailetService.list(new QueryWrapper<Ordersdetailet>().eq("order_id", orders.getOrderId()));
        //循环订单明细数据，取出明细中 商品编号，到商品业务对象中查询该商品编号的 详细对象数据
        odList.stream().forEach(od->{
            Goods goods = goodsService.getById(od.getGoodsId());
            od.setGoods(goods);
        });

        orders.setOdList(odList);
        return Result.success(orders);
    }

    @PostMapping("/listByOrdersIds")
    public Result listByOrdersIds(@RequestBody Long[] ordersIds){
        List<Orders> orders = ordersService.listByIds(Arrays.asList(ordersIds));
        orders.forEach(o->{
            Account account = accountService.getById(o.getAccountId());
            o.setAccount(account);

            Business business = businessService.getById(o.getBusinessId());
            o.setBusiness(business);

            List<Ordersdetailet> odList = ordersdetailetService.list(new QueryWrapper<Ordersdetailet>().eq("order_id", o.getOrderId()));
            odList.stream().forEach(od->{
                Goods goods = goodsService.getById(od.getGoodsId());
                od.setGoods(goods);
            });

            o.setOdList(odList);
        });

        return Result.success(orders);
    }

//    @GetMapping("/list/{accountId}")
//    public Result listOrdersByAccountId(@PathVariable String accountId){
//        QueryWrapper<Orders> qw = new QueryWrapper<Orders>();
//        qw.eq("account_id",accountId);
//        List<Orders> list = ordersService.list(qw);
//
//        list.stream().forEach(o->{
//            Business bus = businessService.getById(o.getBusinessId());
//            o.setBusiness(bus);
//
//            List<Ordersdetailet> odlist = ordersdetailetService.list(new QueryWrapper<Ordersdetailet>().eq("order_id", o.getOrderId()));
//            odlist.stream().forEach(od->{
//                Goods goods = goodsService.getById(od.getGoodsId());
//                od.setGoods(goods);
//            });
//
//            o.setOdList(odlist);
//        });
//
//        return Result.success(list);
//    }

    @GetMapping("/list/{accountId}")
    public Result listOrdersByAccountId(@PathVariable String accountId, @RequestParam String businessName){
        QueryWrapper<Orders> qw = new QueryWrapper<Orders>();
        qw
                .eq("account_id",accountId)
                .inSql(StrUtil.isNotBlank(businessName), "business_id", "SELECT business_id FROM sys_business WHERE business_name LIKE '%" + businessName + "%'");
        List<Orders> list = ordersService.list(qw);

        list.stream().forEach(o->{
            Business bus = businessService.getById(o.getBusinessId());
            o.setBusiness(bus);

            List<Ordersdetailet> odlist = ordersdetailetService.list(new QueryWrapper<Ordersdetailet>().eq("order_id", o.getOrderId()));
            odlist.stream().forEach(od->{
                Goods goods = goodsService.getById(od.getGoodsId());
                od.setGoods(goods);
            });

            o.setOdList(odlist);
        });

        return Result.success(list);
    }

    @PostMapping("/pay/{orderId}")
    public Result update(@PathVariable Long orderId){
        Orders orders = new Orders();
        orders.setUpdated(LocalDateTime.now());
        orders.setState(1);

        QueryWrapper<Orders> qw =new QueryWrapper<>();
        qw.eq("order_id",orderId);
        ordersService.update(orders,qw);
        return Result.success("支付成功");
    }

    @PostMapping("/pay")
    public Result update(@RequestBody Long[] ordersIds){
        List<Orders> ordersList = ordersService.listByIds(Arrays.asList(ordersIds));

        ordersList.forEach(o->{
            o.setUpdated(LocalDateTime.now());
            o.setState(1);
        });

        ordersService.updateBatchById(ordersList);
        return Result.success("支付成功");
    }

    @Transactional
    @PostMapping("/save")
    public Result save(@RequestBody Orders orders){
        return Result.success(ordersService.saveOrder(orders));
    }

//    public Result save(@RequestBody Orders orders){
//        //查询当前用户 购物车中的详情 商品数据。
//        QueryWrapper<Cart> qw = new QueryWrapper<>();
//        qw.eq("account_id",orders.getAccountId());
//        qw.eq("business_id",orders.getBusinessId());
//
//        List<Cart> cartList = cartService.list(qw);
//
//        //保存订单
//        orders.setCreated(LocalDateTime.now());
//        orders.setUpdated(LocalDateTime.now());
//        orders.setState(0); //1已支付，0未支付
//        ordersService.save(orders);  //订单表主键order_id字段是自增的。获取生成自增order_id字段值，因为保存订单明细需要订单编号的。
//        Long orderId = orders.getOrderId();  //只有save()方法保存成功，Orders实体类的orderId属性会接受到数据库自增主键值。
//
//        //保存订单明细。
//        List<Ordersdetailet> odList = new ArrayList<>();  //定义一个存储订单明细的空 集合
//        cartList.forEach(cart->{
//            //从购物车中取出商品信息，创建为订单明细对象。
//            Ordersdetailet od = new Ordersdetailet();
//            od.setGoodsId(cart.getGoodsId());
//            od.setOrderId(orderId);
//            od.setQuantity(cart.getQuantity());
//            //将创建的订单明细对象，依次保存到订单明细的集合中
//            odList.add(od);
//        });
//
//        ordersdetailetService.saveBatch(odList);  //saveBatch()批量保存，将集合中的订单明细对象批量保存。
//
//        //删除购物车中已经生成订单的数据
//        cartService.remove(qw);
//
//        //订单和订单明细保存成功，将生成订单号返回给前端VUe
//        return Result.success(orderId);
//    }

    @Transactional
    @PostMapping("/saveList")
    public Result saveList(@RequestBody Orders[] orders){
        List<Long> ordersIds = new ArrayList<>();
        Arrays.asList(orders).forEach(o->{
            Long ordersId = ordersService.saveOrder(o);
            ordersIds.add(ordersId);
        });
        return Result.success(ordersIds);
    }
}
