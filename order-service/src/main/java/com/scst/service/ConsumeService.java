package com.scst.service;

import com.scst.domain.T_order;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//消息消费服务
@Slf4j
@Service
public class ConsumeService {

    @Component
    //topic需要和生产者的topic一致；consumerGroup属性必须指定，内容可随意；messageModel默认为CLUSTERING
    @RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "msg-topic", consumerGroup = "${rocketmq.consumer.group0}", messageModel = MessageModel.BROADCASTING)
    class ConsumerInGroup0 implements RocketMQListener<Object> {
        @Override
        public void onMessage(Object o) {
            //消息体（Payload）o为JSON对象(字符串)
            log.info("C0开始消费消息:{}", o);
        }
    }

    @Component
    //topic需要和生产者的topic一致；consumerGroup属性必须指定，内容可随意；consumeMode默认为CONCURRENTLY
    @RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "msg-topic", consumerGroup = "${rocketmq.consumer.group1}", consumeMode = ConsumeMode.ORDERLY)
    class ConsumerInGroup1 implements RocketMQListener<T_order> {
        @Override
        public void onMessage(T_order o) {
            //消息体（Payload）o为Java对象
            log.info("C1开始消费消息:{}", o);
        }
    }

    @Component
    //topic需要和生产者的topic一致；consumerGroup属性必须指定，内容可随意；consumeMode默认为CONCURRENTLY；messageModel默认为CLUSTERING
    @RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "msg-topic", consumerGroup = "${rocketmq.consumer.group2}")
    class ConsumerInGroup2 implements RocketMQListener<T_order> {
        @Override
        public void onMessage(T_order o) {
            //消息体（Payload）o为Java对象
            log.info("C2开始消费消息:{}", o);
        }
    }
}