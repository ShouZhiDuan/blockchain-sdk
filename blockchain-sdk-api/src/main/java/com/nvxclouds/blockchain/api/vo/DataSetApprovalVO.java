package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:48
 * @Description:
 */
@Getter
@Setter
public class DataSetApprovalVO extends RegulatorVO<DataSetApprovalVO.DataSetApprovalInfoVO> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    public static class DataSetApprovalInfoVO extends CommonVO{

        private String dataNodeID;
        private String nodeName;
        private String datasetID;
        private String status;
        private String txTime;
        private String messageHash;
        private String dataMiningID;
        private String researchProjectID;
        private String datasets;
        private String appProviderID;
        private String appID;
        private String regulatorUserID;
    }
}
