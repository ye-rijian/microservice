package com.scst.service;

import com.scst.domain.T_order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.scst.domain.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//库存服务
@Slf4j
@Service
public class OrderService {

    private List<Order> consumeRecds = new ArrayList<>();

    @Component
    //@RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "order-topic", consumerGroup = "${rocketmq.consumer.group}",consumeMode=ConsumeMode.ORDERLY)
    @RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "order-topic", consumerGroup = "${rocketmq.consumer.group}")
    class OrderConsumer implements RocketMQListener<Order> {
        @Override
        public void onMessage(Order order) {
            //消息体（Payload）o为Java对象
            log.info("开始记载订单:{}", order);
            consumeRecds.add(order);
            addOrder(order);
        }
    }

    @Transactional
    public void addOrder(Order order){
        T_order o=new T_order();
        o.setOrderid(order.getOrderId());
        o.setUserid(order.getUserId());
        o.setProductid(order.getProductId());
        o.setPurchasenum(order.getPurchaseNum());
        o.setStatu(order.isStatus());
        o.setCreatetime(new Date()+"");
        boolean result=o.insert();
        if(result){
            System.out.println("您成功插入了数据");
        }else{
            System.out.println("执行插入操作失败");
        }
    }

    public String secKillSuccesses(){
        return "当前服务记载的秒杀成功订单数："+this.consumeRecds.size();
    }

    public List<Order> getOrders(){
        return consumeRecds;
    }
}
