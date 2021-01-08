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
import com.nvxclouds.blockchain.biz.service.DataDemandService;
import com.nvxclouds.common.base.pojo.Pagination;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author 2020/6/23 20:22  zhengxing.hu
 * @version 2020/6/23 20:22  1.0.0
 * @file DataDemandServiceImpl
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Service
public class DataDemandServiceImpl implements DataDemandService {

    private static final String DATA_USER_QUERY = "dataUserQuery";
    private static final String DATA_USER_INVOKE = "dataUserInvoke";

    private static final String CREATE_RESEARCH_PROJECTS_OPERATION = "createResearchProjects";
    private static final String USE_DATASET_APPLY_OPERATION = "useDatasetApply";
    private static final String QUERY_USER_RESEARCH_RESULTS_OPERATION = "queryUserResearchResults";
    private static final String PURCHASE_AUTHORIZATION_OPERATION = "purchaseAuthorization";
    private static final String ACTIVATE_AUTHORIZATION_OPERATION = "activateAuthorization";

    private final BlockChainClientHandler blockChainClientHandler;

    @SneakyThrows
    public DataDemandServiceImpl(BlockChainClientHandler blockChainClientHandler) {
        this.blockChainClientHandler = blockChainClientHandler;
        blockChainClientHandler.config(
                UserConfig.builder()
                        .name(Config.ADMIN)
                        .affiliation(Config.DATAUSER)
                        .mspId(Config.DataUserMSP)
                        .build(),
                ClientConfig.builder()
                        .url(Config.CA_DATAUSER_URL)
                        .tslFile(Config.DATAUSER_TLSFILE)
                        .host(Config.CA_DATAUSER_URL)
                        .eventHubPort(Config.EVENT_HUB_PROT1)
                        .build(),
                ChannelConfig.builder()
                        .channelName(Config.CHANNEL_NAME)
                        .peer(Config.DATAUSER_PEER_1)
                        .peerUrl(Config.DATAUSER_PEER_1_URL)
                        .orderName(Config.ORDERER1_NAME)
                        .orderUrl(Config.ORDERER1_URL)
                        .server(Config.server1Url)
                        .build());
    }


    @SneakyThrows
    @Override
    public StudyProjectVO queryStudyProject(StudyProjectQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", CREATE_RESEARCH_PROJECTS_OPERATION);
        Util.handleParam(param, query);
        return query(param,StudyProjectVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveStudyProject(StudyProjectDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", CREATE_RESEARCH_PROJECTS_OPERATION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public DataSetUsageApprovalVO queryDataSetUsageApproval(DataSetUsageApprovalQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", USE_DATASET_APPLY_OPERATION);
        Util.handleParam(param, query);
        return query(param,DataSetUsageApprovalVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataUsageSetApproval(DataSetUsageApprovalDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", USE_DATASET_APPLY_OPERATION);
        Util.handleParam(param, dto);
//        param.put("Datasets", JSON.toJSONString(dto.getDatasets()));
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public StudyProjectResultVO queryStudyProjectResult(StudyProjectResultQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", QUERY_USER_RESEARCH_RESULTS_OPERATION);
        Util.handlePageParam(param, query);
        return query(param,StudyProjectResultVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveStudyProjectResult(StudyProjectResultDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", QUERY_USER_RESEARCH_RESULTS_OPERATION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public LicensePurchaseVO queryLicensePurchase(LicensePurchaseQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", PURCHASE_AUTHORIZATION_OPERATION);
        Util.handlePageParam(param, query);
        return query(param,LicensePurchaseVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveLicensePurchase(LicensePurchaseDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", PURCHASE_AUTHORIZATION_OPERATION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @SneakyThrows
    @Override
    public LicenseActivationVO queryLicenseActivation(LicenseActivationQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", ACTIVATE_AUTHORIZATION_OPERATION);
        Util.handlePageParam(param, query);
        return query(param,LicenseActivationVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveLicenseActivation(LicenseActivationDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", ACTIVATE_AUTHORIZATION_OPERATION);
        Util.handleParam(param, dto);
        return transaction(param);
    }

    @SneakyThrows
    public <T extends Pagination<K>, K> T query(JSONObject param, Class<T> clazz) {
        return blockChainClientHandler.query(DATA_USER_QUERY, param.toJSONString(), clazz);
    }

    @SneakyThrows
    public BlockChainTransactionVO transaction(JSONObject param) {
        return blockChainClientHandler.transaction(DATA_USER_INVOKE, param.toJSONString());
    }
}
