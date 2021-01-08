package com.nvxclouds.blockchain.biz.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/8/24 13:28
 * @Description: 区块详情信息
 */
@Data
@Table(name = "block_chain_info")
public class BlockChainInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;
    //当前区块高度
    @Column(name = "block_number")
    private Long blockNumber;
    //当前区块的交易总数
    @Column(name = "transaction_count")
    private Integer transactionCount;
    //当前区块的hash值
    @Column(name = "data_hash")
    private String dataHash;
    //父节点Hash值
    @Column(name = "previous_hash_id")
    private String previousHashID;
    //通道名称
    @Column(name = "channel_id")
    private String channelId;





}
