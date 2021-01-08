package com.nvxclouds.blockchain.biz.entity;

import lombok.Data;

@Data
public class Regulator {

    private String operation;
    private String dataNodeID;
    private String nodeName;
    private String datasetID;
    private String txTime;
    private String status;
    private String messageHash;
    private String transactionID;

    private String dataMiningID;
    private String researchProjectID;
    private String datasets;

    private String appProviderID;
    private String appID;

    private String regulatorUserID;
}
