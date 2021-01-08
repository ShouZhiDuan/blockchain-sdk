package com.nvxclouds.blockchain.api.dto;

import lombok.Data;

/**
 * @author 2020/6/24 09:32  zhengxing.hu
 * @version 2020/6/24 09:32  1.0.0
 * @file LicenseActivationDTO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Data
public class LicenseActivationDTO {
    private String dataMiningID;
    private String certificateID;
    private String txTime;
    private String messageHash;
}
