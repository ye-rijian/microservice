package com.scst.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Slf4j
public class SentinelResourceExceptionHandler {
    //BlockException时进入的方法
    public static String blockHandler(String accountId,BlockException ex) {
        log.error("{}", ex);
        return "账号："+accountId+"，[此为被限流降级处理结果]";
    }
    //Throwable时进入的方法
    public static String fallback(String accountId,Throwable throwable) {
        log.error("{}", throwable);
        return "账号："+accountId+"，[此为被容错处理结果]";
    }
}
