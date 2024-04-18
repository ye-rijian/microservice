package com.scst.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@FeignClient(name = "ordering-service")
public interface OrderFeignClient {

    @GetMapping("/order/update/{id}")
    public String update(@PathVariable("id") String productId);
}
