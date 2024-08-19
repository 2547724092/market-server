package com.soft.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.common.Result;
import com.soft.entity.Goods;
import com.soft.entity.Ordersdetailet;
import com.soft.service.GoodsService;
import com.soft.service.OrdersdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.soft.common.BaseController;

import java.util.List;

@RestController
@RequestMapping("/ordersdetailet")
public class OrdersdetailetController extends BaseController {

}
