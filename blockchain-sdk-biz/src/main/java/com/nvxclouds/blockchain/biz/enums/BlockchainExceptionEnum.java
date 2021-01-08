package com.nvxclouds.blockchain.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/5/25 11:28
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum BlockchainExceptionEnum {
    BLOCKCHAIN_INVOKE_FAILED(1020001, "数据上链失败"),
    BLOCKCHAIN_QUERY_FAILED(1020002, "区块链数据查询失败"),
    TX_TIME_INVALID(1020003, "txTime格式有误");
    private final int code;
    private final String msg;
}
