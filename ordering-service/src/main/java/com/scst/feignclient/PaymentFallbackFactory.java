package com.scst.feignclient;

import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//可拿到具体错误的容错类，也要求必须实现被容错的接口,并为每个方法实现容错方案
@Component
public class PaymentFallbackFactory implements FallbackFactory<PaymentFeignClient> {
    @Override
    public PaymentFeignClient create(Throwable cause){
        return new PaymentFeignClient() {
            @Override
            public String pay(String accountId) {
                cause.printStackTrace();
                return "FeignClient调用payment-service微服务失败："+ HttpStatus.INTERNAL_SERVER_ERROR;
            }
        };
    }
}
