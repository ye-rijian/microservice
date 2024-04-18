package com.scst.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;        //唯一订单号（由雪花算法生成），据此实现消费幂等性
    private String userId;         //用户ID
    private String productId;      //商品ID
    private Integer purchaseNum;   //购买数量
    private boolean status;        //是否支付
    private Date createTime;       //下单时间
}
