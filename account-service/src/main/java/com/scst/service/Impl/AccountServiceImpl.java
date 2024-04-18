package com.scst.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scst.entity.account;
import com.scst.mapper.accountMapper;
import com.scst.service.accountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Slf4j
@Service
public class AccountServiceImpl implements accountService {

    public boolean reduce(String accountId){
        log.info("account ing...");
        //查询数据库
        account a = new account();
        QueryWrapper<account> wrapper = new QueryWrapper<>();
        wrapper.eq("a_id",accountId);
        account target = a.selectOne(wrapper);
        //判断用户是否为空
        if (target != null) {
            float balance = target.getBalance()-100;
            target.setBalance(balance);
          target.updateById();
            return true;
        }

        //若没有该账户
        log.error("不存在该用户");
        System.out.println("不存在该用户");
        return false;
    }
}
