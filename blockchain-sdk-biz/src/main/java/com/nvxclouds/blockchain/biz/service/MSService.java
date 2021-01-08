package com.nvxclouds.blockchain.biz.service;

import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 17:38
 * @Description:
 */
public interface MSService {

    DataSetApprovalVO queryDataSetApproval(DataSetApprovalQuery dataSetApprovalQuery);

    BlockChainTransactionVO saveDataSetApproval(DataSetApprovalDTO dataSetApprovalDTO);

    DataSetApprovalModificationVO queryDataSetApprovalModification(DataSetApprovalModificationQuery dataSetApprovalModificationQuery);

    BlockChainTransactionVO saveDataSetApprovalModification(DataSetApprovalModificationDTO dataSetApprovalModificationDTO);

    StudyProjectApprovalVO queryStudyProjectApproval(StudyProjectApprovalQuery studyProjectApprovalQuery);

    BlockChainTransactionVO saveStudyProjectApproval(StudyProjectApprovalDTO studyProjectApprovalDTO);

    StudyProjectApprovalModificationVO queryStudyProjectApprovalModification(StudyProjectApprovalModificationQuery studyProjectApprovalModificationQuery);

    BlockChainTransactionVO saveStudyProjectApprovalModification(StudyProjectApprovalModificationDTO studyProjectApprovalModificationDTO);

    DataSetUsageApprovalMSVO queryDataSetUsageApproval(DataSetUsageApprovalQuery dataSetUsageApprovalQuery);

    BlockChainTransactionVO saveDataUsageSetApproval(DataSetUsageApprovalDTO dataSetUsageApprovalDTO);

    StudyProjectResultMSVO queryStudyProjectResult(StudyProjectResultQuery studyProjectResultQuery);

    BlockChainTransactionVO saveStudyProjectResult(StudyProjectResultDTO studyProjectResultDTO);

    void queryBlock();
}
