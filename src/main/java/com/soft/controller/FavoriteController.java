package com.soft.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.common.Result;
import com.soft.entity.Business;
import com.soft.entity.Favorite;
import com.soft.service.BusinessService;
import com.soft.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.soft.common.BaseController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author group4
 * @since 2024-06-21
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController extends BaseController {
    @Autowired
    BusinessService businessService;
    @Autowired
    FavoriteService favoriteService;

    //通过 点击 “♥” 收藏 根据所选择的businessId 和 当前accountId 上传至表sys_favorite中
    @PostMapping("/saveFavorite")
    public Result saveFavorite(@RequestBody Favorite favorite){
        //获取收藏商家的名称
        Business business = businessService.getById(favorite.getBusinessId());
        String businessName = business.getBusinessName();
        //不可重复收藏商家
        Favorite fa = favoriteService.getOne(new QueryWrapper<Favorite>().eq("business_id",favorite.getBusinessId()).eq("account_id",favorite.getAccountId()));
        if (!StringUtils.isEmpty(fa)){
            return Result.fail("不可重复收藏商家！");
        }
        favoriteService.save(favorite);
        return Result.success("您已收藏商家【"+businessName+"】!");
    }
    //通过 点击“红心” 取消收藏
    @PostMapping("/deleteFavorite")
    public Result delFavorite(@RequestBody Favorite favorite){
        //获取收藏商家的名称
        Business business = businessService.getById(favorite.getBusinessId());
        String businessName = business.getBusinessName();

        Favorite fa = favoriteService.getOne(new QueryWrapper<Favorite>().eq("business_id",favorite.getBusinessId()).eq("account_id",favorite.getAccountId()));
        favoriteService.removeById(fa.getFavoriteId());

        return Result.success("您已成功将商家【"+businessName+"】移出收藏！");
    }
    //根据accountId查看收藏的商家
    @GetMapping("/listFavorite/{accountId}")
    public Result listFavoriteByAccountId(@PathVariable String accountId){
        List<Favorite> list = favoriteService.list(new QueryWrapper<Favorite>().eq("account_id",accountId));
        return Result.success(list);
    }
    //根据accountId删除所有当前账户收藏的商家
    @PostMapping("/delAllFavorite/{accountId}")
    public Result delAllFavorite(@PathVariable String accountId){
        favoriteService.remove(new QueryWrapper<Favorite>().eq("account_id",accountId));
        return Result.success("成功清空所有收藏的商家！");
    }
    //根据商家 businessIds 查询对应商家信息
    @PostMapping("/listFavoriteBusiness")
    public Result list(@RequestBody Long[] businessIds){
        List<Business> businessList = new ArrayList<>();
        //数组转集合
        Arrays.stream(businessIds).forEach(businessId ->{
            Business business = businessService.getById(businessId);
            //存入对象 businessList
            businessList.add(business);
        });
        return Result.success(businessList);
    }


}
