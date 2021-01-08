package com.nvxclouds.blockchain.biz.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "invoke_info")
public class InvokeInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "status")
    private Integer status;
    @Column(name = "comment")
    private String comment;
    @Column(name = "operation")
    private String operation;
    @Column(name = "function_name")
    private String functionName;
    @Column(name = "content")
    private String content;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "succeed")
    private Integer succeed;
    @Column(name = "peer_node")
    private String peerNode;
    @Column(name = "peer_url")
    private String peerUrl;
    @Column(name = "order_node")
    private String orderNode;
    @Column(name = "order_url")
    private String orderUrl;
    @Column(name = "hash")
    private String hash;
    @Column(name = "param")
    private String param;


}
