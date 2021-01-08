package com.nvxclouds.blockchain.biz.service;

import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;

/**
 * @author 2020/6/23 20:22  zhengxing.hu
 * @version 2020/6/23 20:22  1.0.0
 * @file DataDemandService
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public interface DataDemandService {

    StudyProjectVO queryStudyProject(StudyProjectQuery query);

    BlockChainTransactionVO saveStudyProject(StudyProjectDTO dto);

    DataSetUsageApprovalVO queryDataSetUsageApproval(DataSetUsageApprovalQuery dataSetUsageApprovalQuery);

    BlockChainTransactionVO saveDataUsageSetApproval(DataSetUsageApprovalDTO dataSetUsageApprovalDTO);

    StudyProjectResultVO queryStudyProjectResult(StudyProjectResultQuery studyProjectResultQuery);

    BlockChainTransactionVO saveStudyProjectResult(StudyProjectResultDTO studyProjectResultDTO);

    LicensePurchaseVO queryLicensePurchase(LicensePurchaseQuery query);

    BlockChainTransactionVO saveLicensePurchase(LicensePurchaseDTO dto);

    LicenseActivationVO queryLicenseActivation(LicenseActivationQuery query);

    BlockChainTransactionVO saveLicenseActivation(LicenseActivationDTO dto);
}
