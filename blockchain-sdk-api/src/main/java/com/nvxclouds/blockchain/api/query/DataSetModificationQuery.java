package com.nvxclouds.blockchain.api.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:21
 * @Description:
 */
@Getter
@Setter
public class DataSetModificationQuery extends PageQuery{
    private String dataNodeID;
    private String nodeName;
    private String datasetID;
    private String status;
    private String txTime;
    private String messageHash;
}
