package com.soft.controller;
import com.soft.common.Result;
import com.soft.entity.Category;
import com.soft.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.soft.common.BaseController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public Result list( ){
        List<Category> list = categoryService.list();
        return Result.success(list);
    }
}
