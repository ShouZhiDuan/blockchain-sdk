package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/23 20:26  zhengxing.hu
 * @version 2020/6/23 20:26  1.0.0
 * @file StudyProjectVO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Setter
@Getter
public class StudyProjectVO extends DataDemandVO<StudyProjectVO.StudyProjectInfoVO> {
    @Getter
    @Setter
    public static class StudyProjectInfoVO extends CommonVO{
        private String dataMiningID;
        private String researchProjectID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
