package com.nvxclouds.blockchain.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/8/24 11:03
 * @Description: 组织交易数量统计数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationTradeCountVO {
    private Long tradeCount;
    private String organizationName;
}
