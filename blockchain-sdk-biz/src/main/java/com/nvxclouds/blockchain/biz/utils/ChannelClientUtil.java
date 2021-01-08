package com.nvxclouds.blockchain.biz.utils;

import com.nvxclouds.blockchain.biz.config.ChannelConfig;
import com.nvxclouds.blockchain.biz.config.ClientConfig;
import com.nvxclouds.blockchain.biz.config.Config;
import com.nvxclouds.blockchain.biz.config.UserConfig;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/9 18:26
 * @Description:
 */
public class ChannelClientUtil {

    private static final String DATA_PROVIDER_QUERY = "dataProviderQuery";
    private static final String DATA_PROVIDER_INVOKE = "dataProviderInvoke";
    private static final String DATA_NODE_REGISTRATION_OPERATION = "dataNodeRegistration";
    private static final String DATA_NODE_MODIFICATION_OPERATION = "dataNodeModificationApply";
    private static final String ADD_DATASET_OPERATION = "addDatasetApply";
    private static final String OPEN_DATASET_OPERATION = "openDatasets";
    private static final String MODIFY_DATASET_OPERATION = "modifyDatasets";
    private static final String DATASET_OFFLINE_OPERATION = "datasetsOffline";
    private static final String QUERY_NODE_DATA_RESEARCH_RESULT_OPERATION = "queryNodeDataResearchResults";


    public static BlockChainClientInit getBlockChainClientInit() throws Exception {
        BlockChainClientInit blockChainClientInit =  new BlockChainClientInit();
        blockChainClientInit.config(
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
        blockChainClientInit.init();
        return blockChainClientInit;

    }



}
