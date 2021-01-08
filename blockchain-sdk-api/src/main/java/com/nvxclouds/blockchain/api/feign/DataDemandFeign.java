package com.nvxclouds.blockchain.api.feign;

import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;
import com.nvxclouds.common.base.pojo.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author 2020/6/24 13:19  zhengxing.hu
 * @version 2020/6/24 13:19  1.0.0
 * @file DataDemandFeign
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */

public interface DataDemandFeign {

    @GetMapping(value = "/dataDemand/studyProject")
    BaseResult<StudyProjectVO> queryStudyProject(StudyProjectQuery query);

    @PostMapping(value = "/dataDemand/studyProject")
    BaseResult<BlockChainTransactionVO> saveStudyProject(@RequestBody @Valid StudyProjectDTO dto);

    @GetMapping("/dataDemand/dataSet/usage/approval")
    BaseResult<DataSetUsageApprovalVO> queryDataSetApproval(DataSetUsageApprovalQuery dataSetUsageApprovalQuery);

    @PostMapping(value = "/dataDemand/dataSet/usage/approval")
    BaseResult<BlockChainTransactionVO> saveDataSetUsageApproval(@RequestBody @Valid DataSetUsageApprovalDTO dataSetUsageApprovalDTO);

    @GetMapping(value = "/dataDemand/studyProject/result")
    BaseResult<StudyProjectResultVO> queryStudyProjectApproval(StudyProjectResultQuery studyProjectResultQuery);

    @PostMapping(value = "/dataDemand/studyProject/result")
    BaseResult<BlockChainTransactionVO> saveStudyProjectResult(@RequestBody @Valid StudyProjectResultDTO studyProjectResultDTO);

    @GetMapping(value = "/dataDemand/license/purchase")
    BaseResult<LicensePurchaseVO> queryLicensePurchase(LicensePurchaseQuery query);

    @PostMapping(value = "/dataDemand/license/purchase")
    BaseResult<BlockChainTransactionVO> saveLicensePurchase(@RequestBody @Valid LicensePurchaseDTO dto);

    @GetMapping(value = "/dataDemand/license/activation")
    BaseResult<LicenseActivationVO> queryLicenseActivation(LicenseActivationQuery query);

    @PostMapping(value = "/dataDemand/license/activation")
    BaseResult<BlockChainTransactionVO> saveLicenseActivation(@RequestBody @Valid LicenseActivationDTO dto);
}
