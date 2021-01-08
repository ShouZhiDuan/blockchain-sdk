package com.nvxclouds.blockchain.api.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/23 19:20  zhengxing.hu
 * @version 2020/6/23 19:20  1.0.0
 * @file StudyProjectResultQuery
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
public class StudyProjectResultQuery extends PageQuery{
    private String regulatorUserID;
    private String researchProjectID;
    private String txTime;
    private String messageHash;
}
