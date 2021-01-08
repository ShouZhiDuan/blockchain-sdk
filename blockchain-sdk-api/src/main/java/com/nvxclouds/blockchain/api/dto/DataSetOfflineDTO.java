package com.nvxclouds.blockchain.api.dto;

import lombok.Data;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:38
 * @Description:
 */
@Data
public class DataSetOfflineDTO {
    private String dataNodeID;
    private String nodeName;
    private String datasetID;
    private String status;
    private String txTime;
    private String messageHash;
}
