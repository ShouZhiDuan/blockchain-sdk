package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/24 09:30  zhengxing.hu
 * @version 2020/6/24 09:30  1.0.0
 * @file LicenseActivationVO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
public class LicenseActivationVO extends DataDemandVO<LicenseActivationVO.LicenseActivationInfoVO> {
    @Getter
    @Setter
    public static class LicenseActivationInfoVO extends CommonVO{
        private String dataMiningID;
        private String certificateID;
        private String txTime;
        private String messageHash;
    }
}
