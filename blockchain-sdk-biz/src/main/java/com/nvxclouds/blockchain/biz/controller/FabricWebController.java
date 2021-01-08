package com.nvxclouds.blockchain.biz.controller;

import com.nvxclouds.blockchain.api.dto.BlockChainBaseInfoDTO;
import com.nvxclouds.blockchain.api.query.BaseQuery;
import com.nvxclouds.blockchain.api.support.CommonPage;
import com.nvxclouds.blockchain.biz.query.BlockchainTransactionApprovalQuery;
import com.nvxclouds.blockchain.biz.service.BlockchainService;
import com.nvxclouds.blockchain.biz.service.FabricService;
import com.nvxclouds.blockchain.biz.vo.BlockchainTransactionApprovalVO;
import com.nvxclouds.common.base.pojo.BaseResult;
import com.nvxclouds.common.base.pojo.Pagination;
import com.nvxclouds.common.core.annotation.Log;
import com.nvxclouds.common.core.enums.LogTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/8 17:00
 * @Description:
 */

@RestController
@RequestMapping(value = "/v1/fabric")
public class FabricWebController  {

                @Autowired
                private FabricService fabricFeignClient;

                @Autowired
                private BlockchainService blockchainService;


                /**
                 *区块及交易->监管方审批操作
                 */
                @GetMapping("/approval")
                //@Permission(name = "fabric:queryBlockchainTransactionApproval")
                public BaseResult<Pagination<BlockchainTransactionApprovalVO>> queryBlockchainTransactionApproval(BlockchainTransactionApprovalQuery query) {
                    Pagination<BlockchainTransactionApprovalVO> blockchainTransactionApprovals =  blockchainService.queryBlockchainTransactionApproval(query);
                    return BaseResult.ok(blockchainTransactionApprovals);
                }

                /**
                 * 查询平台-分页查询区块列表
                 */
                @Log(name = "区块链-分页查询区块列表", biz = LogTypeEnum.BLOCKCHAIN)
                @GetMapping("/queryBlockList")
                //@Permission(name = "fabric:queryBlockList")
                public BaseResult queryBlockList(BaseQuery query) throws Exception {
                    CommonPage page = fabricFeignClient.queryBlockList(query);
                    return  BaseResult.ok(page);
                }

                /**
                 * 查询平台-区块详情
                 */
                @Log(name = "区块链-查询区块详情", biz = LogTypeEnum.BLOCKCHAIN)
                @GetMapping("/queryBlockByBlockNumber")
                //@Permission(name = "fabric:queryBlockByBlockDetails")
                public BaseResult queryBlockByBlockNumber(Long blockNumber) throws Exception {
                    BlockChainBaseInfoDTO dto = fabricFeignClient.queryBlockByBlockNumber(blockNumber);
                    return  BaseResult.ok(dto);
                }

                /**
                 * 查询平台-分页查询当前区块下的所有交易列表(区块内全部交易)
                 */
                @Log(name = "区块链-分页查询当前区块下的所有交易列表", biz = LogTypeEnum.BLOCKCHAIN)
                @GetMapping("/queryTransactionListByBlockNumber")
                //@Permission(name = "fabric:queryTransactionListByBlockNumber")
                public BaseResult queryTransactionListByBlockNumber(BaseQuery query) throws Exception {
                    CommonPage psge = fabricFeignClient.queryTransactionListByBlockNumber(query);
                    return  BaseResult.ok(psge);
                }

                /**
                 * 查询平台-分页查询系统全部交易列表
                 */
                @Log(name = "区块链-分页查询系统全部交易列表", biz = LogTypeEnum.BLOCKCHAIN)
                @GetMapping("/queryTransactionList")
                //@Permission(name = "fabric:queryTransactionList")
                public BaseResult queryTransactionList(BaseQuery query) throws Exception {
                    CommonPage psge = fabricFeignClient.queryTransactionList(query);
                    return  BaseResult.ok(psge);
                }

                /**
                 * 查询平台-根据transactionId查询交易详情(单笔交易详情)
                 */
                @Log(name = "区块链-根据transactionId查询交易详情", biz = LogTypeEnum.BLOCKCHAIN)
                @GetMapping("/queryTransactionDetails")
                //@Permission(name = "fabric:transactionDetails")
                public BaseResult queryTransactionDetails(@RequestParam @NotBlank(message = "transactionId不能为空") String transactionId) throws Exception {
                    Object details = fabricFeignClient.queryTransactionDetails(transactionId);
                    return  BaseResult.ok(details);
                }

                /**
                 * 查询平台-前1小时每5分钟交易统计数据
                 */
                @Log(name = "区块链-前1小时每5分钟交易统计数据", biz = LogTypeEnum.BLOCKCHAIN)
                @GetMapping("/querTradeCountGroupBy5Mins")
                public BaseResult querTradeCountGroupBy5Mins(){
                    List<Long> list = fabricFeignClient.querTradeCountGroupBy5Mins(System.currentTimeMillis());
                    return  BaseResult.ok(list);
                }

                /**
                 * 查询平台-前12小时的交易数据统计
                 */
                @Log(name = "区块链-前12小时的交易数据统计", biz = LogTypeEnum.BLOCKCHAIN)
                @GetMapping("/querTradeCountGroup1Hour")
                public BaseResult querTradeCountGroup1Hour(){
                    List<Long> list = fabricFeignClient.querTradeCountGroup1Hour(System.currentTimeMillis());
                    return  BaseResult.ok(list);
                }
}
