package com.scst.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "商品类")
public class product {

    private Integer id;// 主键
    private String productId; //产品标识id
    private String productName;  // 商品名称
    private String productTag;   // 商品标签
    private Double productPrice;  //价格
    private Integer stock;  //库存
    private String businessName;  //商家名称
}
