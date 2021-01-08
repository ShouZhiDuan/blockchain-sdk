package com.nvxclouds.blockchain.api.feign;

import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;
import com.nvxclouds.common.base.pojo.BaseResult;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/22 09:58
 * @Description:
 */
public interface MSFeign {

    @GetMapping("/ms/dataSet/approval")
    BaseResult<DataSetApprovalVO> queryDataSetApproval(@SpringQueryMap DataSetApprovalQuery dataSetApprovalQuery);

    @PostMapping(value = "/ms/dataSet/approval")
    BaseResult<BlockChainTransactionVO> saveDataSetApproval(@RequestBody @Valid DataSetApprovalDTO dataSetApprovalDTO);

    @GetMapping(value = "/ms/dataSet/approval/modification")
    BaseResult<DataSetApprovalModificationVO> queryDataSetApprovalModification(@SpringQueryMap DataSetApprovalModificationQuery dataSetApprovalModificationQuery);

    @PostMapping(value = "/ms/dataSet/approval/modification")
    BaseResult<BlockChainTransactionVO> saveDataSetApprovalModification(@RequestBody @Valid DataSetApprovalModificationDTO dataSetApprovalModificationDTO);

    @GetMapping(value = "/ms/studyProject/approval")
    BaseResult<StudyProjectApprovalVO> queryStudyProjectApproval(@SpringQueryMap StudyProjectApprovalQuery studyProjectApprovalQuery);

    @PostMapping(value = "/ms/studyProject/approval")
    BaseResult<BlockChainTransactionVO> saveStudyProjectApproval(@RequestBody @Valid StudyProjectApprovalDTO studyProjectApprovalDTO);

    @GetMapping(value = "/ms/studyProject/approval/modification")
    BaseResult<StudyProjectApprovalModificationVO> queryStudyProjectApprovalModification(StudyProjectApprovalModificationQuery studyProjectApprovalModificationQuery);

    @PostMapping(value = "/ms/studyProject/approval/modification")
    BaseResult<BlockChainTransactionVO> saveStudyProjectApprovalModification(@RequestBody @Valid StudyProjectApprovalModificationDTO studyProjectApprovalModificationDTO);

    @GetMapping("/ms/dataSet/usage/approval")
    BaseResult<DataSetUsageApprovalMSVO> queryDataSetApproval(@SpringQueryMap DataSetUsageApprovalQuery dataSetUsageApprovalQuery);

    @PostMapping(value = "/ms/dataSet/usage/approval")
    BaseResult<BlockChainTransactionVO> saveDataSetUsageApproval(@RequestBody @Valid DataSetUsageApprovalDTO dataSetUsageApprovalDTO);

    @GetMapping(value = "/studyProject/result")
    BaseResult<StudyProjectResultMSVO> queryStudyProjectApproval(@SpringQueryMap StudyProjectResultQuery studyProjectResultQuery);

    @PostMapping(value = "/studyProject/result")
    BaseResult<BlockChainTransactionVO> saveStudyProjectResult(@RequestBody @Valid StudyProjectResultDTO studyProjectResultDTO);
}
