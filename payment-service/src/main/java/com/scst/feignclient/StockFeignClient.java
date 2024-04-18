package com.scst.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//声明Feign客户端,其中value用于指定被调用的微服务,fallback或fallbackFactory用于指定容错类
@FeignClient(value = "stock-service",fallbackFactory = StockFallbackFactory.class,configuration = FeignRequestInterceptor.class)
public interface StockFeignClient {
    //调用pay方法:http://[payment-service address]/payment/pay/{id}
    @GetMapping("/stock/deduct/{id}")
    public String check(@PathVariable("id") String stockId);
}
