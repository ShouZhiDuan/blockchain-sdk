package com.nvxclouds.blockchain.api.query;

import lombok.*;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/1 13:54
 * @Description:
 */
@Getter
@Setter
public class BaseQuery {
     private Integer page = 1; //查询第几页
     private Integer perPage = 20; //每页显示几条
     private Long blockNumber;//区块高度
}
