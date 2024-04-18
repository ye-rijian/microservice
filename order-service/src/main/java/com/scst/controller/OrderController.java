package com.scst.controller;

import com.scst.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scst.domain.Order;

import java.util.List;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@RequestMapping("order")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/successes")
    public String successes() {
        return orderService.secKillSuccesses();
    }

    @GetMapping("/orders")
    public List<Order> orders() {
        return orderService.getOrders();
    }

//    @GetMapping("/Getorders")
//    public String addOrder(){
//        orderService.addOrder(Order order);
//        return "success";
//    }
}