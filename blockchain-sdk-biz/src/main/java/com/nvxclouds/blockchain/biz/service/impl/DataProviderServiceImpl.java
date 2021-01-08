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
import com.nvxclouds.blockchain.biz.service.DataProviderService;
import com.nvxclouds.common.base.pojo.Pagination;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 11:47
 * @Description:
 */
@Service
public class DataProviderServiceImpl implements DataProviderService {
    private static final String DATA_PROVIDER_QUERY = "dataProviderQuery";
    private static final String DATA_PROVIDER_INVOKE = "dataProviderInvoke";

    private static final String DATA_NODE_REGISTRATION_OPERATION = "dataNodeRegistration";
    private static final String DATA_NODE_MODIFICATION_OPERATION = "dataNodeModificationApply";
    private static final String ADD_DATASET_OPERATION = "addDatasetApply";
    private static final String OPEN_DATASET_OPERATION = "openDatasets";
    private static final String MODIFY_DATASET_OPERATION = "modifyDatasets";
    private static final String DATASET_OFFLINE_OPERATION = "datasetsOffline";
    private static final String QUERY_NODE_DATA_RESEARCH_RESULT_OPERATION = "queryNodeDataResearchResults";

    private final BlockChainClientHandler blockChainClientHandler;

    @SneakyThrows
    public DataProviderServiceImpl(BlockChainClientHandler blockChainClientHandler) {
        this.blockChainClientHandler = blockChainClientHandler;
        blockChainClientHandler.config(
                UserConfig.builder()
                        .name(Config.ADMIN)
                        .affiliation(Config.DATAPROVIDER)
                        .mspId(Config.DataProviderMSP)
                        .build(),
                ClientConfig.builder()
                        .url(Config.CA_DATAPROVIDER_URL)
                        .tslFile(Config.DATAPROVIDER_TLSFILE)
                        .host(Config.CA_DATAPROVIDER_URL)
                        .eventHubPort(Config.EVENT_HUB_PROT1)
                        .build(),
                ChannelConfig.builder()
                        .channelName(Config.CHANNEL_NAME)
                        .peer(Config.DATAPROVIDER_PEER_1)
                        .peerUrl(Config.DATAPROVIDER_PEER_1_URL)
                        .orderName(Config.ORDERER0_NAME)
                        .orderUrl(Config.ORDERER0_URL)
                        .server(Config.server0Url)
                        .build());
    }

    @SneakyThrows
    @Override
    public DataNodeRegistrationVO queryDataNodeRegistration(DataNodeRegistrationQuery query) {
        JSONObject param = new JSONObject();
        param.put("Operation", DATA_NODE_REGISTRATION_OPERATION);
        Util.handlePageParam(param, query);
//        Util.handleQuery(param,query.getClass());
        Util.handleParam(param, query);
        return query(param,DataNodeRegistrationVO.class);
    }


    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataNodeRegistration(DataNodeRegistrationDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", DATA_NODE_REGISTRATION_OPERATION);
        Util.handleParam(param, dto);
        return blockChainClientHandler.transaction(DATA_PROVIDER_INVOKE, param.toJSONString());
    }

    @SneakyThrows
    @Override
    public DataNodeModificationVO queryDataNodeModification(DataNodeModificationQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", DATA_NODE_MODIFICATION_OPERATION);
        Util.handleParam(param, query);
        return query(param,DataNodeModificationVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataNodeModification(DataNodeModificationDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", DATA_NODE_MODIFICATION_OPERATION);
        Util.handleParam(param, dto);
        return blockChainClientHandler.transaction(DATA_PROVIDER_INVOKE, param.toJSONString());
    }

    @SneakyThrows
    @Override
    public DataSetIncreaseVO queryDataSetIncrease(DataSetIncreaseQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", ADD_DATASET_OPERATION);
        Util.handleParam(param, query);
        return query(param,DataSetIncreaseVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataSetIncrease(DataSetIncreaseDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", ADD_DATASET_OPERATION);
        Util.handleParam(param, dto);
        return blockChainClientHandler.transaction(DATA_PROVIDER_INVOKE, param.toJSONString());
    }

    @SneakyThrows
    @Override
    public DataSetOpenVO queryDataSetOpen(DataSetOpenQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", OPEN_DATASET_OPERATION);
        Util.handleParam(param, query);
        return query(param,DataSetOpenVO.class);

    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataSetOpen(DataSetOpenDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", OPEN_DATASET_OPERATION);
        Util.handleParam(param, dto);
        return blockChainClientHandler.transaction(DATA_PROVIDER_INVOKE, param.toJSONString());
    }

    @SneakyThrows
    @Override
    public DataSetModificationVO queryDataSetModification(DataSetModificationQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", MODIFY_DATASET_OPERATION);
        Util.handleParam(param, query);
        return query(param,DataSetModificationVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataSetModification(DataSetModificationDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", MODIFY_DATASET_OPERATION);
        Util.handleParam(param, dto);
        return blockChainClientHandler.transaction(DATA_PROVIDER_INVOKE, param.toJSONString());
    }

    @SneakyThrows
    @Override
    public DataSetOfflineVO queryDataSetOffline(DataSetOfflineQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", DATASET_OFFLINE_OPERATION);
        Util.handleParam(param, query);
        return query(param,DataSetOfflineVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveDataSetOffline(DataSetOfflineDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", DATASET_OFFLINE_OPERATION);
        Util.handleParam(param, dto);
        return blockChainClientHandler.transaction(DATA_PROVIDER_INVOKE, param.toJSONString());
    }

    @SneakyThrows
    @Override
    public StudyResultVO queryStudyResult(StudyResultQuery query) {
        JSONObject param = new JSONObject();
        Util.handlePageParam(param, query);
        param.put("Operation", QUERY_NODE_DATA_RESEARCH_RESULT_OPERATION);
        Util.handleParam(param, query);
        return query(param,StudyResultVO.class);
    }

    @SneakyThrows
    @Override
    public BlockChainTransactionVO saveStudyResult(StudyResultDTO dto) {
        JSONObject param = new JSONObject();
        param.put("Operation", QUERY_NODE_DATA_RESEARCH_RESULT_OPERATION);
        Util.handleParam(param, dto);
        return blockChainClientHandler.transaction(DATA_PROVIDER_INVOKE, param.toJSONString());
    }

    @SneakyThrows
    public <T extends Pagination<K>, K> T query(JSONObject param, Class<T> clazz) {
       return blockChainClientHandler.query(DATA_PROVIDER_QUERY, param.toJSONString(), clazz);
    }

    @Override
    public BlockChainClientHandler getBlockChainClientHandler() {
        return this.blockChainClientHandler;
    }
}
