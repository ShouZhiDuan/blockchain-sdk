package com.nvxclouds.blockchain.api.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/23 20:27  zhengxing.hu
 * @version 2020/6/23 20:27  1.0.0
 * @file StudyProjectQuery
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
public class StudyProjectQuery extends PageQuery{
    private String dataMiningID;
    private String researchProjectID;
    private String status;
    private String txTime;
    private String messageHash;
}
