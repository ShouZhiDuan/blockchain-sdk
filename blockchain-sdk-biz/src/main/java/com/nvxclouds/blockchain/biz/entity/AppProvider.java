package com.nvxclouds.blockchain.biz.entity;

import lombok.Data;

@Data
public class AppProvider {

    private String operation;
    private String appProviderID;
    private String appID;
    private String txTime;
    private String status;
    private String messageHash;
    private String transactionID;

}
