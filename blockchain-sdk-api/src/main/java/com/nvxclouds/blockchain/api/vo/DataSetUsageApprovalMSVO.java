package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/23 18:19  zhengxing.hu
 * @version 2020/6/23 18:19  1.0.0
 * @file DataSetUsageApprovalVO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
public class DataSetUsageApprovalMSVO extends RegulatorVO<DataSetUsageApprovalMSVO.DataSetUsageApprovalInfoVO> {

    @Getter
    @Setter
    public static class DataSetUsageApprovalInfoVO extends CommonVO{
        private String dataMiningID;
        private String researchProjectID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
