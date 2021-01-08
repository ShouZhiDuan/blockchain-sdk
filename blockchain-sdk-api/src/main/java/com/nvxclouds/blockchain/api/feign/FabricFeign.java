package com.nvxclouds.blockchain.api.feign;

import com.alibaba.fastjson.JSONObject;
import com.nvxclouds.blockchain.api.dto.BlockChainBaseInfoDTO;
import com.nvxclouds.blockchain.api.query.BaseQuery;
import com.nvxclouds.blockchain.api.support.CommonPage;
import com.nvxclouds.blockchain.api.vo.OrganizationTradeCountVO;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/8 16:33
 * @Description: Fabric原生相关服务
 */
public interface FabricFeign {
    /**
     * 查询平台-区块数量
     */
    @GetMapping(value = "/fabric/queryBlockChainAmount")
    Long queryBlockChainAmount();

    /**
     * 查询平台-交易历史条数
     */
    @GetMapping(value = "/fabric/queryTradeAmount")
    String queryTradeAmount();

    /**
     * 查询平台-链码数量
     */
    @GetMapping(value = "/fabric/queryChainCodeAmount")
    Integer queryChainCodeAmount();

    /**
     * 查询平台-节点数量
     */
    @GetMapping(value = "/fabric/queryPeerAmount")
    Integer queryPeerAmount();

    /**
     * 查询平台-节点名称列表
     */
    @GetMapping(value = "/fabric/qureyPeerNameList")
    List<String> qureyPeerNameList();

    /**
     * 查询平台->全部区块列表->带分页
     */
    @GetMapping(value = "/fabric/queryBlockList")
    CommonPage queryBlockList(@SpringQueryMap BaseQuery query);

    /**
     * 根据->区块高度->查询区块详情
     */
    @GetMapping(value = "/fabric/queryBlockByBlockNumber")
    BlockChainBaseInfoDTO queryBlockByBlockNumber(@RequestParam("blockNumber") Long blockNumber);

    /**
     * 根据->区块高度->查询当前区块下的所有交易列表->带分页
     */
    @GetMapping(value = "/fabric/queryTransactionListByBlockNumber")
    CommonPage queryTransactionListByBlockNumber(@SpringQueryMap BaseQuery query);

    /**
     * 查询平台-区块全部交易列表
     */
    @GetMapping(value = "/fabric/queryTransactionList")
    CommonPage queryTransactionList(@SpringQueryMap BaseQuery query);

    /**
     * 根据transactionId查询交易详情
     */
    @GetMapping(value = "/fabric/queryTransactionDetails")
    Object queryTransactionDetails(@RequestParam("transactionId") String transactionId);


    /**
     * 根据组织分组查询每个组织下的交易数量
     */
    @GetMapping(value = "/fabric/queryTradeCountGroupByOrganization")
    List<OrganizationTradeCountVO> queryTradeCountGroupByOrganization() ;

}
