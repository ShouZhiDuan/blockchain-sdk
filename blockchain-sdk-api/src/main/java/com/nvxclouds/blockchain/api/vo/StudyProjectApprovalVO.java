package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:57
 * @Description:
 */
@Getter
@Setter
public class StudyProjectApprovalVO extends RegulatorVO<StudyProjectApprovalVO.StudyProjectApprovalInfoVO> {

    @Getter
    @Setter
    public static class StudyProjectApprovalInfoVO extends CommonVO{
        private String dataMiningID;
        private String researchProjectID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
