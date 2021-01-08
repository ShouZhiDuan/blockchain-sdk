package com.nvxclouds.blockchain.api.dto;

import lombok.Data;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/8 10:56
 * @Description:
 */
@Data
public class BlockChainBaseInfoDTO {
    //区块高度、当前区块下有多少条交易、父Hash、当前Hash

    private Long blockNumber;//区块高度

    private Integer transactionCount;//当前区块交易的条数

    private String  dataHash;//当前hash值

    private String  previousHashID;//父hash值

    private String  channelId;//通道名称

}
