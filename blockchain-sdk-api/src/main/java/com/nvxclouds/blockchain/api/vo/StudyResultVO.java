package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:40
 * @Description:
 */
@Getter
@Setter
public class StudyResultVO extends DataProviderVO<StudyResultVO.StudyResultInfoVO> {
    @Getter
    @Setter
    public static class StudyResultInfoVO extends CommonVO {
        private String dataNodeID;
        private String nodeName;
        private String datasetID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
