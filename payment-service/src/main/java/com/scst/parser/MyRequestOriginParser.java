package com.scst.parser;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
//来源解析类
//@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        //来源origin必须在sentinel授权配置的白名单或黑名单里才可对其进行访问控制
        String origin = request.getRequestURI();
        return origin;
    }
}
