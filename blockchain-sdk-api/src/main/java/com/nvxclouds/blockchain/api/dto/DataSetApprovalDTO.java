package com.nvxclouds.blockchain.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:48
 * @Description:
 */
@Getter
@Setter
public class DataSetApprovalDTO {

    @NotBlank
    private String dataNodeID;
    @NotBlank
    private String nodeName;
    @NotBlank
    private String datasetID;
    @NotBlank
    private String status;
    @NotBlank
    private String txTime;
    @NotBlank
    private String messageHash;
}
