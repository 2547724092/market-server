package com.soft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.common.Result;
import com.soft.entity.*;
import com.soft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.soft.common.BaseController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController extends BaseController {
    @Autowired
    BusinessService businessService;
    @Autowired
    BusinessCategoryService businessCategoryService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    CommentService commentService;
    @Autowired
    FavoriteService favoriteService;

    @GetMapping("/list")
    public Result list() {
        List<Business> list = businessService.list();
        if (list == null) {
            return Result.fail("首页商家数据加载失败");
        }
        // 遍历每个商家
        for (Business business : list) {
            // 根据商家ID查询所有商品
            List<Goods> goodsList = goodsService.list(new QueryWrapper<Goods>().eq("business_id", business.getBusinessId()));
            List<Comment> commentList = commentService.list(new QueryWrapper<Comment>().eq("business_id", business.getBusinessId()));

            Integer totalQuantity = 0;
            Integer num = 0;
            Double totalRate = 0.0;

            // 遍历每个商品并累加其数量
            for (Goods goods : goodsList) {
                totalQuantity += goods.getSoldNum();
            }
            for (Comment comment : commentList) {
                num++;
                totalRate += comment.getRate();
            }

            if(num>0){
                business.setRate(totalRate / num);
            }

            // 将总数量设置到商家对象中
            business.setSoldNum(totalQuantity);  // 确保Business对象有setQuantity方法
        }

        // 返回包含商家及其商品总数量的结果
        return Result.success(list);
    }


    @GetMapping("/listByCategoryId/{categoryId}")
    public Result listByCategoryId(@PathVariable Integer categoryId) {
        List<BusinessCategory> bcList = businessCategoryService.list(new QueryWrapper<BusinessCategory>().eq("category_id", categoryId));
        List<Business> businessList = new ArrayList<>();

        bcList.stream().forEach(bc -> {
            Business business = businessService.getById(bc.getBusinessId());
            businessList.add(business);
        });

        if (businessList == null) {
            return Result.fail("该类别下的商家数据加载失败");
        } else {
            return Result.success(businessList);
        }
    }

    @GetMapping("/info/{businessId}")
    public Result info(@PathVariable Long businessId) {
        //如果是根据主键查询
        Business business = businessService.getById(businessId);
        if (business == null) {
            return Result.fail("商家的详细信息加载失败.");
        } else {
            return Result.success(business);
        }
    }

    @GetMapping("/listFavriteByAccountId/{accountId}")
    public Result listFavriteByAccountId(@PathVariable String accountId) {
        List<Favorite> favoriteList = favoriteService.list(new QueryWrapper<Favorite>().eq("account_id", accountId));
        List<Business> businessList = new ArrayList<>();
        for (Favorite f : favoriteList) {
            businessList.add(businessService.getById(f.getBusinessId()));
        }

        for (Business business : businessList) {
            // 根据商家ID查询所有商品
            List<Goods> goodsList = goodsService.list(new QueryWrapper<Goods>().eq("business_id", business.getBusinessId()));
            List<Comment> commentList = commentService.list(new QueryWrapper<Comment>().eq("business_id", business.getBusinessId()));

            Integer totalQuantity = 0;
            Integer num = 0;
            Double totalRate = 0.0;

            // 遍历每个商品并累加其数量
            for (Goods goods : goodsList) {
                totalQuantity += goods.getSoldNum();
            }
            for (Comment comment : commentList) {
                num++;
                totalRate += comment.getRate();
            }

            if(num>0){
                business.setRate(totalRate / num);
            }

            // 将总数量设置到商家对象中
            business.setSoldNum(totalQuantity);  // 确保Business对象有setQuantity方法
        }
        return Result.success(businessList);
    }
}
