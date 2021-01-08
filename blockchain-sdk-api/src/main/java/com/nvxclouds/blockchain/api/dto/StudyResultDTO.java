package com.nvxclouds.blockchain.api.dto;

import lombok.Data;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:41
 * @Description:
 */
@Data
public class StudyResultDTO {
    private String dataNodeID;
    private String nodeName;
    private String datasetID;
    private String status;
    private String txTime;
    private String messageHash;
}
