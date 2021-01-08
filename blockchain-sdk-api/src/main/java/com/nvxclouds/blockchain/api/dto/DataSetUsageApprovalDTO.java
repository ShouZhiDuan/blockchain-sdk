package com.nvxclouds.blockchain.api.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 2020/6/23 18:22  zhengxing.hu
 * @version 2020/6/23 18:22  1.0.0
 * @file DataSetUsageApprovalDTO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Data
public class DataSetUsageApprovalDTO {
    private String dataMiningID;
    private String researchProjectID;
    private List<DataSetInfo> datasets;
    private String status;
    private String txTime;
    private String messageHash;

    @Data
    public static class DataSetInfo {
        private String dataNodeID;
        private String datasetID;
        private String datasetHash;
    }
}
