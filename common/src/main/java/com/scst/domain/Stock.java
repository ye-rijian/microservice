package com.scst.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Component
@Data
public class Stock {
    private String productId = "NO005";
    private String productName = "苹果";
    private Integer stockNum = 100;        //初始库存量
}
