package com.soft.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.common.Result;
import com.soft.entity.Account;
import com.soft.entity.Orders;
import com.soft.service.AccountService;
import com.soft.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.soft.common.BaseController;

import java.time.LocalDateTime;
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController extends BaseController {
    @Autowired
    AccountService accountService;


    @GetMapping("/check/{accountId}")
    public Result check(@PathVariable String accountId){
        Account account = accountService.getById(accountId);
        if(account==null){
            return Result.success("手机号码可注册");
        }else{
            return Result.fail(20005,"手机号码已经注册","手机号码已经注册");
        }
    }

    @PostMapping("/update/")
    public Result updateAccount(@RequestBody Account account) {
        // 确保在 account 对象中正确设置字段
        account.setUpdated(LocalDateTime.now());

        // 执行更新操作
        accountService.updateById(account);
        // 加密密码
        return Result.success("更新成功");
    }
    @PostMapping("/updatepsw/")
    public Result updatepsw(@RequestBody Account account) {
        // 确保在 account 对象中正确设置字段
        account.setUpdated(LocalDateTime.now());
        // 加密密码
        account.setPassword( MD5Utils.md5(MD5Utils.inputPassToNewPass(account.getPassword()) ) );  //将输入的密码先加工再加密
        // 执行更新操作
        accountService.updateById(account);
        return Result.success("更新成功");
    }
    @PostMapping("/check")
    public Result checkPasswordWithAccount(@RequestBody Account account) {
        log.info("手机号:{}正在进行密码验证操作--", account.getAccountId());

        // 根据accountId查询账户信息
        Account dbAccount = accountService.getOne(new QueryWrapper<Account>().eq("account_id", account.getAccountId()));
        if (dbAccount == null) {
            return Result.fail("账户的手机号码不存在");
        }

        // 使用传入的密码进行加密处理，然后与数据库中的密码比较
        String encryptedInputPassword = MD5Utils.md5(MD5Utils.inputPassToNewPass(account.getPassword()));
        System.out.println(account.getPassword()+"mimamima");
        if (dbAccount.getPassword().equals(encryptedInputPassword)) {
            return Result.success("密码正确");
        } else {
            return Result.fail("密码不正确");
        }
    }
    @PostMapping("/register")
    public Result resgister(@RequestBody Account account){
        account.setCreated(LocalDateTime.now());
        account.setUpdated(LocalDateTime.now());
        account.setPassword(  MD5Utils.md5(MD5Utils.inputPassToNewPass(account.getPassword()) ) );  //将输入的密码先加工再加密
        account.setStatu(1);
        account.setAccountImg("https://server-system-soft.oss-cn-hangzhou.aliyuncs.com/default.jpg");
        accountService.save(account);
        return Result.success("用户信息注册成功");
    }
    //accountId=13333444444&password=123123
    @PostMapping("/login")
    public Result login(String accountId,String password){
        log.info("手机号:{}用户正在进行登录操作--",accountId);
        //查询accountId这个手机号在sys_account表中存在否?
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("account_id", accountId));
        if(account==null){
            return Result.fail("账户的手机号码不存在");
        }else{
            //需要将输入的密码进行加工再加密
            String md5password = MD5Utils.md5(MD5Utils.inputPassToNewPass(password));
            //判断查询account账户中密码密文和输入密码密文相等
            if(account.getPassword().equals(md5password)){
                //如果密码正确，登录操作成功，判断账户是否被禁用    1正常 0禁用
                if(account.getStatu()==0 || account.getDelTag()!=null ){
                    return Result.fail("该账户被禁用或被注销，暂不可用");
                }else{
                    //登录成功，直接返回登录账户对象信息
                    return Result.success(account);
                }
            }else{
                return Result.fail("密码不正确");
            }
        }
    }

    @PostMapping("/delete")
    public Result deleteAccountByAccountId(@RequestBody Account account) {
        // 确保在 account 对象中正确设置字段
        account.setUpdated(LocalDateTime.now());
        account.setDelTag(1);

        // 执行更新操作
        accountService.updateById(account);
        // 加密密码
        return Result.success("注销账号成功");
    }
}
