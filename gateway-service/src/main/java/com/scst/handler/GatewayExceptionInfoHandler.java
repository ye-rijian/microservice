package com.scst.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayExceptionInfoHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable e) {
        ResponseData data = null;
        if (e instanceof FlowException) {
            data = new ResponseData(-1, "网关流控规则不通过");
        } else if (e instanceof DegradeException) {
            data = new ResponseData(-2, "网关熔断规则不通过");
        } else if (e instanceof ParamFlowException) {
            data = new ResponseData(-3, "网关热点规则不通过");
        } else if (e instanceof AuthorityException) {
            data = new ResponseData(-4, "网关授权规则不通过");
        }else if (e instanceof SystemBlockException) {
            data = new ResponseData(-5, "网关系统规则不通过");
        }else {
            data = new ResponseData(0,e.getMessage());
        }
        return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).
                body(BodyInserters.fromValue(data));
    }
}

@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor
//无参构造
class ResponseData {
    private int code;
    private String message;
}