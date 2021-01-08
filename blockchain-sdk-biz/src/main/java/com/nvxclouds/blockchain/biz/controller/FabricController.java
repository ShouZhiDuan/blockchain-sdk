package com.nvxclouds.blockchain.biz.controller;

import com.nvxclouds.blockchain.api.dto.BlockChainBaseInfoDTO;
import com.nvxclouds.blockchain.api.feign.FabricFeign;
import com.nvxclouds.blockchain.api.query.BaseQuery;
import com.nvxclouds.blockchain.api.support.CommonPage;
import com.nvxclouds.blockchain.biz.service.FabricService;
import com.nvxclouds.blockchain.api.vo.OrganizationTradeCountVO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/8 17:00
 * @Description:
 */

@RestController
@RequestMapping(value = "/fabric")
public class FabricController implements FabricFeign {

                @Autowired
                private FabricService fabricService;


                /**
                 * 查询平台-区块数量
                 */
                @SneakyThrows
                @GetMapping(value = "/queryBlockChainAmount")
                public Long queryBlockChainAmount() {
                    return fabricService.queryBlockChainAmount();
                }

                /**
                 * 查询平台-交易历史条数
                 */
                @SneakyThrows
                @GetMapping(value = "/queryTradeAmount")
                public String queryTradeAmount() {
                    return fabricService.queryTradeAmount();
                }

                /**
                 * 查询平台-链码数量
                 */
                @SneakyThrows
                @GetMapping(value = "/queryChainCodeAmount")
                public Integer queryChainCodeAmount() {
                    return fabricService.queryChainCodeAmount();
                }


                /**
                 * 查询平台-节点数量
                 */
                @GetMapping(value = "/queryPeerAmount")
                public Integer queryPeerAmount() {
                    return fabricService.queryPeerAmount();
                }

                /**
                 * 查询平台-节点名称列表
                 */
                @GetMapping(value = "/qureyPeerNameList")
                public List<String> qureyPeerNameList() {
                    return fabricService.qureyPeerNameList();
                }

                /**
                 * 查询平台->全部区块列表->带分页
                 */
                @SneakyThrows
                @GetMapping(value = "/queryBlockList")
                public CommonPage queryBlockList(BaseQuery query) {
                    return fabricService.queryBlockList(query);
                }

                /**
                 * 根据->区块高度->查询区块详情
                 */
                @SneakyThrows
                @GetMapping(value = "/queryBlockByBlockNumber")
                public BlockChainBaseInfoDTO queryBlockByBlockNumber(@RequestParam("blockNumber") Long blockNumber) {
                    return fabricService.queryBlockByBlockNumber(blockNumber);
                }

                /**
                 * 根据->区块高度->查询当前区块下的所有交易列表->带分页
                 */
                @SneakyThrows
                @GetMapping(value = "/queryTransactionListByBlockNumber")
                public CommonPage queryTransactionListByBlockNumber(BaseQuery query) {
                    return fabricService.queryTransactionListByBlockNumber(query);
                }

                /**
                 * 查询平台-区块全部交易列表
                 */
                @SneakyThrows
                @GetMapping(value = "/queryTransactionList")
                public CommonPage queryTransactionList(BaseQuery query) {
                    return fabricService.queryTransactionList(query);
                }

                /**
                 * 根据transactionId查询交易详情
                 */
                @SneakyThrows
                @GetMapping(value = "/queryTransactionDetails")
                public Object queryTransactionDetails(@RequestParam("transactionId") String transactionId) {
                    return fabricService.queryTransactionDetails(transactionId);
                }


                /**
                 * 根据组织分组查询每个组织下的交易数量
                 */
                @SneakyThrows
                @GetMapping(value = "/queryTradeCountGroupByOrganization")
                public List<OrganizationTradeCountVO> queryTradeCountGroupByOrganization() {
                    return fabricService.queryTradeCountGroupByOrganization();
                }






}
