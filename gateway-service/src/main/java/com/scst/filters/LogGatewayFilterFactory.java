package com.scst.filters;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//自定义局部过滤器,要求：
//1 类名必须是：配置名+GatewayFilterFactory
//2 必须继承AbstractGatewayFilterFactory<配置值接收类>
@Component
public class LogGatewayFilterFactory
        extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    //配置值接收类,用于接收配置文件中对应参数值
    @Data
    @NoArgsConstructor
    public static class Config {
        private boolean consoleLog;
        private boolean cacheLog;
    }
    //构造函数
    public LogGatewayFilterFactory() {
        super(Config.class);
    }

    //读取配置文件的中参数值，并将其赋值到配置值接收类的属性上
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog", "cacheLog");
    }

    //过滤器逻辑
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if (config.isCacheLog()) {
                    System.out.println("cacheLog已经开启了....");
                }
                if (config.isConsoleLog()) {
                    System.out.println("consoleLog已经开启了....");
                }
                return chain.filter(exchange);
            }
        };
    }
}