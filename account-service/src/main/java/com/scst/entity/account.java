package com.scst.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
@Data
public class account extends Model<account> {
    private Integer id;
    private String aId;
    private float balance;
}
