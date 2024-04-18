package com.scst.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyBlockExceptionInfoHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse
            response, BlockException e) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ResponseData data = null;
        if (e instanceof FlowException) {
            data = new ResponseData(-1, "流控规则不通过");
        } else if (e instanceof DegradeException) {
            data = new ResponseData(-2, "熔断规则不通过");
        } else if (e instanceof ParamFlowException) {
            data = new ResponseData(-3, "热点规则不通过");
        } else if (e instanceof AuthorityException) {
            data = new ResponseData(-4, "授权规则不通过");
        }else if (e instanceof SystemBlockException) {
            data = new ResponseData(-5, "系统规则不通过");
        }
        response.getWriter().write(JSON.toJSONString(data));
    }
}

@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
class ResponseData {
    private int code;
    private String message;
}


