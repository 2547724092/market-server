package com.soft.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.common.Result;
import com.soft.entity.Comment;
import com.soft.service.AccountService;
import com.soft.service.CommentService;
import com.soft.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.soft.common.BaseController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
@RequestMapping("/comment")
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private OSSUtil ossUtil;
    @Autowired
    private AccountService accountService;

    @PostMapping("/save")
    public Result save(@RequestBody Comment comment){
        comment.setCreated(LocalDateTime.now());
        commentService.save(comment);
        return Result.success("评论成功");
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file){   //上传的文件必须叫 file
        String imgPath = ossUtil.uploadOneFile(file);
        if(imgPath == null){
            return Result.fail("图片上传失败");
        }
        return Result.success(imgPath);
    }

    //显示所有评论
    @GetMapping("/listByBusinessId/{businessId}")
    public Result listByBusinessId(@PathVariable Long businessId){
        List<Comment> commentList = commentService.list(new QueryWrapper<Comment>().eq("business_id",businessId));
        for(Comment comment:commentList){
            comment.setAccount(accountService.getById(comment.getAccountId()));
        }

        return Result.success(commentList);
    }


    //根据用户id显示用户评论
    @GetMapping("/listByAccountId/{accountId}")
    public Result listByAccountId(@PathVariable String accountId){
        List<Comment> commentList = commentService.list(new QueryWrapper<Comment>().eq("account_id",accountId));
        return Result.success(commentList);
    }


    //根据用户ID删除评论
    @PostMapping("/deleteByCoId")
    public Result deletByCoId(@RequestBody Integer coId){
        commentService.removeById(coId);
        return Result.success("评论删除成功");
    }
}
