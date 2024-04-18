package com.scst.feignclient;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//容错类，要求必须实现被容错的接口,并为每个方法实现容错方案
@Component
public class StockFallback implements StockFeignClient {
    @Override
    public String check(String stockId){
        return "FeignClient调用stock-service微服务失败："+ HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
