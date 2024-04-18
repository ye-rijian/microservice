package com.scst.predicates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//自定义路由断言工厂类,要求：
//1 类名必须是：配置名+RoutePredicateFactory
//2 必须继承AbstractRoutePredicateFactory<配置值接收类>
@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {

    //构造函数
    public AgeRoutePredicateFactory() {
        super(Config.class);
    }

    //读取配置文件的中参数值，并将其赋值到配置值接收类的属性上
    @Override
    public List<String> shortcutFieldOrder() {
        //这里参数名顺序必须跟配置文件中值的顺序对应
        return Arrays.asList("minAge", "maxAge");
    }

    //断言逻辑
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                //1 接收访问路径中的age参数
                String ageStr = serverWebExchange.getRequest().getQueryParams().getFirst("age");

                //2 先判断是否为空
                if (StringUtils.isNotEmpty(ageStr)) {
                    //3 如果不为空,再进行路由逻辑判断
                    int age = Integer.parseInt(ageStr);
                    if (age <= config.getMaxAge() && age >= config.getMinAge()) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        };
    }

    //配置值接收类,用于接收配置文件中对应参数值
    @Data
    @NoArgsConstructor
    public static class Config {
        private int minAge;
        private int maxAge;
    }
}