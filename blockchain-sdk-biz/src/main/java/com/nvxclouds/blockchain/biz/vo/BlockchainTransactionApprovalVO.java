package com.nvxclouds.blockchain.biz.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/7/1 10:24  zhengxing.hu
 * @version 2020/7/1 10:24  1.0.0
 * @file BlockchainTransactionApprovalVO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
@Builder
public class BlockchainTransactionApprovalVO {
    private String transactionId;
    private String approvalTime;
    private String peerNode;
    private String operation;
    private String approvalStatus;
    private String hash;
}
