package com.nvxclouds.blockchain.biz.controller;

import com.nvxclouds.blockchain.biz.query.BlockchainTransactionApprovalQuery;
import com.nvxclouds.blockchain.biz.query.BlockchainTransactionListQuery;
import com.nvxclouds.blockchain.biz.query.BlockchainTransactionQuery;
import com.nvxclouds.blockchain.biz.service.BlockchainService;
import com.nvxclouds.blockchain.biz.vo.*;
import com.nvxclouds.common.base.pojo.BaseResult;
import com.nvxclouds.common.base.pojo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 2020/7/1 10:16  zhengxing.hu
 * @version 2020/7/1 10:16  1.0.0
 * @file BlockchainController
 * @brief 区块链相关业务 controller
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@RestController
@RequestMapping("/blockchain")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;


    @GetMapping("/transaction/approval")
    public BaseResult<Pagination<BlockchainTransactionApprovalVO>> queryBlockchainTransactionApproval(BlockchainTransactionApprovalQuery query) {
        Pagination<BlockchainTransactionApprovalVO> blockchainTransactionApprovals =  blockchainService.queryBlockchainTransactionApproval(query);
        return BaseResult.ok(blockchainTransactionApprovals);
    }

    @GetMapping("/transaction")
    public BaseResult<List<BlockchainTransactionVO>> queryBlockchainTransaction(BlockchainTransactionQuery query) {
        return BaseResult.ok();
    }

    @GetMapping("/transaction/list")
    public BaseResult<Pagination<BlockchainTransactionListVO>> queryBlockchainTransactionList(BlockchainTransactionListQuery query) {
        return BaseResult.ok();
    }

    @GetMapping("/transaction/{transactionId}")
    public BaseResult<BlockchainTransactionInfoVO> queryBlockchainTransactionInfo(@PathVariable(value = "transactionId") String transactionId) {
        return BaseResult.ok();
    }

    @GetMapping("/block")
    public BaseResult<List<BlockchainBlockVO>> queryBlockchainBlock() {
        return BaseResult.ok();
    }

    @GetMapping("/block/list")
    public BaseResult<List<BlockchainBlockListVO>> queryBlockchainBlockList() {
        return BaseResult.ok();
    }

    @GetMapping("/block/{blockHeight}")
    public BaseResult<BlockchainBlockInfoVO> queryBlockchainBlock(@PathVariable String blockHeight) {
        return BaseResult.ok();
    }

}
