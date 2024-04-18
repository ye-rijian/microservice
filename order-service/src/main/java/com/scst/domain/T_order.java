package com.scst.domain;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.springframework.stereotype.Component;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Component
@Data
public class T_order extends Model<T_order> {
    private String orderid;        //唯一订单号（由雪花算法生成），据此实现消费幂等性
    private String userid;         //用户ID
    private String productid;      //商品ID
    private Integer purchasenum;   //购买数量
    private boolean statu;        //是否支付
    private String createtime;       //下单时间
}
