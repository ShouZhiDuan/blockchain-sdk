package com.nvxclouds.blockchain.biz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nvxclouds.blockchain.api.dto.*;
import com.nvxclouds.blockchain.api.query.*;
import com.nvxclouds.blockchain.api.vo.*;
import com.nvxclouds.blockchain.biz.config.ChannelConfig;
import com.nvxclouds.blockchain.biz.config.ClientConfig;
import com.nvxclouds.blockchain.biz.config.Config;
import com.nvxclouds.blockchain.biz.config.UserConfig;
import com.nvxclouds.blockchain.biz.handler.BlockChainClientHandler;
import com.nvxclouds.blockchain.biz.service.MSService;
import com.nvxclouds.common.base.pojo.Pagination;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 17:39
 * @Description:
 */
@Slf4j
@Service
public class MSServiceImpl implements MSService {

    private static final String REGULATOR_INVOKE = "regulatorInvoke";
    private static final String REGULATOR_QUERY = "regulatorQuery";
    private static final String DATASET_APPROVAL_OPERATION = "newDatasetApproval";
    private static final String MODIFY_DATASET_APPROVAL_OPERATION = "newDatasetApproval";
    private static final String RESEARCH_PROJECT_APPROVAL_OPERATION = "researchProjectApproval";
    private static final String USE_DATASET_APPROVAL_OPERATION = "useDatasetApproval";
    private static final String PROJECT_RESEARCH_RESULTS_OPERAION = "queryProjectResearchResults";

    private BlockChainClientHandler blockChainClientHandler;

    @SneakyThrows
    public MSServiceImpl(BlockChainClientHandler blockChainClientHandler) {
        this.blockChainClientHandler = blockChainClientHandler;
        blockChainClientHandler.config(
                UserConfig.builder()
                        .name(Config.ADMIN)
                        .affiliation(Config.REGULATOR)
                        .mspId(Config.RegulatorMSP)
                        .build(),
                ClientConfig.builder()
                        .url(Config.CA_REGULATOR_URL)
                        .tslFile(Config.REGULATOR_TLSFILE)
                        .host(Config.CA_REGULATOR_URL)
                        .eventHubPort(Config.EVENT_HUB_PROT1)
                        .build(),
                ChannelConfig.builder()
                        .channelName(Config.CHANNEL_NAME)
                        .peer(Config.REGULATOR_PEER_1)
                        .peerUrl(Config.REGULATOR_PEER_1_URL)
                        .orderName(Config.ORDERER2_NAME)
                        .orderUrl(Config.ORDERER2_URL)
                        .server(Config.server2Url)
                        .build());
    }

    @SneakyThrows
    @Override
    public DataSetApprovalVO queryDataSetApproval(DataSetApprovalQuery dataSetApprovalQuery) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, dataSetApprovalQuery);
        param.put("Operation", DATASET_APPROVAL_OPERATION);
        Util.handleParam(param, dataSetApprovalQuery);
        return query(param, DataSetApprovalVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataSetApproval(DataSetApprovalDTO dataSetApprovalDTO) {
        JSONObject param = new JSONObject();
        param.put("Operation", DATASET_APPROVAL_OPERATION);
        Util.handleParam(param, dataSetApprovalDTO);
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public DataSetApprovalModificationVO queryDataSetApprovalModification(DataSetApprovalModificationQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", MODIFY_DATASET_APPROVAL_OPERATION);
        Util.handleParam(param, query);
        return query(param, DataSetApprovalModificationVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataSetApprovalModification(DataSetApprovalModificationDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", MODIFY_DATASET_APPROVAL_OPERATION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public StudyProjectApprovalVO queryStudyProjectApproval(StudyProjectApprovalQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", RESEARCH_PROJECT_APPROVAL_OPERATION);
        Util.handleParam(param, query);
        return query(param, StudyProjectApprovalVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveStudyProjectApproval(StudyProjectApprovalDTO dto) {
        JSONObject param = new JSONObject();
        param.put("DataMiningID", RESEARCH_PROJECT_APPROVAL_OPERATION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public StudyProjectApprovalModificationVO queryStudyProjectApprovalModification(StudyProjectApprovalModificationQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", MODIFY_DATASET_APPROVAL_OPERATION);
        Util.handleParam(param, query);
        return query(param, StudyProjectApprovalModificationVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveStudyProjectApprovalModification(StudyProjectApprovalModificationDTO dto) {
        JSONObject param = new JSONObject();
        param.put("DataMiningID", RESEARCH_PROJECT_APPROVAL_OPERATION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public DataSetUsageApprovalMSVO queryDataSetUsageApproval(DataSetUsageApprovalQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", USE_DATASET_APPROVAL_OPERATION);
        Util.handleParam(param, query);
        return query(param, DataSetUsageApprovalMSVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataUsageSetApproval(DataSetUsageApprovalDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", USE_DATASET_APPROVAL_OPERATION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public StudyProjectResultMSVO queryStudyProjectResult(StudyProjectResultQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", PROJECT_RESEARCH_RESULTS_OPERAION);
        Util.handleParam(param, query);
        return query(param, StudyProjectResultMSVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveStudyProjectResult(StudyProjectResultDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", PROJECT_RESEARCH_RESULTS_OPERAION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @Override
    public void queryBlock() {
        blockChainClientHandler.getBlock();
    }

    @SneakyThrows
    public <T extends Pagination<K>, K> T query(JSONObject param, Class<T> clazz) {
        return blockChainClientHandler.query(REGULATOR_QUERY, param.toJSONString(), clazz);
    }

    @SneakyThrows
    public BlockChainTransactionVO transaction(JSONObject param) {
        return blockChainClientHandler.transaction(REGULATOR_INVOKE, param.toJSONString());
    }
}