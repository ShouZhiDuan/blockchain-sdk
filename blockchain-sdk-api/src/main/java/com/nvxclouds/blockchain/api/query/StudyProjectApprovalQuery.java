package com.nvxclouds.blockchain.api.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:57
 * @Description:
 */
@Getter
@Setter
public class StudyProjectApprovalQuery extends PageQuery{
    private String dataMiningID;
    private String researchProjectID;
    private String status;
    private String txTime;
    private String messageHash;
}
