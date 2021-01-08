package com.nvxclouds.blockchain.biz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/8/25 11:26
 * @Description:
 */
@Table(name = "trade_info")
public class TradeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;
    @JsonProperty
    @Column(name = "Status")
    private String Status;
    @JsonProperty
    @Column(name = "OrganizationName")
    private String OrganizationName;

    @Column(name = "blockHeight")
    private Long blockHeight;
    @JsonProperty
    @Column(name = "NodeName")
    private String NodeName;
    @JsonProperty
    @Column(name = "TxTime")
    private String TxTime;
    @JsonProperty
    @Column(name = "Operation")
    private String Operation;
    @JsonProperty
    @Column(name = "MessageHash")
    private String MessageHash;
    @JsonProperty
    @Column(name = "DataNodeID")
    private String DataNodeID;
    @JsonProperty
    @Column(name = "DatasetID")
    private String DatasetID;
    @JsonProperty
    @Column(name = "TransactionID")
    private String TransactionID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public String getStatus() {
        return Status;
    }
    @JsonIgnore
    public void setStatus(String status) {
        Status = status;
    }
    @JsonIgnore
    public String getOrganizationName() {
        return OrganizationName;
    }
    @JsonIgnore
    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    public Long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Long blockHeight) {
        this.blockHeight = blockHeight;
    }
    @JsonIgnore
    public String getNodeName() {
        return NodeName;
    }
    @JsonIgnore
    public void setNodeName(String nodeName) {
        NodeName = nodeName;
    }
    @JsonIgnore
    public String getTxTime() {
        return TxTime;
    }
    @JsonIgnore
    public void setTxTime(String txTime) {
        TxTime = txTime;
    }
    @JsonIgnore
    public String getOperation() {
        return Operation;
    }
    @JsonIgnore
    public void setOperation(String operation) {
        Operation = operation;
    }
    @JsonIgnore
    public String getMessageHash() {
        return MessageHash;
    }
    @JsonIgnore
    public void setMessageHash(String messageHash) {
        MessageHash = messageHash;
    }
    @JsonIgnore
    public String getDataNodeID() {
        return DataNodeID;
    }
    @JsonIgnore
    public void setDataNodeID(String dataNodeID) {
        DataNodeID = dataNodeID;
    }
    @JsonIgnore
    public String getDatasetID() {
        return DatasetID;
    }
    @JsonIgnore
    public void setDatasetID(String datasetID) {
        DatasetID = datasetID;
    }
    @JsonIgnore
    public String getTransactionID() {
        return TransactionID;
    }
    @JsonIgnore
    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }
}
