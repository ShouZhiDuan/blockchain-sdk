package com.nvxclouds.blockchain.biz.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.nvxclouds.blockchain.api.vo.BlockChainTransactionVO;
import com.nvxclouds.blockchain.biz.config.ChannelConfig;
import com.nvxclouds.blockchain.biz.config.ClientConfig;
import com.nvxclouds.blockchain.biz.config.Config;
import com.nvxclouds.blockchain.biz.config.UserConfig;
import com.nvxclouds.blockchain.biz.constant.TimeConstant;
import com.nvxclouds.blockchain.biz.domain.InvokeInfo;
import com.nvxclouds.blockchain.biz.domain.QueryInfo;
import com.nvxclouds.blockchain.biz.enums.BlockchainExceptionEnum;
import com.nvxclouds.blockchain.biz.exception.BlockchainException;
import com.nvxclouds.blockchain.biz.mapper.InvokeInfoMapper;
import com.nvxclouds.blockchain.biz.mapper.QueryInfoMapper;
import com.nvxclouds.blockchain.biz.service.impl.*;
import com.nvxclouds.common.base.pojo.Pagination;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hyperledger.fabric.protos.common.Common;
import org.hyperledger.fabric.protos.peer.Query;
import org.hyperledger.fabric.sdk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/16 11:57
 * @Description: 区块客户端请求
 */
@Slf4j
@Component
public class BlockChainClientHandler {

    private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
    private static final String EXPECTED_EVENT_NAME = "event";


    private UserContext adminUser;
    private Channel channel;
    private ChannelClient channelClient;
    private FabricClient fabricClient;
    private Peer peer;

    private UserConfig userConfig;

    private ClientConfig clientConfig;

    private ChannelConfig channelConfig;

    @Autowired
    private InvokeInfoMapper invokeInfoMapper;

    @Autowired
    private QueryInfoMapper queryInfoMapper;


    public BlockChainClientHandler() {


    }

    public void config() {
        this.userConfig = UserConfig.builder()
                .name(Config.ADMIN)
                .affiliation(Config.DATAPROVIDER)
                .mspId(Config.DataProviderMSP)
                .build();
        this.clientConfig = ClientConfig.builder()
                .url(Config.CA_DATAPROVIDER_URL)
                .tslFile(Config.DATAPROVIDER_TLSFILE)
                .host(Config.CA_DATAPROVIDER_URL)
                .build();
        this.channelConfig = ChannelConfig.builder()
                .channelName(Config.CHANNEL_NAME)
                .peer(Config.DATAPROVIDER_PEER_1)
                .peerUrl(Config.DATAPROVIDER_PEER_1_URL)
                .orderName(Config.ORDERER0_NAME)
                .orderUrl(Config.ORDERER0_URL)
                .server(Config.server0Url)
                .build();
    }


    @SneakyThrows
    public void config(UserConfig userConfig, ClientConfig clientConfig, ChannelConfig channelConfig) {
        this.userConfig = userConfig;
        this.clientConfig = clientConfig;
        this.channelConfig = channelConfig;
        //init();

    }


    public ChannelClient init() throws Exception {
        synchronized (this){
            initUser();
            initClient();
            initChannel();
            return this.channelClient;
        }

    }


    private void initChannel() throws Exception {
        this.channelClient = fabricClient.createChannelClient(Config.CHANNEL_NAME);
        this.channel = channelClient.getChannel();
        String peer1 = channelConfig.getPeer();
        String peer1Url = channelConfig.getPeerUrl();
        Properties properties1 = Util.gate2ePro("peer", peer1);
        this.peer = fabricClient.getInstance().newPeer(peer1, peer1Url, properties1);
//            Properties properties2 = Util.gate2ePro("peer", peer1);
//            Peer dataproviderPeer2 = fabClient.getInstance().newPeer(Config.DATAPROVIDER_PEER_0, peer1Url, properties2);
//        EventHub eventHub = fabricClient.getInstance().newEventHub("eventhub01", Config.grpc + Config.server2Url + clientConfig.getEventHubPort(), properties1);
//			EventHub eventHub2 = fabClient.getInstance().newEventHub("eventhub02", "grpcs://"+Config.server0Url+":9053");
        Properties orderPro = Util.gate2ePro("orderer", channelConfig.getOrderName());
        Orderer orderer = fabricClient.getInstance().newOrderer(channelConfig.getOrderName(), channelConfig.getOrderUrl(), orderPro);
        channel.addPeer(peer);
//            channel.addPeer(dataproviderPeer2);//背书策略为OR的话不需要添加 peer2
//        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();
    }

    private void initClient() throws Exception {
        String path = clientConfig.getTslFile();
        InputStream inputStream = BlockChainClientHandler.class.getClassLoader().getResourceAsStream(path);
        String substring = path.substring(path.lastIndexOf("."));
        Path tmpFilePath = Files.createTempFile(UUID.randomUUID().toString(), substring);
        assert inputStream != null;
        Files.copy(inputStream, tmpFilePath, StandardCopyOption.REPLACE_EXISTING);
        String certificate = new String(IOUtils.toByteArray(inputStream), UTF_8);
        Properties properties = new Properties();
        properties.put("pemBytes", certificate.getBytes());
        properties.setProperty("pemFile", tmpFilePath.toString());
        properties.setProperty("allowAllHostNames", "true");
        CAClient caclient = new CAClient(userConfig.getAffiliation(), clientConfig.getUrl(), properties);
        caclient.setAdminUserContext(adminUser);
        adminUser = caclient.enrollAdminUserTLS("admin", "adminpw");
        this.fabricClient = new FabricClient(adminUser);

    }

