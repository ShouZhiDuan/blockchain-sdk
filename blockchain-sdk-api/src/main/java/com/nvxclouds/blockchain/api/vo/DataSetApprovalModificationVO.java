package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:53
 * @Description:
 */
@Getter
@Setter
public class DataSetApprovalModificationVO extends RegulatorVO<DataSetApprovalModificationVO.DataSetApprovalModificationInfoVO> implements Serializable {
    @Getter
    @Setter
    public static class DataSetApprovalModificationInfoVO extends CommonVO{
        private String dataNodeID;
        private String nodeName;
        private String datasetID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
