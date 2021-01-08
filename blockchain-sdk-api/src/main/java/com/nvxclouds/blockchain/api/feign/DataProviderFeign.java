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
 * @author 2020/6/24 13:19  zhengxing.hu
 * @version 2020/6/24 13:19  1.0.0
 * @file DataProviderFeign
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public interface DataProviderFeign {

    @GetMapping(value = "/dataProvider/dataNode/registration")
    BaseResult<DataNodeRegistrationVO> queryDataNodeRegistration(@SpringQueryMap DataNodeRegistrationQuery dataSetBlockChainQuery);

    @PostMapping(value = "/dataProvider/dataNode/registration")
    BaseResult<BlockChainTransactionVO> saveDataNodeRegistration(@RequestBody @Valid DataNodeRegistrationDTO dataNodeRegistrationDTO);

    @GetMapping(value = "/dataProvider/dataNode/modification")
    BaseResult<DataNodeModificationVO> queryDataNodeModification(@SpringQueryMap DataNodeModificationQuery dataNodeModificationQuery);

    @PostMapping(value = "/dataProvider/dataNode/modification")
    BaseResult<BlockChainTransactionVO> saveDataNodeModification(@RequestBody @Valid DataNodeModificationDTO dataNodeModificationDTO);

    @GetMapping(value = "/dataProvider/dataSet/increase")
    BaseResult<DataSetIncreaseVO> queryDataSetIncrease(@SpringQueryMap DataSetIncreaseQuery dataSetIncreaseQuery);

    @PostMapping(value = "/dataProvider/dataSet/increase")
    BaseResult<BlockChainTransactionVO> saveDataSetIncrease(@RequestBody @Valid DataSetIncreaseDTO dataSetIncreaseDTO);

    @GetMapping(value = "/dataProvider/dataSet/open")
    BaseResult<DataSetOpenVO> queryDataSetOpen(@SpringQueryMap DataSetOpenQuery dataSetOpenQuery);

    @PostMapping(value = "/dataProvider/dataSet/open")
    BaseResult<BlockChainTransactionVO> saveDataSetOpen(@RequestBody @Valid DataSetOpenDTO dataSetOpenDTO);

    @GetMapping(value = "/dataProvider/dataSet/modification")
    BaseResult<DataSetModificationVO> queryDataSetModification(@SpringQueryMap DataSetModificationQuery dataSetModificationQuery);

    @PostMapping(value = "/dataProvider/dataSet/modification")
    BaseResult<BlockChainTransactionVO> saveDataSetModification(@RequestBody @Valid DataSetModificationDTO dataSetModificationDTO);

    @GetMapping(value = "/dataProvider/dataSet/offline")
    BaseResult<DataSetOfflineVO> queryDataSetOffline(@SpringQueryMap DataSetOfflineQuery dataSetOfflineQuery);

    @PostMapping(value = "/dataProvider/dataSet/offline")
    BaseResult<BlockChainTransactionVO> saveDataSetOffline(@RequestBody @Valid DataSetOfflineDTO dataSetOfflineDTO);

    @GetMapping(value = "/dataProvider/study/result")
    BaseResult<StudyResultVO> queryStudyResult(StudyResultQuery studyResultQuery);

    @PostMapping(value = "/dataProvider/study/result")
    BaseResult<BlockChainTransactionVO> saveStudyResult(@RequestBody @Valid StudyResultDTO studyResultDTO);


}
