package com.nvxclouds.blockchain.biz.controller;

import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.feign.DataDemandFeign;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;
import com.nvxclouds.blockchain.biz.service.DataDemandService;
import com.nvxclouds.common.base.pojo.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 11:25
 * @Description:
 */
@RestController
@RequestMapping("/dataDemand")
public class DataDemandController implements DataDemandFeign {

    @Autowired
    private DataDemandService dataDemandService;

    @GetMapping(value = "/studyProject")
    public BaseResult<StudyProjectVO> queryStudyProject(StudyProjectQuery query) {
        StudyProjectVO studyProjects = dataDemandService.queryStudyProject(query);
        return BaseResult.ok(studyProjects);
    }

    @PostMapping(value = "/studyProject")//创建研究项目
    public BaseResult<BlockChainTransactionVO> saveStudyProject(@RequestBody @Valid StudyProjectDTO dto) {
        BlockChainTransactionVO blockChainTransaction = dataDemandService.saveStudyProject(dto);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping("/dataSet/usage/approval")
    public BaseResult<DataSetUsageApprovalVO> queryDataSetApproval(DataSetUsageApprovalQuery dataSetUsageApprovalQuery) {
        DataSetUsageApprovalVO dataSetUsageApprovals = dataDemandService.queryDataSetUsageApproval(dataSetUsageApprovalQuery);
        return BaseResult.ok(dataSetUsageApprovals);
    }

    @PostMapping(value = "/dataSet/usage/approval")//使用数据集申请
    public BaseResult<BlockChainTransactionVO> saveDataSetUsageApproval(@RequestBody @Valid DataSetUsageApprovalDTO dataSetUsageApprovalDTO) {
        BlockChainTransactionVO blockChainTransaction = dataDemandService.saveDataUsageSetApproval(dataSetUsageApprovalDTO);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/studyProject/result")
    public BaseResult<StudyProjectResultVO> queryStudyProjectApproval(StudyProjectResultQuery studyProjectResultQuery) {
        StudyProjectResultVO studyProjectResults = dataDemandService.queryStudyProjectResult(studyProjectResultQuery);
        return BaseResult.ok(studyProjectResults);
    }

    @PostMapping(value = "/studyProject/result")//查看研究结果
    public BaseResult<BlockChainTransactionVO> saveStudyProjectResult(@RequestBody @Valid StudyProjectResultDTO studyProjectResultDTO) {
        BlockChainTransactionVO blockChainTransaction = dataDemandService.saveStudyProjectResult(studyProjectResultDTO);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/license/purchase")
    public BaseResult<LicensePurchaseVO> queryLicensePurchase(LicensePurchaseQuery query) {
        LicensePurchaseVO licensePurchases = dataDemandService.queryLicensePurchase(query);
        return BaseResult.ok(licensePurchases);
    }

    @PostMapping(value = "/license/purchase")//证书购买授权
    public BaseResult<BlockChainTransactionVO> saveLicensePurchase(@RequestBody @Valid LicensePurchaseDTO dto) {
        BlockChainTransactionVO blockChainTransaction = dataDemandService.saveLicensePurchase(dto);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/license/activation")
    public BaseResult<LicenseActivationVO> queryLicenseActivation(LicenseActivationQuery query) {
        LicenseActivationVO licenseActivations = dataDemandService.queryLicenseActivation(query);
        return BaseResult.ok(licenseActivations);
    }

    @PostMapping(value = "/license/activation")//证书激活授权
    public BaseResult<BlockChainTransactionVO> saveLicenseActivation(@RequestBody @Valid LicenseActivationDTO dto) {
        BlockChainTransactionVO blockChainTransaction = dataDemandService.saveLicenseActivation(dto);
        return BaseResult.ok(blockChainTransaction);
    }
}
