package com.scst.service;

import com.scst.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Slf4j
@Service
public class OrderingService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    private String destination;
    private Object payload;
    public boolean purchase(String productId,String accountId){
        return true;
    }

    //3.发送异步消息
    public void sendAsyncMessage(String topic, String tag) {
        this.destination = topic;
        if (tag != null) {
            this.destination = topic + ":" + tag;
        }
        this.payload = new Order("O01", "U01", "P01", 2, false, new Date());
        rocketMQTemplate.asyncSend(this.destination, this.payload, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("【异步发送结果】{}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("【异步发送异常】{}", throwable.getMessage());
            }
        });
    }

    //4.发送顺序消息
    public void sendOrderedMessages(String topic, String tag) {
        this.destination = topic;
        if (tag != null) {
            this.destination = topic + ":" + tag;
        }
        for (int i = 0; i < 10; i++) {
            //hashkey用于选择消息队列，只有在相同队列的消息能保持顺序
            rocketMQTemplate.syncSendOrderly(this.destination, new Order("O0" + i, "U01", "P01", 2, false, new Date()), "hashKey");
        }
    }

    //5.发送批量消息
    public void sendBatchMessages(String topic, String tag) {
        this.destination = topic;
        if (tag != null) {
            this.destination = topic + ":" + tag;
        }
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.payload = new Order("O0" + i, "U01", "P01", 2, false, new Date());
            Message<Object> message = MessageBuilder.withPayload(this.payload).build();
            messages.add(message);
        }
        SendResult sendResult = rocketMQTemplate.syncSend(this.destination, messages, 1000);
        log.info("【批量发送结果】{}", sendResult);
    }

    //6.发送延迟消息
    public void sendDelayMessage(String topic, String tag) {
        this.destination = topic;
        if (tag != null) {
            this.destination = topic + ":" + tag;
        }
        this.payload = new Order("O01", "U01", "P01", 2, false, new Date());
        Message<Object> message = MessageBuilder.withPayload(this.payload).build();
        //设置延迟时间为10s(1s/5s/10s/30s/1m/2m/3m/4m/5m/6m/7m/8m/9m/10m/20m/30m/1h/2h)
        SendResult sendResult = rocketMQTemplate.syncSend(this.destination, message, 100000, 3);
        log.info("【延迟发送结果】{}", sendResult);
    }

    //7.发送事务消息
    public void sendTransactionMessage(String topic, String tag) {
        this.destination = topic;
        if (tag != null) {
            this.destination = topic + ":" + tag;
        }
        this.payload = new Order("O01", "U01", "P01", 2, false, new Date());
        Message<Object> message = MessageBuilder.withPayload(this.payload).build();
        log.info("【发送半消息】{}", message.getPayload());
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(this.destination, message, this.payload);
        log.info("【本地事务状态】{}", result.getLocalTransactionState());
    }

    //声明本地事务监听器
    @RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
    class ProducerLocalTransactionListener implements RocketMQLocalTransactionListener {
        private ConcurrentHashMap<String, Object> localTrans = new ConcurrentHashMap<>();

        //半消息投递成功后执行的逻辑
        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
            try {
                log.info("【收到半消息响应ACK，执行本地事务：】");
                log.info("Message:{}", message);
                log.info("Object:{}", o);
                localTrans.put(message.getHeaders().getId() + "", message.getPayload());
                return RocketMQLocalTransactionState.UNKNOWN;
                //return RocketMQLocalTransactionState.COMMIT;
            } catch (Exception e) {
                e.printStackTrace();
                log.error("【执行本地事务异常】 Exception:{}", e.getMessage());
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        }

        //回查本地事务执行状态
        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
            log.info("【检查本地事务状态】");
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
}
