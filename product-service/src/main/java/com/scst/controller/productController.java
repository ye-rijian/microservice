package com.scst.controller;


import com.scst.entity.product;
import com.scst.service.productService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Api("商品信息查询接口")
@RestController
public class productController {

    @Autowired
    productService service;


    @ApiOperation(value = "根据productId具体查询商品")
    @GetMapping("getInfo")
    public product getInfo(String productId){
        product p = service.getInfo(productId);
        return p;
    }

    @ApiOperation(value = "根据名称模糊查询询商品")
    @GetMapping("getByLike")
    public product getByLike(String name){
        product p = service.getByLile(name);
        return p;
    }


}
