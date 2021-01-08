package com.nvxclouds.blockchain.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 13:37
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSetIncreaseDTO {
    private String dataNodeID;
    private String nodeName;
    private String datasetID;
    private String status;
    private String txTime;
    private String messageHash;
}
