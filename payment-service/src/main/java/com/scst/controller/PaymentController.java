package com.scst.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

import com.scst.feignclient.AccountFeignClient;
import com.scst.feignclient.OrderFeignClient;
import com.scst.feignclient.StockFeignClient;
import com.scst.handler.SentinelResourceExceptionHandler;
import com.scst.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private Environment environment;
    @Autowired
    private AccountFeignClient accountFeignClient;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderFeignClient orderFeignClient;
    @Qualifier("com.scst.feignclient.StockFeignClient")
    @Autowired
    private StockFeignClient stockFeignClient;

    @GetMapping("/pay/{id}")
    @SentinelResource(value = "pay",blockHandlerClass = SentinelResourceExceptionHandler.class,
            blockHandler = "blockHandler",
            fallbackClass = SentinelResourceExceptionHandler.class,
            fallback = "fallback")
    public String pay(@PathVariable("id") String accountId) {
        String payResult=accountFeignClient.reduce(accountId);
        boolean flag = paymentService.pay(accountId);
        String serverPort=environment.getProperty("server.port");
        return payResult+" 支付服务端口："+serverPort;
    }

    @GetMapping("/update/{id}")
    @SentinelResource(value = "pay",blockHandlerClass = SentinelResourceExceptionHandler.class,
            blockHandler = "blockHandler",
            fallbackClass = SentinelResourceExceptionHandler.class,
            fallback = "fallback")
    public String update(@PathVariable("id") String productId){
        String updateResult=orderFeignClient.update(productId);
        boolean flag=paymentService.pay(productId);
        String serverPort=environment.getProperty("server.port");
        return updateResult+" 支付服务端口："+serverPort;
    }

    @GetMapping("/check/{id}")
    @SentinelResource(value = "pay",blockHandlerClass = SentinelResourceExceptionHandler.class,
            blockHandler = "blockHandler",
            fallbackClass = SentinelResourceExceptionHandler.class,
            fallback = "fallback")
    public String check(@PathVariable("id") String stockId) {
        String deductResult = stockFeignClient.check(stockId);
        boolean flag = paymentService.pay(stockId);
        return deductResult;
    }
}