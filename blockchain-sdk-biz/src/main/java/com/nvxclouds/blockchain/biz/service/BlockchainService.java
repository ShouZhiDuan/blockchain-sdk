package com.nvxclouds.blockchain.biz.service;

import com.nvxclouds.blockchain.biz.query.BlockchainTransactionApprovalQuery;
import com.nvxclouds.blockchain.biz.vo.BlockchainTransactionApprovalVO;
import com.nvxclouds.common.base.pojo.Pagination;

/**
 * @author 2020/7/1 10:26  zhengxing.hu
 * @version 2020/7/1 10:26  1.0.0
 * @file BlockchainService
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public interface BlockchainService {
    Pagination<BlockchainTransactionApprovalVO> queryBlockchainTransactionApproval(BlockchainTransactionApprovalQuery query);
}
