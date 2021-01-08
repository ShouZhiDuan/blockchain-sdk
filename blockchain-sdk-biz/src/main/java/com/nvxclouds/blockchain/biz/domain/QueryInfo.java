package com.nvxclouds.blockchain.biz.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "query_info")
public class QueryInfo implements Serializable {

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
    @Column(name = "query_param")
    private String queryParam;
    @Column(name = "succeed")
    private Integer succeed;

}
