package com.nvxclouds.blockchain.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:57
 * @Description:
 */
@Getter
@Setter
@NotBlank
public class StudyProjectApprovalDTO {
    private String dataMiningID;
    private String researchProjectID;
    private String status;
    private String txTime;
    private String messageHash;
}
