package com.scst.controller;

import com.scst.feignclient.PaymentFeignClient;
import com.scst.feignclient.StockFeignClient;
import com.scst.service.OrderingService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@RestController
@RequestMapping("/ordering")
@RefreshScope
public class OrderingController {

    @Autowired
    private PaymentFeignClient paymentFeignClient;
    @Autowired
    private StockFeignClient stockFeignClient;
    @Autowired
    private OrderingService orderingService;


    private String topic = "msg-topic";
    private String tag = "TagA";

    @GetMapping("/purchase/{pid}/{aid}")
    @GlobalTransactional
    public String purchase(@PathVariable("pid") String productId, @PathVariable("aid") String accountId) {
        String deductResult = stockFeignClient.deduct(productId);
        String payResult = paymentFeignClient.pay(accountId);
        boolean flag = orderingService.purchase(productId,accountId);
        return deductResult+payResult;
    }

    @GetMapping("/transactionMessage")
    public void testTransactionMessage() {
        orderingService.sendTransactionMessage(topic, tag);
    }


    @GetMapping("/asyncMessage")
    public void testAsyncMessage() {
        orderingService.sendAsyncMessage(topic, tag);
    }

    @GetMapping("/delayMessage")
    public void testDelayMessage() {
        orderingService.sendDelayMessage(topic, tag);
    }

    @GetMapping("/orderedMessages")
    public void testOrderedMessages() {
        orderingService.sendOrderedMessages(topic, tag);
    }

    @GetMapping("/batchMessages")
    public void testBatchMessages() {
        orderingService.sendBatchMessages(topic, tag);
    }

}