package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:58
 * @Description:
 */
@Getter
@Setter
public class StudyProjectApprovalModificationVO extends RegulatorVO<StudyProjectApprovalModificationVO.StudyProjectApprovalModificationInfoVO> {

    @Getter
    @Setter
    public static class StudyProjectApprovalModificationInfoVO extends CommonVO{
        private String dataMiningID;
        private String researchProjectID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
