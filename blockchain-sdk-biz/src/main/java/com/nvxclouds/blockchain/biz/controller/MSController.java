package com.nvxclouds.blockchain.biz.controller;

import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.feign.MSFeign;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;
import com.nvxclouds.blockchain.biz.service.MSService;
import com.nvxclouds.common.base.pojo.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:42
 * @Description: 监管端上链接口
 */
@Slf4j
@RestController
@RequestMapping("/ms")
public class MSController implements MSFeign {

    @Autowired
    private MSService msService;

    @GetMapping("/dataSet/approval")
    public BaseResult<DataSetApprovalVO> queryDataSetApproval(DataSetApprovalQuery dataSetApprovalQuery) {
        DataSetApprovalVO dataSetApprovals = msService.queryDataSetApproval(dataSetApprovalQuery);
        return BaseResult.ok(dataSetApprovals);
    }

    @PostMapping(value = "/dataSet/approval")
    public BaseResult<BlockChainTransactionVO> saveDataSetApproval(@RequestBody @Valid DataSetApprovalDTO dataSetApprovalDTO) {
        return BaseResult.ok(msService.saveDataSetApproval(dataSetApprovalDTO));
    }

    @GetMapping(value = "/dataSet/approval/modification")
    public BaseResult<DataSetApprovalModificationVO> queryDataSetApprovalModification(DataSetApprovalModificationQuery dataSetApprovalModificationQuery) {
        return BaseResult.ok(msService.queryDataSetApprovalModification(dataSetApprovalModificationQuery));
    }

    @PostMapping(value = "/dataSet/approval/modification")
    public BaseResult<BlockChainTransactionVO> saveDataSetApprovalModification(@RequestBody @Valid DataSetApprovalModificationDTO dataSetApprovalModificationDTO) {
        return BaseResult.ok(msService.saveDataSetApprovalModification(dataSetApprovalModificationDTO));
    }

    @GetMapping(value = "/studyProject/approval")
    public BaseResult<StudyProjectApprovalVO> queryStudyProjectApproval(StudyProjectApprovalQuery studyProjectApprovalQuery) {
        return BaseResult.ok(msService.queryStudyProjectApproval(studyProjectApprovalQuery));
    }

    @PostMapping(value = "/studyProject/approval")
    public BaseResult<BlockChainTransactionVO> saveStudyProjectApproval(@RequestBody @Valid StudyProjectApprovalDTO studyProjectApprovalDTO) {
        return BaseResult.ok(msService.saveStudyProjectApproval(studyProjectApprovalDTO));
    }

    @GetMapping(value = "/studyProject/approval/modification")
    public BaseResult<StudyProjectApprovalModificationVO> queryStudyProjectApprovalModification(StudyProjectApprovalModificationQuery studyProjectApprovalModificationQuery) {
        return BaseResult.ok(msService.queryStudyProjectApprovalModification(studyProjectApprovalModificationQuery));
    }

    @PostMapping(value = "/studyProject/approval/modification")
    public BaseResult<BlockChainTransactionVO> saveStudyProjectApprovalModification(@RequestBody @Valid StudyProjectApprovalModificationDTO studyProjectApprovalModificationDTO) {
        return BaseResult.ok(msService.saveStudyProjectApprovalModification(studyProjectApprovalModificationDTO));
    }

    @GetMapping("/dataSet/usage/approval")
    public BaseResult<DataSetUsageApprovalMSVO> queryDataSetApproval(DataSetUsageApprovalQuery dataSetUsageApprovalQuery) {
        return BaseResult.ok(msService.queryDataSetUsageApproval(dataSetUsageApprovalQuery));
    }

    @PostMapping(value = "/dataSet/usage/approval")
    public BaseResult<BlockChainTransactionVO> saveDataSetUsageApproval(@RequestBody @Valid DataSetUsageApprovalDTO dataSetUsageApprovalDTO) {
        return BaseResult.ok(msService.saveDataUsageSetApproval(dataSetUsageApprovalDTO));
    }

    @GetMapping(value = "/studyProject/result")
    public BaseResult<StudyProjectResultMSVO> queryStudyProjectApproval(StudyProjectResultQuery studyProjectResultQuery) {
        return BaseResult.ok(msService.queryStudyProjectResult(studyProjectResultQuery));
    }

    @PostMapping(value = "/studyProject/result")
    public BaseResult<BlockChainTransactionVO> saveStudyProjectResult(@RequestBody @Valid StudyProjectResultDTO studyProjectResultDTO) {
        return BaseResult.ok(msService.saveStudyProjectResult(studyProjectResultDTO));
    }


}
