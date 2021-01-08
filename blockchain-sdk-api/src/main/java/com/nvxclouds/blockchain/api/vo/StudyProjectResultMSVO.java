package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/23 19:20  zhengxing.hu
 * @version 2020/6/23 19:20  1.0.0
 * @file StudyProjectResultVO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
public class StudyProjectResultMSVO extends RegulatorVO<StudyProjectResultMSVO.StudyProjectResultInfoVO>{

    @Getter
    @Setter
    public static class StudyProjectResultInfoVO extends CommonVO{
        private String regulatorUserID;
        private String researchProjectID;
        private String txTime;
        private String messageHash;
    }
}
