package com.scst.filters;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//自定义全局过滤器，需要实现GlobalFilter和Ordered接口
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    //完成判断逻辑
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(JSONArray.toJSONString(exchange.getRequest().getQueryParams()));
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (!StringUtils.equals(token, "admin")) {
            System.out.println("鉴权失败");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //添加token请求头
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("token", "admin","123456").build();
        //调用chain.filter继续向下游执行
        return chain.filter(exchange.mutate().request(request).build());
    }

    //顺序,数值越小,优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}