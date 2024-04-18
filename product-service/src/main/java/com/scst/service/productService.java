package com.scst.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scst.entity.product;
import com.scst.mapper.productMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class productService {

    @Autowired
    productMapper mapper;

    //根据商品id查询对应商品信息
    public product getInfo(String productId){
        LambdaQueryWrapper<product> wrapper = new LambdaQueryWrapper();
        wrapper.eq(product::getProductId,productId);
        product p = mapper.selectOne(wrapper);
        return p;
    }

    //根据商品名称模糊查询
    public product getByLile(String name){
        LambdaQueryWrapper<product> wrapper = new LambdaQueryWrapper();
        wrapper.like(product::getProductName,name);
        product p = mapper.selectOne(wrapper);
        return p;
    }
}
