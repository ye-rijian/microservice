package com.scst.service;

import com.scst.domain.Order;
import com.scst.domain.Stock;
import com.scst.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//秒杀服务
@Slf4j
@Service
public class SecKillService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedissonClient redisson;
    @Autowired
    private Stock stock;
    private String destination;
    private Object payload;
    public static final String SEC_KILL_GOODS_KEY = "secondsKillGoods";

    //商品秒杀
    public void secKill(String topic, String tag) throws InterruptedException{
        String userId="User"+buildRandomUserId();
        String productId=stock.getProductId();
        this.destination = topic;
        if (tag != null) {
            this.destination = topic + ":" + tag;
        }
        //分布式锁
        RLock lock = redisson.getLock("seckill:" + productId);
        //尝试加锁，最多等待10s，上锁以后30s自动解锁
        boolean res = lock.tryLock(10, 30, TimeUnit.SECONDS);
        //lock.lock();
        //Future<Boolean> res = lock.tryLockAsync(100, 10, TimeUnit.SECONDS);
        if(res) {
            this.payload = createOrder(productId, userId);
            if (this.payload != null) {
                //rocketMQTemplate.syncSendOrderly(this.destination, this.payload,"hashKey");
                rocketMQTemplate.syncSend(this.destination, this.payload);
            }
            //只有加锁成功才需要解锁，且自己加的锁自己解，不能解别人的锁
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    //生成订单
    private Order createOrder(String productId, String userId) {
        //获取操作redis hash类型的操作类
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(SEC_KILL_GOODS_KEY);
        //从Redis获得秒杀商品信息
        Integer amount = (Integer) hashOperations.get(productId);
        if (amount == null || amount <= 0) {
            return null;
        }
        //redis预扣库存
        Long value = hashOperations.increment(productId, -1);
        if (value <= 0) {
            // 如果扣完后库存为0，则删除当前商品
            hashOperations.delete(productId);
        }
        Order order = new Order();
        order.setOrderId(String.valueOf(idWorker.nextId()));
        order.setUserId(userId);
        order.setProductId(productId);
        order.setPurchaseNum(1);
        order.setCreateTime(new Date());
        order.setStatus(false);
        return order;
    }

    private Integer buildRandomUserId(){
        return ThreadLocalRandom.current().nextInt(100000) + 50;
    }

    //模块一旦启动就往redis中加载秒杀商品
    @EventListener
    public void contextRefreshedEventListener(ContextRefreshedEvent contextRefreshedEvent) {
        if(redisTemplate == null){
            return;
        }
        System.out.println("往redis中加载秒杀商品开始！");
        //获取操作redis hash类型的操作类
        BoundHashOperations<String, Object, Object> hashOperations=redisTemplate.boundHashOps(SEC_KILL_GOODS_KEY);
        //往redis中加载数据
        hashOperations.put(stock.getProductId(),stock.getStockNum());
        System.out.println("往redis中加载秒杀商品成功！");
    }
}