    private void initUser() {
        Util.cleanUp();
        this.adminUser = new UserContext();
        adminUser.setName(userConfig.getName());
        adminUser.setAffiliation(userConfig.getAffiliation());
        adminUser.setMspId(userConfig.getMspId());
    }

    /**
     * @param function
     * @param param
     * @return
     * @throws Exception
     */
    public String query(String function, String param) throws Exception {
        checkParam(function, param);
        init();
        log.info("query function : {} , param : {}", function, param);
        Collection<ProposalResponse> responsesQuery = channelClient.queryByChainCode(Config.CHAINCODE_TOBCC_NAME, function, new String[]{param});
        for (ProposalResponse pres : responsesQuery) {
            saveQueryInfo(pres.getStatus(), param, function);
            if (pres.getStatus().getStatus() == 200) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                log.info("Query success : {}", stringResponse);
                return stringResponse;
            } else {
                String message = pres.getMessage();
                log.error("Query failed : {}", message);
                JSONObject jsonObject = JSON.parseObject(message);
                Integer code = (Integer) jsonObject.get("ErrNO");
                if (code != null && code == 1106) {
                    return "";
                }

            }
            break;
        }
        throw new BlockchainException(BlockchainExceptionEnum.BLOCKCHAIN_QUERY_FAILED);
    }

    public void checkParam(String function, String param) {
        if (StringUtils.isBlank(function) || StringUtils.isBlank(param)) {
            log.info("function or param is block");
            throw new BlockchainException(BlockchainExceptionEnum.BLOCKCHAIN_QUERY_FAILED);
        }
        JSONObject jsonParam = JSONObject.parseObject(param);
        String txTime = (String) jsonParam.get("txTime");
        if (null != txTime) {
            if (!Pattern.matches(TimeConstant.YYYYMMDDHHMMSS_REGX,txTime)) {
                throw new BlockchainException(BlockchainExceptionEnum.TX_TIME_INVALID);
            }
        }

    }

    public <T extends Pagination<K>, K> T query(String function, String param, Class<T> clazz) throws Exception {
        checkParam(function, param);
        init();
        log.info("query function : {} , param : {}", function, param);
        Collection<ProposalResponse> responsesQuery = channelClient.queryByChainCode(Config.CHAINCODE_TOBCC_NAME, function, new String[]{param});
        for (ProposalResponse pres : responsesQuery) {
            saveQueryInfo(pres.getStatus(), param, function);
            if (pres.getStatus().getStatus() == 200) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                log.info("Query success : {}", stringResponse);
                return JSON.parseObject(stringResponse, clazz);
            } else {
                String message = pres.getMessage();
                log.error("Query failed : {}", message);
                JSONObject jsonObject = JSON.parseObject(message);
                Integer code = (Integer) jsonObject.get("ErrNO");
                if (code != null && code == 1106) {
                    return clazz.newInstance();
                }
            }
            break;
        }
        throw new BlockchainException(BlockchainExceptionEnum.BLOCKCHAIN_QUERY_FAILED);
    }


    private void saveQueryInfo(ChaincodeResponse.Status status, String param, String function) {
        QueryInfo record = new QueryInfo();
        if (status.getStatus() == 200) {
            record.setSucceed(1);
        } else {
            record.setSucceed(0);
        }
        record.setCreateTime(new Date());
        record.setFunctionName(function);
        record.setOperation(getOperationByParam(param));
        record.setQueryParam(param);
        queryInfoMapper.insertSelective(record);
    }

    private String getOperationByParam(String param) {
        return JSONObject.parseObject(param).getString("Operation");
    }

    private String getHashByParam(String param) {
        return JSONObject.parseObject(param).getString("messageHash");
    }

    public BlockChainTransactionVO transaction(String function, String param) throws Exception {
        init();
        if (StringUtils.isBlank(function) || StringUtils.isBlank(param)) {
            log.info("function or param is block");
            throw new BlockchainException(BlockchainExceptionEnum.BLOCKCHAIN_QUERY_FAILED);
        }
        TransactionProposalRequest request = fabricClient.getInstance().newTransactionProposalRequest();
        ChaincodeID ccID = ChaincodeID.newBuilder().setName(Config.CHAINCODE_TOBCC_NAME).build();
        request.setChaincodeID(ccID);
        request.setFcn(function);
        request.setArgs(param);
        request.setProposalWaitTime(1000);
        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
        tm2.put("result", ":)".getBytes(UTF_8));
        tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);
        request.setTransientMap(tm2);
        Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
        for (ProposalResponse res : responses) {
            ChaincodeResponse.Status status = res.getStatus();
            if (status.getStatus() == 200) {
                String stringResponse = new String(res.getChaincodeActionResponsePayload());
                log.info("Invoke success : {}", stringResponse);
                saveInvokeInfo(1, function, param, stringResponse);
                return JSONObject.parseObject(stringResponse, BlockChainTransactionVO.class);
            } else {
                saveInvokeInfo(0, function, param, null);
                log.error("Invoke failed : {}", res.getMessage());
            }
            break;
        }
        throw new BlockchainException(BlockchainExceptionEnum.BLOCKCHAIN_INVOKE_FAILED);
    }

    private void saveInvokeInfo(Integer succeed, String function, String param, String stringResponse) {
        InvokeInfo record = new InvokeInfo();
        record.setOperation(getOperationByParam(param));
        record.setFunctionName(function);
        record.setSucceed(succeed);
        record.setContent(stringResponse);
        record.setCreateTime(new Date());
        record.setPeerNode(channelConfig.getPeer());
        record.setPeerUrl(channelConfig.getPeerUrl());
        record.setOrderNode(channelConfig.getOrderName());
        record.setOrderUrl(channelConfig.getOrderUrl());
        record.setHash(getHashByParam(param));
        record.setParam(param);
        invokeInfoMapper.insertSelective(record);
    }


    @SneakyThrows
    public void getBlock() {

        
        init();
        //区块信息
        BlockchainInfo blockchainInfo = channel.queryBlockchainInfo(peer);
        log.info("区块高度 : {}", blockchainInfo.getHeight());
        log.info("区块交易信息 : {}");
        //链码条数
        List<Query.ChaincodeInfo> chainCount = channel.queryInstantiatedChaincodes(peer);
        if (CollectionUtils.isEmpty(chainCount)) {
            log.info("getBlock error : {}", "chain count 为空");
            return;
        }
        chainCount.forEach(c->{
            ByteString inputBytes = c.getInputBytes();
            ByteString id = c.getId();
        });

        //区块hash
        long b = blockchainInfo.getHeight();
        for (int i = 0; i < b; i++) {
            BlockInfo blockInfo = channel.queryBlockByNumber(i);
            //hash
            ByteString bytes = blockInfo.getBlock().getData().getData(i);

            byte[] bytes1 = bytes.toByteArray();
//            JsonReader reader = Json.createReader(new ByteArrayInputStream(bytes1));
//            JsonObject jsonObject1 = reader.readObject();
//
//            JsonArray arr = reader.readArray();
//
//
//            for (int i1 = 0; i1 < arr.size(); i1++) {
//                JsonObject jsonObject= arr.getJsonObject(i1);
//                System.out.println(jsonObject.toString());
//            }

            String s = new String(bytes1);
//            String s = enc.encodeBuffer(bytes1);
            System.out.println(s);
            Common.BlockHeader a = blockInfo.getBlock().getHeader();


            //String test = bytesToString(bytes,"utf-8");
            System.out.println(bytes.toString());
            //JSON.toJSONString(blockInfo);


            JSONObject.parse(blockInfo.toString());

            blockInfo.getBlock().getData().getData(0);
            //channel
            //JSONObject object = JSONObject.parseObject(String.valueOf(blockInfo.getBlock().getData().getData(0).toByteArray()));
//                System.out.println("hash内容：" +  blockInfo.getBlock().getHeader().getDataHash());
//                System.out.println("链序号：" +  blockInfo.getBlock().getHeader().getNumber());
//                System.out.println("======");
        }


        System.out.println("-------");

    }

    /**
     * 获取原生channel
     * @return
     */
    public Channel getChannel() {
        return this.channel;
    }

    /**
     * 获取当前组织peer节点信息
     * @return
     */
    public Peer getPeer() {
        return this.peer;
    }


