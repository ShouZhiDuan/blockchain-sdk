package com.nvxclouds.blockchain.biz.entity;

import lombok.Data;

@Data
public class DataProvider {

    private String operation;
    private String dataNodeID;
    private String txTime;
    private String nodeName;
    private String messageHash;
    private String datasetID;
    private String status;
    private String transactionID;

}
