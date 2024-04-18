package com.scst.controller;

import com.scst.service.stockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@RequestMapping("stock")
@RestController
public class StockController {

    @Autowired
    private stockService stock;

    @GetMapping("/deduct/{id}")
    public String deduct(@PathVariable("id") String productId) {
        boolean flag = stock.deduct(productId);
        return "商品号："+productId+"，库存减扣成功！";
    }
}