//    public static void main(String[] args) throws Exception {
////        BlockChainClientHandler blockChainClientHandler = new BlockChainClientHandler();
//        long start = System.currentTimeMillis();
//        BlockChainClientHandler blockChainClientHandler = new BlockChainClientHandler(
//                UserConfig.builder()
//                        .name(Config.ADMIN)
//                        .affiliation(Config.REGULATOR)
//                        .mspId(Config.RegulatorMSP)
//                        .build(),
//                ClientConfig.builder()
//                        .url(Config.CA_REGULATOR_URL)
//                        .tslFile(Config.REGULATOR_TLSFILE)
//                        .host(Config.CA_REGULATOR_URL)
//                        .build(),
//                ChannelConfig.builder()
//                        .channelName(Config.CHANNEL_NAME)
//                        .peer(Config.REGULATOR_PEER_1)
//                        .peerUrl(Config.REGULATOR_PEER_1_URL)
//                        .orderName(Config.ORDERER2_NAME)
//                        .orderUrl(Config.ORDERER2_URL)
//                        .server(Config.server2Url)
//                        .build());
//        blockChainClientHandler.init();
////        blockChainClientHandler.transaction();
//
//        log.info("invoke time : {}", System.currentTimeMillis() - start);
//
////        blockChainClientHandler.query();
////        blockChainClientHandler.invoke();
//    }


}
