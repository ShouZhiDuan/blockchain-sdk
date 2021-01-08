package com.nvxclouds.blockchain.biz.service;

import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;
import com.nvxclouds.blockchain.biz.handler.BlockChainClientHandler;

/**
 * @author 2020/6/23 17:12  zhengxing.hu
 * @version 2020/6/23 17:12  1.0.0
 * @file DataProviderService
 * @brief 数据提供方业务层层
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public interface DataProviderService {

    DataNodeRegistrationVO queryDataNodeRegistration(DataNodeRegistrationQuery dataSetBlockChainQuery);

    BlockChainTransactionVO saveDataNodeRegistration(DataNodeRegistrationDTO dataNodeRegistrationDTO);

    DataNodeModificationVO queryDataNodeModification(DataNodeModificationQuery dataNodeModificationQuery);

    BlockChainTransactionVO saveDataNodeModification(DataNodeModificationDTO dataNodeModificationDTO);

    DataSetIncreaseVO queryDataSetIncrease(DataSetIncreaseQuery dataSetIncreaseQuery);

    BlockChainTransactionVO saveDataSetIncrease(DataSetIncreaseDTO dataSetIncreaseDTO);

    DataSetOpenVO queryDataSetOpen(DataSetOpenQuery dataSetOpenQuery);

    BlockChainTransactionVO saveDataSetOpen(DataSetOpenDTO dataSetOpenDTO);

    DataSetModificationVO queryDataSetModification(DataSetModificationQuery dataSetModificationQuery);

    BlockChainTransactionVO saveDataSetModification(DataSetModificationDTO dataSetModificationDTO);

    DataSetOfflineVO queryDataSetOffline(DataSetOfflineQuery dataSetOfflineQuery);

    BlockChainTransactionVO saveDataSetOffline(DataSetOfflineDTO dataSetOfflineDTO);

    StudyResultVO queryStudyResult(StudyResultQuery studyResultQuery);

    BlockChainTransactionVO saveStudyResult(StudyResultDTO studyResultDTO);

    BlockChainClientHandler getBlockChainClientHandler();
}
