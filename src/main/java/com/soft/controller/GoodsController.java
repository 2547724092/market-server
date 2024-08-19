package com.soft.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.common.Result;
import com.soft.entity.Goods;
import com.soft.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.soft.common.BaseController;

import java.util.List;

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController extends BaseController {
    @Autowired
    GoodsService goodsService;

    @GetMapping("/listByBusinessId/{businessId}")
    public Result listByBusinessId(@PathVariable Long businessId){
        log.info("获取商家编号为{}所有的餐品信息.",businessId);
        List<Goods> goodsList = goodsService.list(new QueryWrapper<Goods>().eq("business_id", businessId));
        if(goodsList==null){
            return Result.fail("获得商家发布的商品信息失败");
        }else{
            return Result.success(goodsList);
        }
    }

    @GetMapping("/info/{goodsId}")
    public Result info(@PathVariable Integer goodsId){
        log.info("查询商品编号为{}的商品详情数据",goodsId);
        Goods goods = goodsService.getById(goodsId);
        if(goods==null){
            return Result.fail("商品详情数据查询失败");
        }else{
            return Result.success(goods);
        }
    }
}
