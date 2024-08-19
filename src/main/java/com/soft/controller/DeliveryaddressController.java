package com.soft.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.common.Result;
import com.soft.entity.Deliveryaddress;
import com.soft.service.DeliveryaddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.soft.common.BaseController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/deliveryaddress")
@Slf4j
public class DeliveryaddressController extends BaseController {
    @Autowired
    DeliveryaddressService deliveryaddressService;

    @GetMapping("/listByAccountId/{accountId}")
    public Result listByAccountId(@PathVariable String accountId){
        log.info("获取用户编号为{}配送地址信息",accountId);
        List<Deliveryaddress> list = deliveryaddressService.list(new QueryWrapper<Deliveryaddress>().eq("account_id", accountId));
        if(list==null){
            return Result.fail("用户配送地址获取失败");
        }else{
            return Result.success(list);
        }
    }

    @PostMapping("/save")
    public Result save(@RequestBody Deliveryaddress deliveryaddress){
        log.info("用户编号为{}添加配送地址操作成功",deliveryaddress.getAccountId());
        deliveryaddress.setCreated(LocalDateTime.now());
        deliveryaddress.setUpdated(LocalDateTime.now());
        deliveryaddress.setStatu(1);
        deliveryaddressService.save(deliveryaddress);
        return Result.success("配送地址添加成功");
    }
    //编辑配送地址-->daId-->获取数据-->回显
    @GetMapping("/info/{daId}")
    public Result info(@PathVariable Integer daId){
        log.info("获取配送地址为{}的详细信息",daId);
        Deliveryaddress deliveryaddress = deliveryaddressService.getById(daId);
        if(deliveryaddress==null){
            return Result.fail("配送地址详细信息获取shibai");
        }else{
            return Result.success(deliveryaddress);
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody Deliveryaddress deliveryaddress){
        log.info("更新编号为{}配送地址信息",deliveryaddress.getDaId());
        deliveryaddress.setUpdated(LocalDateTime.now());
        QueryWrapper<Deliveryaddress> qw = new QueryWrapper<>();
        qw.eq("da_id",deliveryaddress.getDaId());
        deliveryaddressService.update(deliveryaddress,qw);
        return Result.success("配送地址更新成功");
    }

    @PostMapping("/delete/{daId}")
    public Result delete(@PathVariable Integer daId){
        log.info("删除配送地址变为{}的信息",daId);
        deliveryaddressService.removeById(daId);
        return Result.success("配送地址删除成功");
    }
}
