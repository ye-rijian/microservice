package com.scst.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scst.entity.stock;
import com.scst.mapper.stockMapper;
import com.scst.service.stockService;
import com.scst.service.stockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Slf4j
@Service
public class StockServiceImpl implements stockService {

    public boolean deduct(String productId){
        log.info("stock ing...");
        stock s = new stock();
        QueryWrapper<stock> wrapper = new QueryWrapper<>();
        wrapper.eq("p_id",productId);
        stock st = s.selectOne(wrapper);

        if (st != null) {
            Integer count = st.getCount()-10;
            st.setCount(count);
            st.updateById();
            return true;
        }

        System.out.println("不存在该商品");
        return false;
    }
}
