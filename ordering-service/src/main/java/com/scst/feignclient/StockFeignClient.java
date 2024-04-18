package com.scst.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//声明Feign客户端,调用stock-service微服务
@FeignClient(name="stock-service")
public interface StockFeignClient {

    //调用deduct方法:http://[stock-service address]/stock/deduct/{id}
    @GetMapping("/stock/deduct/{id}")
    public String deduct(@PathVariable("id") String productId);
}
