package com.nvxclouds.blockchain.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:54
 * @Description:
 */
@Getter
@Setter
public class DataSetApprovalModificationDTO {
    private String dataNodeID;
    private String nodeName;
    private String datasetID;
    private String status;
    private String txTime;
    private String messageHash;
}
