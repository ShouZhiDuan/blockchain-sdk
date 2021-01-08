package com.nvxclouds.blockchain.api.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/24 09:24  zhengxing.hu
 * @version 2020/6/24 09:24  1.0.0
 * @file LicensePurchaseQuery
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
public class LicensePurchaseQuery extends PageQuery{
    private String dataMiningID;
    private String certificateID;
    private String txTime;
    private String messageHash;
}
