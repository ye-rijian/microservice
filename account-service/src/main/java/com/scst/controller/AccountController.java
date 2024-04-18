package com.scst.controller;

import com.scst.service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@RequestMapping("account")
@RestController
public class AccountController {

    @Autowired
    private accountService account;

    @GetMapping("/reduce/{id}")
    public String reduce(@PathVariable("id") String accountId) {
        boolean flag = account.reduce(accountId);
        return "账号："+accountId+"，扣款成功!";
    }
}
