package com.nvxclouds.blockchain.biz.entity;

import lombok.Data;

@Data
public class AppUser {

    private String operation;
    private String appUserID;
    private String txTime;
    private String messageHash;
    private String researchProjectID;
    private String transactionID;

}
