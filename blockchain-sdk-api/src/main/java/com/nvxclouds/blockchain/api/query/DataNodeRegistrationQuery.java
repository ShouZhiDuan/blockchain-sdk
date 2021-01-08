package com.nvxclouds.blockchain.api.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 11:42
 * @Description:
 */
@Getter
@Setter
public class DataNodeRegistrationQuery extends PageQuery{
    private String dataNodeID;
    private String nodeName;
    private String txTime;
    private String messageHash;
    private String status;
    private String datasetID;
}
