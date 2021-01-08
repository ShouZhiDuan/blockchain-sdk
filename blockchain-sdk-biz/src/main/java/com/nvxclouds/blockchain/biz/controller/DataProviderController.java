package com.nvxclouds.blockchain.biz.controller;

import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.feign.DataProviderFeign;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;
import com.nvxclouds.blockchain.biz.service.DataProviderService;
import com.nvxclouds.common.base.pojo.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 09:59
 * @Description: 数据提供方相关上链操作
 */
@RestController
@RequestMapping(value = "/dataProvider")
public class DataProviderController implements DataProviderFeign {

    @Autowired
    private DataProviderService dataProviderService;

    @GetMapping(value = "/dataNode/registration")
    public BaseResult<DataNodeRegistrationVO> queryDataNodeRegistration(DataNodeRegistrationQuery dataSetBlockChainQuery) {
        DataNodeRegistrationVO dataNodeRegistrations = dataProviderService.queryDataNodeRegistration(dataSetBlockChainQuery);
        return BaseResult.ok(dataNodeRegistrations);
    }

    @PostMapping(value = "/dataNode/registration")
    public BaseResult<BlockChainTransactionVO> saveDataNodeRegistration(@RequestBody @Valid DataNodeRegistrationDTO dataNodeRegistrationDTO) {
        BlockChainTransactionVO blockChainTransaction = dataProviderService.saveDataNodeRegistration(dataNodeRegistrationDTO);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/dataNode/modification")
    public BaseResult<DataNodeModificationVO> queryDataNodeModification(DataNodeModificationQuery dataNodeModificationQuery) {
        DataNodeModificationVO dataNodeModifications = dataProviderService.queryDataNodeModification(dataNodeModificationQuery);
        return BaseResult.ok(dataNodeModifications);
    }

    @PostMapping(value = "/dataNode/modification")
    public BaseResult<BlockChainTransactionVO> saveDataNodeModification(@RequestBody @Valid DataNodeModificationDTO dataNodeModificationDTO) {
        BlockChainTransactionVO blockChainTransaction = dataProviderService.saveDataNodeModification(dataNodeModificationDTO);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/dataSet/increase")
    public BaseResult<DataSetIncreaseVO> queryDataSetIncrease(DataSetIncreaseQuery dataSetIncreaseQuery) {
        DataSetIncreaseVO dataSetIncreases = dataProviderService.queryDataSetIncrease(dataSetIncreaseQuery);
        return BaseResult.ok(dataSetIncreases);
    }

    @PostMapping(value = "/dataSet/increase")
    public BaseResult<BlockChainTransactionVO> saveDataSetIncrease(@RequestBody @Valid DataSetIncreaseDTO dataSetIncreaseDTO) {
        BlockChainTransactionVO blockChainTransaction = dataProviderService.saveDataSetIncrease(dataSetIncreaseDTO);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/dataSet/open")
    public BaseResult<DataSetOpenVO> queryDataSetOpen(DataSetOpenQuery dataSetOpenQuery) {
        DataSetOpenVO dataSetOpens = dataProviderService.queryDataSetOpen(dataSetOpenQuery);
        return BaseResult.ok(dataSetOpens);
    }

    @PostMapping(value = "/dataSet/open")
    public BaseResult<BlockChainTransactionVO> saveDataSetOpen(@RequestBody @Valid DataSetOpenDTO dataSetOpenDTO) {
        BlockChainTransactionVO blockChainTransaction = dataProviderService.saveDataSetOpen(dataSetOpenDTO);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/dataSet/modification")
    public BaseResult<DataSetModificationVO> queryDataSetModification(DataSetModificationQuery dataSetModificationQuery) {
        DataSetModificationVO dataSetModifications = dataProviderService.queryDataSetModification(dataSetModificationQuery);
        return BaseResult.ok(dataSetModifications);
    }

    @PostMapping(value = "/dataSet/modification")
    public BaseResult<BlockChainTransactionVO> saveDataSetModification(@RequestBody @Valid DataSetModificationDTO dataSetModificationDTO) {
        BlockChainTransactionVO blockChainTransaction = dataProviderService.saveDataSetModification(dataSetModificationDTO);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/dataSet/offline")
    public BaseResult<DataSetOfflineVO> queryDataSetOffline(DataSetOfflineQuery dataSetOfflineQuery) {
        DataSetOfflineVO dataSetOffices = dataProviderService.queryDataSetOffline(dataSetOfflineQuery);
        return BaseResult.ok(dataSetOffices);
    }

    @PostMapping(value = "/dataSet/offline")
    public BaseResult<BlockChainTransactionVO> saveDataSetOffline(@RequestBody @Valid DataSetOfflineDTO dataSetOfflineDTO) {
        BlockChainTransactionVO blockChainTransaction = dataProviderService.saveDataSetOffline(dataSetOfflineDTO);
        return BaseResult.ok(blockChainTransaction);
    }

    @GetMapping(value = "/study/result")
    public BaseResult<StudyResultVO> queryStudyResult(StudyResultQuery studyResultQuery) {
        StudyResultVO studyResults = dataProviderService.queryStudyResult(studyResultQuery);
        return BaseResult.ok(studyResults);
    }

    @PostMapping(value = "/study/result")
    public BaseResult<BlockChainTransactionVO> saveStudyResult(@RequestBody @Valid StudyResultDTO studyResultDTO) {
        BlockChainTransactionVO blockChainTransaction = dataProviderService.saveStudyResult(studyResultDTO);
        return BaseResult.ok(blockChainTransaction);
    }

}
