package com.nvxclouds.blockchain.api.dto;

import lombok.Data;

/**
 * @author 2020/6/24 09:25  zhengxing.hu
 * @version 2020/6/24 09:25  1.0.0
 * @file LicensePurchase
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Data
public class LicensePurchaseDTO {
    private String dataMiningID;
    private String certificateID;
    private String txTime;
    private String messageHash;
}
