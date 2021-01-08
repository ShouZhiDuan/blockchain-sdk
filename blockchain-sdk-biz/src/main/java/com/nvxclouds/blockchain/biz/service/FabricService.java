package com.nvxclouds.blockchain.biz.service;

import com.nvxclouds.blockchain.api.dto.BlockChainBaseInfoDTO;
import com.nvxclouds.blockchain.api.query.BaseQuery;
import com.nvxclouds.blockchain.api.support.CommonPage;
import com.nvxclouds.blockchain.api.vo.OrganizationTradeCountVO;

import java.util.List;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/6 13:21
 * @Description: 区块链原生操作服务
 */
public interface FabricService {

    /**
     * 查询平台-区块数量
     */
    Long queryBlockChainAmount() throws Exception;

    /**
     * 查询平台-交易历史条数
     */
    String queryTradeAmount() throws Exception;

    /**
     * 查询平台-链码数量
     */
    Integer queryChainCodeAmount() throws Exception;

    /**
     * 查询平台-节点数量
     */
    Integer queryPeerAmount();

    /**
     * 查询平台-节点名称列表
     */
    List<String> qureyPeerNameList();

    /**
     * 查询平台->全部区块列表->带分页
     */
    CommonPage queryBlockList(BaseQuery query) throws Exception;

    /**
     * 根据->区块高度->查询区块详情
     */
    BlockChainBaseInfoDTO queryBlockByBlockNumber(Long blockNumber) throws Exception;

    /**
     * 根据->区块高度->查询当前区块下的所有交易列表->带分页
     */
    CommonPage queryTransactionListByBlockNumber(BaseQuery query) throws Exception;

    /**
     * 查询平台-区块全部交易列表
     */
    CommonPage queryTransactionList(BaseQuery query) throws Exception;

    /**
     * 根据transactionId查询交易详情
     */
    Object queryTransactionDetails(String transactionId) throws Exception;

    /**
     * 根据组织分组查询每个组织下的交易数量
     * queryTradeCountGroupByOrganization
     */
    List<OrganizationTradeCountVO> queryTradeCountGroupByOrganization() throws Exception;


    /**
     * 按照前1小时统计每5分钟的交易数据
     */
    List<Long> querTradeCountGroupBy5Mins(Long time);


    /**
     * 按照前1小时统计每5分钟的交易数据
     */
    List<Long> querTradeCountGroup1Hour(Long time);

}
