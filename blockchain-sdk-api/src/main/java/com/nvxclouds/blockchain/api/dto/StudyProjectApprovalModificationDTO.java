package com.nvxclouds.blockchain.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:59
 * @Description:
 */
@Data
@NotBlank
public class StudyProjectApprovalModificationDTO {
    private String dataMiningID;
    private String researchProjectID;
    private String status;
    private String txTime;
    private String messageHash;
}
