package com.scst.service;

import com.scst.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
        }
    }

    public String secKillSuccesses(){
        return "当前服务记载的秒杀成功订单数："+this.consumeRecds.size();
    }

    public List<Order> getOrders(){
        return consumeRecds;
    }
}
