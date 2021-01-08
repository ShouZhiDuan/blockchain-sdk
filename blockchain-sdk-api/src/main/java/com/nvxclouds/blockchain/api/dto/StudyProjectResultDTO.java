package com.nvxclouds.blockchain.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 2020/6/23 19:22  zhengxing.hu
 * @version 2020/6/23 19:22  1.0.0
 * @file StudyProjectResultDTO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Data
public class StudyProjectResultDTO {
    @NotBlank
    private String regulatorUserID;
    @NotBlank
    private String researchProjectID;
    @NotBlank
    private String txTime;
    @NotBlank
    private String messageHash;
}
